package controllers;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import sound_pexeso.App;
import sound_pexeso.MessageProcessing;
import sound_pexeso.Protocol;
import sound_pexeso.TcpClient;

/**
 * FXML Controller class
 *
 * @author Michal-PC
 */
public class MenuController implements Initializable {

    @FXML
    private Button btnPlayGame;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void playGame(ActionEvent event) {
        //Vytvorime spojeni
        TcpClient connection = new TcpClient();
        TcpClient.setConnection(connection);
        //Vytvorime vlakno pro naslouchani
        Thread tcpClientThread = new Thread(connection);
        tcpClientThread.setDaemon(true);
        tcpClientThread.start();
        String message = MessageProcessing.createMessageForServer("",Integer.toString(Protocol.LOGIN_TO_LOBBY_REQUEST), "");
        System.out.println("Zprava pro server je: " + message);
        //Posleme zadost o pripojeni
        connection.send(message); 
        
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run(){
                if(connection.send(message))
                    this.cancel();
            }
        }, 100, 100);
        //TODO SEND CONNECT REQUEST AND WAIT
        //App.lobby();
    }
    
    @FXML
    private void exitGame(ActionEvent event){
        Platform.exit();
    }
    
}
