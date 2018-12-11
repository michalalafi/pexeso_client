/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls;

import controllers.GameController;
import javafx.scene.layout.FlowPane;

/**
 *
 * @author michal
 */
public class PexesoFlowPane extends FlowPane {
    private PexesoPlaySoundButton[] notReveleadPlaySoundButtons = new PexesoPlaySoundButton[GameController.numberOfPexesos];
    private PexesoPlaySoundButton[] revealedPlaySoundButtons = new PexesoPlaySoundButton[GameController.numberOfPexesos];
    
    public PexesoFlowPane(){
        super();
        
        for(int i = 0; i < GameController.numberOfPexesos; i++){
            PexesoPlaySoundButton btn = new PexesoPlaySoundButton(i);
            this.getChildren().add(btn);
            notReveleadPlaySoundButtons[i] = btn;
        }
    }
    
    public void setDisableAllNotReveleadPlaySoundButtons(boolean value){
        for(int i = 0; i < GameController.numberOfPexesos; i++){
            PexesoPlaySoundButton btn = this.notReveleadPlaySoundButtons[i];
            if(btn != null){
               btn.setDisable(value);
            }
        }
    }
    public void setPlaySoundButtonPlayed(int id){
        PexesoPlaySoundButton btn = notReveleadPlaySoundButtons[id];
        if(btn == null){
            //TODO co se stane kdyz tam neni
            return;
        }
        btn.setPlayed();
    }
    public void setPlaySoundButtonRevealed(int id){
        PexesoPlaySoundButton btn = notReveleadPlaySoundButtons[id];
        if(btn == null){
            //TODO co se stane kdyz tam neni
            return;
        }
        btn.setRevealed();
        //Dame ho do odhalenych
        notReveleadPlaySoundButtons[id] = null;
        revealedPlaySoundButtons[id] = btn;
    }
    public void resetNotRevealedPlaySoundButtons(){
        for(int i = 0; i < GameController.numberOfPexesos; i++){
            PexesoPlaySoundButton btn = this.notReveleadPlaySoundButtons[i];
            if(btn != null){
               btn.reset();
            }
        }
    }
    
}
