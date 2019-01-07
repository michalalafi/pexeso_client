/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import sound_pexeso.Protocol;
import sound_pexeso.Request;
import sound_pexeso.TcpClient;

/**
 * FXML Controller class
 *
 * @author michal
 */
public class WaitingController implements Initializable, IConnectedController {
    @FXML
    private Label lbUserName;
    @FXML
    private Label lbSession;
    @FXML
    private Label lbWaiting;
    @FXML
    private Label lbStatus;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        requestSessionId();
        requestClientName();
    }  
    public void setWaitingLabel(String reason){
        lbWaiting.setText(reason);
    }
    
    public void newGameConfirmation(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you accept game?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if(alert.getResult() == ButtonType.YES){
            //Posleme na server ze chceme hrat
            System.out.println("Yes");
            TcpClient.getConnection().sendRequest(new Request(Protocol.NEW_GAME_REQUEST, "1"));
        }
        else if( alert.getResult() == ButtonType.NO){
            //Posleme na server ze nechceme hrat
            System.out.println("No");
            TcpClient.getConnection().sendRequest(new Request(Protocol.NEW_GAME_REQUEST, "0"));
        }
        
        
    }

    @Override
    public void setClientName(String clientName) {
        lbUserName.setText(clientName);
    }

    @Override
    public void connected() {
        System.out.println("WAITING CONTROLLER - connected");
    }

    @Override
    public void disconnected() {
        System.out.println("WAITING CONTROLLER - disconnected");
        TcpClient.getConnection().disconnect();
        
        setupConnection();
    }

    @Override
    public void setSessionId(String sessionId) {
        System.out.println("Waiting controller: Set session id:" + sessionId);
        this.lbSession.setText("Session: "+sessionId);
    }

    @Override
    public void requestSessionId() {
        TcpClient.getConnection().sendRequest(new Request(Protocol.SESSION_ID_REQUEST));
    }

    @Override
    public void requestClientName() {
        TcpClient.getConnection().sendRequest(new Request(Protocol.CLIENTS_NAME_REQUEST));
    }

    @Override
    public void setStatus(String status) {
        this.lbStatus.setText(status);
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

    @Override
    public void disableControls(boolean value) {
        
    }
}
