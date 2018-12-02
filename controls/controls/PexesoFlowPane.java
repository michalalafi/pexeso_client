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
    
    public PexesoFlowPane(){
        super();
        
        for(int i = 0; i < GameController.numberOfPexesos; i++){
            this.getChildren().add(new PexesoPlaySoundButton(i));
        }
    }
    
}
