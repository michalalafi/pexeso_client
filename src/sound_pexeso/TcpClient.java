package sound_pexeso;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamCorruptedException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import static sound_pexeso.CommunicationManager.handleResponse;
import static sound_pexeso.Protocol.*;

/**
 *
 * @author michal
 */
public class TcpClient implements Runnable{
    
    private static TcpClient connection;
    private static Client client;
    private ExpandedBufferedReader reader;
    private PrintWriter writer;
    private Socket socket;
    
    private CommunicationManager communicationManager;
    private Timer communicationManagerTimer;
    private static final int COMMUNICATION_TIMER_INTERVAL = 2000;
    
    private static final int RECONNECT_TRY_DELAY = 200;
    
    public static boolean reconnect = false;
    private static int failedToReconnect = 0;
    private static final int MAX_ATTEMPTS_TO_RECONNECT = 3;
    
    private static final int MAX_CORRUPTED_RESPONSES = 5;
    private static int corruptedResponses = 0;
    
    public static boolean connected = false;
    
    
    public static String ipAdress = "192.168.0.103";
    public static int port = 10000;
    public static boolean read = true;
    
    
    public static final String SOUNDS_PATH="../sounds/";
    public TcpClient(){
        this.reader = null;
        this.writer = null;
        
        this.communicationManager = new CommunicationManager();
    }
    @Override
    public void run(){
       
        communicationManagerTimer = new Timer();
        
        communicationManagerTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                        if(!communicationManager.checkSentRequests()) {
                            handleRequestTimeout();
                        }
                        
                        communicationManager.sendIsAliveIfNotActive();
                }
        }, 0, COMMUNICATION_TIMER_INTERVAL);

        connect();
    }
    public static void setConnection(TcpClient newConnection){
        connection = newConnection;
    }
    public static TcpClient getConnection(){
        return connection;
    }
    public static Client getClient(){
        if(client == null){
            client = new Client(); 
        }            
        return client;
    }
    private void connect(){
        System.out.println("CONNECT - Start");
        // Pokud je pripojeny nedelame nic   
        if(isConnected()){
            return;
        }
        
        // Nastavime socket a reader a writer
        try{
            System.out.println("CONNECT - Connection setup");
            this.socket = new Socket(ipAdress, port);
            InetAddress adress = socket.getInetAddress();
            
            client = getClient();
            
            reader = new ExpandedBufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            //Vyresetujeme pocet pokusu o reconnect
            failedToReconnect = 0;
            
            connected = true;
            
            if(reconnect){
                //Pokud ma uzivatel pridelene id
                if(client.getClientId() != -1){
                    reconnect();
                    Platform.runLater(() -> {
                        App.getConnectedController().setStatus("Reconnection request sent...");
                    });
                }
                // Pokud nema pridelene id
                else{
                    sendLoginLobbyRequest();
                    Platform.runLater(() -> {
                        App.getConnectedController().setStatus("Lobby request sent...");
                    });
                }
                
                reconnect = false;
            }
            System.out.println("    Connected to: " + adress.getHostAddress() + " [" + adress.getHostName()+"]");
            Platform.runLater(() -> {
                App.getConnectedController().disableControls(false);
            });
            //Nastavime vlakno aby poslouchalo 
            listen();
        }
        catch(IOException e){
            handleConnectException();
        }
        System.out.println("CONNECT - End");
    }
    
    public void disconnect(){
        System.out.println("DISCONNECT - Start");
        
        read = false;
        
        if(communicationManagerTimer != null){
            communicationManagerTimer.cancel();
            communicationManagerTimer = null;
        }

        if(socket != null && !socket.isClosed()){
            try{
                socket.close();
            }
            catch(IOException e){
                System.err.println("    DISCONNECT - Closing socket failed");
            }
        }
        socket = null;
        writer = null;
        reader = null;
        System.out.println("DISCONNECT - End");
    }
    public void reconnect(){
        //Pokud byl odpojen posleme zadost o pripojeni a zablokujeme tlacitka na new game
        if(reconnect == true){
            System.out.println("RECONNECT - Sending reconnect request");
            sendRequest(new Request(RECONNECT_REQUEST));
        }
    }
    private boolean isConnected(){
        boolean isConnected = socket != null && socket.isConnected() && !socket.isClosed();
        
        return isConnected;
    }
    /**
     * 
     */
    public void sendLoginLobbyRequest(){
        System.out.println("SEND LOBBY REQUEST - Start");
        String message = MessageProcessing.createMessageForServerWithoutDelimiter(Protocol.LOGIN_TO_LOBBY_REQUEST);
        //Posleme jednoduchou zpravu jen s zadosti o lobby
        connection.send(message);
        System.out.println("SEND LOBBY REQUEST - End");
    }
    public void sendRequest(Request request){
        if(!connected){
            System.err.println("SEND REQUEST - Not connected");
            return;
        }
        //Pokud uz jsme zazadali o id muzeme posilat requesty s timto id clienta
        if(client != null && client.getClientId() != -1){
            String message = MessageProcessing.createMessageForServer(Integer.toString(client.getClientId()), Integer.toString(request.getRequestId()), request.getParams());
            //Pridame do pole a nechame poslat 
            communicationManager.addRequest(request);
            send(message);  
        }
        //Jinak pozadama o id, pokud uzivatel zmackne reconnect a jeste mu nebyla prideleno id
        else{
            sendLoginLobbyRequest();
        }
        
    }
    public void send(String message){
        System.out.println("SEND - Start");
        if(writer != null){
            try{
                System.out.println("Sending message: " + message);
                
                writer.println(message);
                writer.flush();
                //Pokud se nam podarilo poslat jsme pripojeni
                Platform.runLater(() -> {
                    App.getConnectedController().connected();
                });
                
                }
            catch(Exception e){
                handleSendError("Writer exception");
            }  
        }
        else{
            handleSendError("Writer is null");
        }
        System.out.println("SEND - End");
    }
    private void listen(){
        System.out.println("LISTEN - STARTED");
        read = true;
        while(read){
            if(reader == null){
                handleListenError("Reader is null");
            }
            else{
                try{
                    String message = reader.readLine();
                    if(message != null){
                        System.out.println("LISTEN - Received message:" + message);
                        Response response = new Response(message);
                        if(response.isValid()){
                            this.communicationManager.removeRequest(response);
                            handleResponse(response);
                        }
                        else{
                            System.err.println("LISTEN - Recieved message is not valid");
                            corruptedResponses++;
                        }
                    }
                    else{
                        System.err.println("LISTEN - Recieved message is null");
                        corruptedResponses++;
                    }

                    if(corruptedResponses > MAX_CORRUPTED_RESPONSES){
                        corruptedResponses = 0;
                        handleListenError("Max corrupted responses reached");
                    }

                }
                catch(StreamCorruptedException e){
                    handleListenError("Stream is corrupted");
                }
                catch(SocketTimeoutException e){
                    handleListenError("Socket timeout");
                }
                catch(IOException e){
                    System.err.println("LISTEN - IOException");
                } 
            }
        }
        System.out.println("LISTEN - ENDED");
    }
    
    private void handleSendError(String exception){
        System.err.println("SEND - " + exception);
        
        handleServerNotAvaible();
    }
    private void handleListenError(String exception){
        System.err.println("LISTEN - " + exception);
        
        handleServerNotAvaible();
    }
    private void handleRequestTimeout(){
        System.err.println("REQUEST TIMEOUT");
        
        handleServerNotAvaible();
    }
    private void handleServerNotAvaible(){
        connected = false;
        Platform.runLater(() -> {
            App.getConnectedController().setStatus("Server is not avaible..");
            App.getConnectedController().disconnected();
        });
        System.out.println("Nastavujeme reconnect na true");
        reconnect = true; // Dame ze true metoda connect se postara o to jestli se bude posilat zadost o reconnect nebo o lobby
    }
    private void handleConnectException(){
        System.out.println("    HANDLE CONNECT EXCEPTION - Start");
        
        connected = false;
        //Zvedme pocet pokusu o pripojeni
        failedToReconnect++;

        if(failedToReconnect > MAX_ATTEMPTS_TO_RECONNECT){
            System.err.println("        HANDLE CONNECT EXCEPTION - Max attempts to reconnect reached");
            
            Platform.runLater(() -> {
                App.getConnectedController().disableControls(false);
            });
            
            disconnect();
            //Vyresetujeme pokusy
            failedToReconnect = 0;
            Platform.runLater(() -> {
                App.menu();
                App.getConnectedController().setStatus("Trying to set connection failed - max attempts reached");
                //TODO povolit tlacitka
            });
            
            System.out.println("    HANDLE CONNECT EXCEPTION - End");
        }
        else{
            Platform.runLater(() -> {
                App.getConnectedController().disableControls(true);
            });
            
            System.err.println("        HANDLE CONNECT EXCEPTION - Connection setup failed, trying to reconnect");
            //Resetneme odeslane requesty
            communicationManager.resetRequestsSent();
            try {
                System.out.println("        THREAD STARTS TO SLEEP");
                Thread.sleep(RECONNECT_TRY_DELAY);
                System.out.println("        THREAD WAKES UP");
            } catch (InterruptedException ex) {
                Logger.getLogger(TcpClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            //TODO zablokovat tlacitka u controlleru
            Platform.runLater(() -> {
                App.getConnectedController().setStatus("Trying to set connection...");
            });
            
            connect();
        }
    }
}
 