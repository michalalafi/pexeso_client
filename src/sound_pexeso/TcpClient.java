/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sound_pexeso;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamCorruptedException;
import java.net.InetAddress;
import java.net.Socket;
import javafx.application.Platform;
import static sound_pexeso.Protocol.*;

/**
 *
 * @author michal
 */
public class TcpClient implements Runnable{
    
    private static TcpClient connection;
    private ExpandedBufferedReader reader;
    private PrintWriter writer;
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
    private void connect(){
        Socket s = null;
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
        System.out.println("Client connected!");
        listen();
    }
    public boolean send(String message){
        if(writer == null){
            System.out.println("Writer je NULL");
            return false;
        }
        try{
            //line = reader.readLine();
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
            case CLIENT_NAME_RESPONSE: 
                 Platform.runLater(()->{
                     App.lobby();
                     
                     App.getLobbyController().setClientName(parts[1]);
                 });
            
        }
        
    }
}

