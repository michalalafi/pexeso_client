package controllers;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import sound_pexeso.TcpClient;

/**
 * FXML Controller class
 *
 * @author Michal-PC
 */
public class MenuController implements Initializable, IConnectedController {

    @FXML
    private Button btnPlayGame;
    @FXML
    private Button btnReconnect;
    @FXML
    private Label lbStatus;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Menu controller - initialize");
        
        if(TcpClient.reconnect){
            this.btnReconnect.setVisible(true);
        }
        else{
            this.btnReconnect.setVisible(false);
        }
        setupConnection();
    }    
    @FXML
    private void reconnect(ActionEvent event){
        TcpClient.getConnection().reconnect();
    }
    @FXML
    private void playGame(ActionEvent event) {
        TcpClient.getConnection().sendLoginLobbyRequest();
    }
    
    private void setupConnection(){
        //Vytvorime spojeni
        TcpClient connection = new TcpClient();
        TcpClient.setConnection(connection);
        //Vytvorime vlakno pro naslouchani
        Thread tcpClientThread = new Thread(connection);
        tcpClientThread.setDaemon(true);
        tcpClientThread.start();
    }
    
    @FXML
    private void exitGame(ActionEvent event){
        Platform.exit();
    }
    @Override
    public void setStatus(String status) {
        this.lbStatus.setText(status);
    }

    @Override
    public void requestClientName() {
        return;
    }

    @Override
    public void setClientName(String clientName) {
        return;
    }

    @Override
    public void connected() {
        return;
    }

    @Override
    public void disconnected() {
        return;
    }

    @Override
    public void setSessionId(String sessionId) {
        return;
    }

    @Override
    public void requestSessionId() {
        return;
    }
}
