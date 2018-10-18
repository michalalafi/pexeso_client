/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxml;

import java.net.URL;
import java.util.ResourceBundle;
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
public class LobbyController implements Initializable {

    @FXML
    private Button btnNewGame;
    @FXML
    private Button btnBack;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void newGame(ActionEvent event) {
    }

    @FXML
    private void back(ActionEvent event) {
        App.menu();
    }
    
}
