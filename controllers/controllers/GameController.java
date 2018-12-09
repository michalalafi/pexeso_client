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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import sound_pexeso.App;
import sound_pexeso.Protocol;
import sound_pexeso.TcpClient;
import utils.SoundPlayer;

/**
 *
 * @author michal
 */
public class GameController implements Initializable {
    @FXML
    PexesoFlowPane gameBoard;
    
    public static int numberOfPexesos = 4;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public static void pexesoPlaySoundButtonClick(PexesoPlaySoundButton sender){
        App.getGameController().setGameBoardDisable(true);
        
        int pexesoId = sender.getButonId();
        // Posli serveru stisknute pexeso
        // Server az posle zpravu zacne hrat zvuk
        TcpClient.getConnection().sendSimpleMessage(Protocol.PEXESO_REVEAL_REQUEST, Integer.toString(pexesoId));
    }
    
    public void setGameBoardDisable(boolean value){
        this.gameBoard.setDisableAllPlayButtons(value);
    }
    
    public void setPlayedPexeso(int id){
        this.gameBoard.setPlaySoundButtonPlayed(id);
    }
    public PexesoFlowPane getGameBoard(){
        return this.gameBoard;
    }
    
    
    

}
