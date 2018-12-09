/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls;

import controllers.GameController;
import javafx.scene.control.Button;

/**
 *
 * @author michal
 */
public class PexesoPlaySoundButton extends Button{
    private static final String BUTTON_CLASS_STYLE = "game-pexeso-play-button";
    private static final String BUTTON_PLAYED_CLASS_STYLE = "-fx-background-color: #ff0000;";
    private static final String BUTTON_REVEALED_CLASS_STYLE = "-fx-background-color: #00ff00;";
    private String defaultButtonStyle = "";

    private int buttonId;
    
    public PexesoPlaySoundButton(int id){    
        super("" + (id+1));
        this.defaultButtonStyle = this.getStyle();
        this.getStyleClass().add(BUTTON_CLASS_STYLE);
        
        this.buttonId = id;
        this.setOnAction(Event -> GameController.pexesoPlaySoundButtonClick(this));
    }
    
    public void setPlayed(){
        this.setStyle(BUTTON_PLAYED_CLASS_STYLE);
    }
    public void setRevealed(){
        this.setStyle(BUTTON_REVEALED_CLASS_STYLE);
        this.setDisable(true);
    }
    
    public void reset(){
        this.setStyle(defaultButtonStyle);
    }
    
    public int getButonId(){
        return this.buttonId;
    }
    public void setButtonId(int id){
        this.buttonId = id;
    }
    
}
