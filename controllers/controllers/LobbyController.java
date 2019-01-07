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
import sound_pexeso.Request;
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
    @FXML
    private Label lbStatus;
    @FXML
    private Label lbNumberOfOnlineClients;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        requestClientName();
        requestNumberOfOnlineClients();
    }
    
    @FXML
    private void newGame(ActionEvent event) {
        TcpClient.getConnection().sendRequest(new Request(Protocol.NEW_SESSION_REQUEST));
    }

    @FXML
    private void back(ActionEvent event) {
        App.menu();
    }
    public void requestNumberOfOnlineClients(){
        TcpClient.getConnection().sendRequest(new Request(Protocol.NUMBER_OF_CLIENTS_ONLINE_REQUEST));
    }
    public void setNumberOfOnlineClients(int number){
        lbNumberOfOnlineClients.setText("Online players: " + number);
    }
    @Override
    public void setClientName(String clientName) {
        lbUserName.setText(clientName);
    }
    
    @Override
    public void connected(){
        lbOnline.setText("Online");
        lbOnline.setStyle("-fx-text-fill: #00ff00");
    }

    @Override
    public void disconnected() {
        System.out.println("LOBBY CONTROLLER  - disconnected");
        
        lbOnline.setText("Offline");
        lbOnline.setStyle("-fx-text-fill: #ff0000");
        
        TcpClient.getConnection().disconnect();
        
        setupConnection();
    }

    @Override
    public void setSessionId(String sessionId) {
        System.out.println("Lobby - set session id");
    }

    @Override
    public void requestSessionId() {
        System.out.println("Lobby - request session id");
    }

    @Override
    public void requestClientName() {
        TcpClient.getConnection().sendRequest(new Request(Protocol.CLIENTS_NAME_REQUEST));
    }

    @Override
    public void setStatus(String status) {
        this.lbStatus.setText(status);
    }
    @Override
    public void setupConnection(){
        //Vytvorime spojeni
        TcpClient connection = new TcpClient();
        TcpClient.setConnection(connection);
        //Vytvorime vlakno pro naslouchani
        Thread tcpClientThread = new Thread(connection);
        tcpClientThread.setDaemon(true);
        tcpClientThread.start();
    }

    @Override
    public void disableControls(boolean value) {
        this.btnBack.setDisable(value);
        this.btnNewGame.setDisable(value);
    }
    
    
}
