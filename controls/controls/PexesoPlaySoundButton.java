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
    private static final String buttonClassStyle = "game-pexeso-play-button";

    private int buttonId;
    
    public PexesoPlaySoundButton(int id){    
        super("" + (id+1));
        this.getStyleClass().add(buttonClassStyle);
        
        this.buttonId = id;
        this.setOnAction(Event -> GameController.pexesoPlaySoundButtonClick(this));
    }
    
    public int getButonId(){
        return this.buttonId;
    }
    public void setButtonId(int id){
        this.buttonId = id;
    }
    
}
