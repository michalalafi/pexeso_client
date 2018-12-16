package sound_pexeso;

import controllers.IConnectedController;
import controllers.LobbyController;
import controllers.WaitingController;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamCorruptedException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import static sound_pexeso.Protocol.*;
import utils.SoundPlayer;

/**
 *
 * @author michal
 */
public class TcpClient implements Runnable{
    
    private static TcpClient connection;
    private static Client client;
    private ExpandedBufferedReader reader;
    private PrintWriter writer;
    
    private static final String SOUNDS_PATH="../sounds/";
    public TcpClient(){
        this.reader = null;
        this.writer = null;
        
    }
    @Override
    public void run(){
            connect();
    }
    public static void setConnection(TcpClient newConnection){
        connection = newConnection;
    }
    public static TcpClient getConnection(){
        return connection;
    }
    public static Client getClient(){
        return client;
    }
    private void connect(){
        Socket s;
        try{
            s = new Socket("127.0.0.1", 10000);
            InetAddress adress = s.getInetAddress();
            System.out.println("Connecting to: " + adress.getHostAddress() + " [" + adress.getHostName()+"]");
        }
        catch(IOException e){ return;}
        try {
            reader = new ExpandedBufferedReader(new InputStreamReader(s.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
        }
        catch(NullPointerException | IOException e){ return;}
        client = new Client();
        System.out.println("Client connected!");
        listen();
    }
    /**
     * 
     */
    public void sendLoginLobbyRequest(){
        String message = Integer.toString(Protocol.LOGIN_TO_LOBBY_REQUEST);
        System.out.println("Zprava pro server je: " + message);
        
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run(){
                if(connection.send(message))
                    this.cancel();
            }
        }, 100, 100);
    }
    public void sendSimpleMessage(int requestId, String params){
        String message = MessageProcessing.createMessageForServer(Integer.toString(client.getClientId()), Integer.toString(requestId), params);
        //TODO posilat dokud send nebude true?
        send(message);    
    }
    public boolean send(String message){
        if(writer == null){
            System.out.println("Writer je NULL");
            return false;
        }
        try{
            writer.println(message);
            writer.flush();
            System.out.println("Message sended");
        }
        catch(Exception e){ return false;}
        
        return true;
    }
    private void listen(){
        System.out.println("Vlakno posloucha!");
        //String buffer = "";
        
        boolean read = true;
        while(read){
            try{
                String message = reader.readLine();
                if(message != null){
                    System.out.println("Prijata message: " + message);
                    String[] parts = MessageProcessing.getPartsOfMessage(message);
                    processServerResponse(parts);
                }
                else{
                    //System.out.println("Zadna prijata message");
                }
            }
            catch(StreamCorruptedException e){}
            catch(IOException e){}
            
        }
    }
    
    private void processServerResponse(String[] parts){
        for(int i=0; i<parts.length; i++){
            System.out.println("part[" +i + "]: "+parts[i]);
        }
        int action = Integer.parseInt(parts[0]);
        switch(action){
            case CLIENTS_NAME_RESPONSE:
                    setClientName(parts);
                    break;
            case CLIENT_ID_RESPONSE:
                    setClientId(parts);
                    break;
            case NEW_SESSION_RESPONE:
                    newSessionResponse(parts);
                    break;
            case SESSION_ID_RESPONSE:
                    setSessionId(parts);
                    break; 
            case NEW_GAME_BEGIN_RESPONSE:
                    newGameBegin(parts);
                    break;  
            case IS_PLAYER_TURN_RESPONSE:
                    playersTurn(parts);
                    break;
            case PLAY_AGAIN_RESPONSE:
                    playAgain(parts);
                    break;
            case PEXESO_REVEAL_RESPONSE:
                    // play sound
                    revealSound(parts);
                    break;
            case PEXESO_REVEAL_ID_RESPONSE:
                    revealPexesoPlayed(parts);
                    break;
            case SUCCESFULLY_REVEALED_PEXESO_RESPONSE:
                    successfullyRevealedPexesos(parts);
                    break;
            case SCORE_RESPONSE:
                    setScore(parts);
                    break;                     
            case WANT_TO_PLAY_GAME_RESPONSE:
                    wantToPlayGame();
                    break;
                    
            case END_OF_GAME_RESPONSE:
                    endOfGame();
                    break;
            case OPPONENTS_NAME_RESPONSE:
                    setOpponentsName(parts);
                    break;
            case STATUS_RESPONSE:
                    statusResponse(parts);
                    break;
            case NUMBER_OF_CLIENTS_ONLINE_RESPONSE:
                    setNumberOfClientsOnline(parts);
                    break;
                    
            case RETURN_TO_LOBBY_RESPONSE:
                    returnToLobbyResponse(parts);
                    break;
        }
        
    }
    
