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
import sound_pexeso.TcpClient;

/**
 * FXML Controller class
 *
 * @author michal
 */
public class WaitingController implements Initializable {

    @FXML
    private Label lbSessionId;
    @FXML
    private Label lbWaiting;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TcpClient.getConnection().sendSimpleMessage(Protocol.NEW_GAME_REQUEST, "");
    }  
    
    public void setSessionId(String id){
        lbSessionId.setText(id);
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
        }
        
        
    }
    
}
