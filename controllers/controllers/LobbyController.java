package controllers;

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
    @FXML
    private Label lbUserName;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setClientName(String clientName){
        lbUserName.setText(clientName);
    }
    @FXML
    private void newGame(ActionEvent event) {
        TcpClient.getConnection().sendSimpleMessage(Protocol.NEW_SESSION_REQUEST,"");
        //TODO pozadat o prirazeni sessiony
        //App.game();
    }

    @FXML
    private void back(ActionEvent event) {
        App.menu();
    }
    
}