    private void setClientName(String[] parts){
        System.out.println("New client name is: "+ parts[1]);
        client.setClientName(parts[1]);
        
        Platform.runLater(() -> {
            IConnectedController controller = App.getConnectedController();
            if(controller != null){
                App.getConnectedController().setClientName(parts[1]);
            }         
        });
    }
    
    private void setClientId(String[] parts){
        System.out.println("New client id is: " + parts[1]);
        client.setClientId(Integer.parseInt(parts[1]));
        
        Platform.runLater(() -> {
            App.lobby();
        });
    }
    private void newSessionResponse(String[] parts){
        System.out.println("New session response");
        Platform.runLater(() -> {
            App.waiting();
        });
    }
    private void setSessionId(String[] parts){
        System.out.println("New session id is: "+ parts[1]);
        Platform.runLater(() -> {
            IConnectedController controller = App.getConnectedController();
            if(controller != null){
                App.getConnectedController().setSessionId(parts[1]);
            }  
        });
    }
    private void statusResponse(String[] parts){
        System.out.println("Menime status");
        Platform.runLater(() -> {        
            IConnectedController controller = App.getConnectedController();
            if(controller != null){
                App.getConnectedController().setStatus(parts[1]);
            }  
        });
    }
    private void playersTurn(String[] parts){
        System.out.println("Players turn: " + parts[1]);
        boolean isPlayersTurn = Integer.parseInt(parts[1]) == 1;
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run(){
                Platform.runLater(() -> { 
                    App.getGameController().getGameBoard().resetNotRevealedPlaySoundButtons();
                    App.getGameController().getGameBoard().setDisableAllNotReveleadPlaySoundButtons(!isPlayersTurn);
                    App.getGameController().setPlayersTurn(isPlayersTurn);
                });
            }
        }, 3000);
    }
    private void playAgain(String[] parts){
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run(){
                Platform.runLater(() -> { 
                    App.getGameController().getGameBoard().setDisableAllNotReveleadPlaySoundButtons(false);
                    App.getGameController().getGameBoard().setFirstClickedButtonDisable(true);
                });
            }
        }, 3000);
    }
    private void newGameBegin(String[] parts){
        System.out.println("Zacina nova hra");
        Platform.runLater(() -> {
            App.game();
            App.getGameController().getGameBoard().setDisableAllNotReveleadPlaySoundButtons(true);
        });
    }
    private void revealSound(String[] parts){
        //Muzeme pustit takhle protoze to bezi asynchrone
        SoundPlayer.playSound(SOUNDS_PATH + parts[1]);
        
    }
    private void revealPexesoPlayed(String[] parts){
        //Oznacime odhalenou pexeso
        Platform.runLater(() -> {
            //TODO OSETRIT PARSE INT
            App.getGameController().getGameBoard().setPlaySoundButtonPlayed(Integer.parseInt(parts[1]));
        });
    }
    private void successfullyRevealedPexesos(String[] parts){
        String[] ids = parts[1].split("\\;");
        int first_id = Integer.parseInt(ids[0]);
        int second_id = Integer.parseInt(ids[1]);
        
        //Oznacime uspesne odhalene pexeso
        Platform.runLater(() -> {
            //TODO OSETRIT PARSE INT
            App.getGameController().getGameBoard().setPlaySoundButtonRevealed(first_id);
            App.getGameController().getGameBoard().setPlaySoundButtonRevealed(second_id);
        });
    }
    
    private void setScore(String[] parts){
        Platform.runLater(() -> {
            App.getGameController().setScore(parts[1].split("\\;"));
        });
        
    }
    private void wantToPlayGame(){
        System.out.println("want to play");
        Platform.runLater(() -> {
            IConnectedController controller = App.getConnectedController();
            if(controller != null){
                if(controller instanceof WaitingController){
                    WaitingController waitingController = (WaitingController) controller;
                    waitingController.newGameConfirmation();
                }
            }
        });
    }
    private void endOfGame(){
        //Popneme dotaz jestli chteji hrat znovu?
        System.out.println("End of game");
        
        Platform.runLater(() -> {
           App.getGameController().playAgainConfirmation();
        });
    }
    private void setOpponentsName(String[] parts){
        System.out.println("Setting opponents name");
        
        Platform.runLater(() -> {
           App.getGameController().setOpponentName(parts[1]);
        });
    }
    private void setNumberOfClientsOnline(String[] parts){
        System.out.println("Setting number of online clients");
        Platform.runLater(() -> {
            IConnectedController controller = App.getConnectedController();
            if(controller != null){
                if(controller instanceof LobbyController){
                    LobbyController lobbyController = (LobbyController) controller;
                    lobbyController.setNumberOfOnlineClients(Integer.parseInt(parts[1]));
                }
            }
        });
        
    }
    private void returnToLobbyResponse(String[] parts){
        System.out.println("Return to lobby response");
        Platform.runLater(() -> {
            App.lobby();
        });
    }
    
}

