/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controls.PexesoFlowPane;
import controls.PexesoPlaySoundButton;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import sound_pexeso.App;
import sound_pexeso.Client;
import sound_pexeso.Protocol;
import sound_pexeso.TcpClient;

/**
 *
 * @author michal
 */
public class GameController implements Initializable, IConnectedController{
    @FXML
    private PexesoFlowPane gameBoard;
    @FXML
    private Label lbSession;
    @FXML
    private Label lbUserName;
    @FXML
    private VBox P1ScoreVBox;
    @FXML
    private VBox P2ScoreVBox;
    @FXML
    private Label lbOpponentName;
    @FXML
    Label lbP1Score;
    @FXML
    Label lbP2Score;
    
    @FXML
    private Label lbStatus;
    
    public static int numberOfPexesos = 4;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        requestSessionId();
        requestClientName();

    }
    
    public static void pexesoPlaySoundButtonClick(PexesoPlaySoundButton sender){
        App.getGameController().getGameBoard().setDisableAllNotReveleadPlaySoundButtons(true);
        App.getGameController().getGameBoard().setFirstClickedButton(sender);
        
        int pexesoId = sender.getButonId();
        // Posli serveru stisknute pexeso
        // Server az posle zpravu zacne hrat zvuk
        TcpClient.getConnection().sendSimpleMessage(Protocol.PEXESO_REVEAL_REQUEST, Integer.toString(pexesoId));
    }
    
    public PexesoFlowPane getGameBoard(){
        return this.gameBoard;
    }
    
    public void setScore(String scores[]){
        lbP1Score.setText("Score: " + scores[0]);
        lbP2Score.setText("Score: " + scores[1]);
    }
    
    public void playAgainConfirmation(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to play again?", ButtonType.YES, ButtonType.NO);
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
    public void setPlayersTurn(boolean isPlayersTurn){
        if(isPlayersTurn){
            P1ScoreVBox.setStyle("-fx-border-color: green; -fx-border-width:3;");
            P2ScoreVBox.setStyle("-fx-border-color: red;-fx-border-width:3;");
        }
        else{
            P2ScoreVBox.setStyle("-fx-border-color: green;-fx-border-width:3;");
            P1ScoreVBox.setStyle("-fx-border-color: red;-fx-border-width:3;");
        }
    }
    public void setOpponentName(String name){
        this.lbOpponentName.setText(name);
    }

    @Override
    public void connected() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void disconnected() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setSessionId(String sessionId) {
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
    public void setClientName(String clientName) {
        lbUserName.setText(clientName);
    }

    @Override
    public void setStatus(String status) {
        this.lbStatus.setText(status);
    }
    
    
    

}
