package controllers;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import sound_pexeso.App;
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
    }    
    @FXML
    private void reconnect(ActionEvent event){
        TcpClient.getConnection().reconnect();
    }
    @FXML
    private void playGame(ActionEvent event) {
        TcpClient.getConnection().sendLoginLobbyRequest();
    }
    
    @Override
    public void setupConnection(){
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
        App.exitApp();
    }
    @Override
    public void setStatus(String status) {
        this.lbStatus.setText(status);
    }

    @Override
    public void requestClientName() {
        System.out.println("MENU CONTROLLER - RequestClientName");
    }

    @Override
    public void setClientName(String clientName) {
        System.out.println("MENU CONTROLLER - SetClientName");
    }

    @Override
    public void connected() {
        System.out.println("MENU CONTROLLER - Connected");
    }

    @Override
    public void disconnected() {
        System.out.println("MENU CONTROLLER - disconnected");
        TcpClient.getConnection().disconnect();
        
        setupConnection();
    }

    @Override
    public void setSessionId(String sessionId) {
        System.out.println("MENU CONTROLLER - SetSessionId");
    }

    @Override
    public void requestSessionId() {
        System.out.println("MENU CONTROLLER - RequestSessionId");
    }

    @Override
    public void disableControls(boolean value) {
        this.btnPlayGame.setDisable(value);
        this.btnReconnect.setDisable(value);
    }
}
