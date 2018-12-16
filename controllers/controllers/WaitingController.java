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
import sound_pexeso.Client;
import sound_pexeso.Protocol;
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
            TcpClient.getConnection().sendSimpleMessage(Protocol.NEW_GAME_REQUEST, "1");
        }
        else if( alert.getResult() == ButtonType.NO){
            //Posleme na server ze nechceme hrat
            System.out.println("No");
            TcpClient.getConnection().sendSimpleMessage(Protocol.NEW_GAME_REQUEST, "0");
        }
        
        
    }

    @Override
    public void setClientName(String clientName) {
        lbUserName.setText(clientName);
    }

    @Override
    public void connected() {
        /*lbOnline.setText("Online");
        lbOnline.setStyle("-fx-text-fill: #00ff00"); */
    }

    @Override
    public void disconnected() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setSessionId(String sessionId) {
        System.out.println("Waiting controller: Set session id:" + sessionId);
        this.lbSession.setText("Session: "+sessionId);
    }

    @Override
    public void requestSessionId() {
        TcpClient.getConnection().sendSimpleMessage(Protocol.SESSION_ID_REQUEST, "");
    }

    @Override
    public void requestClientName() {
        TcpClient.getConnection().sendSimpleMessage(Protocol.CLIENTS_NAME_REQUEST, "");
    }

    @Override
    public void setStatus(String status) {
        this.lbStatus.setText(status);
    }
}
