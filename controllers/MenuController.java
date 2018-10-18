

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import sound_pexeso.App;

/**
 * FXML Controller class
 *
 * @author Michal-PC
 */
public class MenuController implements Initializable {

    @FXML
    private Button btnPlayGame;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void playGame(ActionEvent event) {
        App.lobby();
    }
    
    @FXML
    private void exitGame(ActionEvent event){
        Platform.exit();
    }
    
}
