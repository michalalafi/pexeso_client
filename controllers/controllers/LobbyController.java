package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import sound_pexeso.App;
import sound_pexeso.Client;
import sound_pexeso.Protocol;
import sound_pexeso.TcpClient;

/**
 * FXML Controller class
 *
 * @author Michal-PC
 */
public class LobbyController implements Initializable, IConnectedController {

    @FXML
    private Button btnNewGame;
    @FXML
    private Button btnBack;
    @FXML
    private Label lbUserName;
    @FXML
    private Label lbOnline;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Client client = TcpClient.getClient();
        if(client == null){
            //Zobrazime ze je neco spatne
            disconnected();
            return;
        }
        setClientName(client);
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
    
    @Override
    public void setClientName(Client client){
        lbUserName.setText(client.getClientName());
    }
    
    @Override
    public void connected(){
        lbOnline.setText("Online");
        lbOnline.setStyle("-fx-text-fill: #00ff00");
    }

    @Override
    public void disconnected() {
        lbOnline.setText("Offline");
        lbOnline.setStyle("-fx-text-fill: #ff0000");
    }
    
    
}
