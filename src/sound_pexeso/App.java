/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sound_pexeso;

import controllers.GameController;
import controllers.IConnectedController;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Michal-PC
 */
public class App extends Application {
    private static final String MENU_FXML  = "/fxml/Menu.fxml";
    private static final String LOBBY_FXML  = "/fxml/Lobby.fxml";
    private static final String GAME_FXML = "/fxml/Game.fxml";
    private static final String WAITING_FXML = "/fxml/Waiting.fxml";
    
    private static Stage primaryStage;
    
    private static GameController game;

    private static IConnectedController connectedController;
    
    
    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setTitle("Sound pexeso");
        
        menu();
        connectedController.setupConnection();
    }
    public static void printHelp(){
        System.out.println("HELP:");
        System.out.println("    To start app use this command:");
        System.out.println("    java -jar Sound_pexeso.jar <ip-adress> <port> [client name]");
        System.out.println("        <ip-adress>  - ip adress of server");
        System.out.println("        <port> - port of server");
        System.out.println("        [client name] - optional  param for reconnecting");
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        boolean validStart = true;
        switch (args.length) {
            case 1:
                if(args[0].equalsIgnoreCase("-help") || args[0].equalsIgnoreCase("-h")){
                    printHelp();
                }
                break;
            case 2:
                try{
                    TcpClient.ipAdress = args[0];
                    TcpClient.port = Integer.parseInt(args[1]);
                }
                catch(NumberFormatException e){
                    validStart = false;
                }
                break;
            case 3:
                try{
                    TcpClient.ipAdress = args[0];
                    TcpClient.port = Integer.parseInt(args[1]);
                }
                catch(NumberFormatException e){
                    validStart = false;
                }
                
                if(TcpClient.getClient().setClientIdFromString(args[2])){
                    TcpClient.getClient().setClientName(args[2]);
                    TcpClient.reconnect = true;
                }
                else{
                    validStart = false;
                }
                break;
            default:
                validStart = false;
                break;
        }
        if(!validStart){
            printHelp();
            exitApp();
        }
        
        System.out.println("Client starting with params - adress:" + TcpClient.ipAdress + " port:" + TcpClient.port);

        launch(args);     
    }
    
    @Override
    public void stop(){
        exitApp();
    }
    
    public static void exitApp(){
        if(TcpClient.getConnection() != null){
            System.out.println("Posilame request o ukonceni spojeni");
            TcpClient.getConnection().sendRequest(new Request(Protocol.DISCONNECT_REQUEST));   
        }
        System.out.println("Program is closing");
        Platform.exit();
        System.exit(0);
    }
    public static void menu(){
        try{
            
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(MENU_FXML));
            Parent root = fxmlLoader.load();
            
            connectedController = (IConnectedController) fxmlLoader.getController();
            Scene scene = new Scene(root);
        
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public static void lobby(){
        try{
            
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(LOBBY_FXML));
            Parent root = fxmlLoader.load();
            
            connectedController = (IConnectedController) fxmlLoader.getController();
            Scene scene = new Scene(root);
        
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void waiting(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(WAITING_FXML));
            Parent root = fxmlLoader.load();
            
            connectedController = (IConnectedController) fxmlLoader.getController();
            Scene scene = new Scene(root);
        
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void game(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(GAME_FXML));
            Parent root = fxmlLoader.load();
            
            game = (GameController) fxmlLoader.getController();
            connectedController = (IConnectedController) fxmlLoader.getController();
                      
            Scene scene = new Scene(root);
        
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public static GameController getGameController(){
        return game;
    }
    
    public static IConnectedController getConnectedController(){
        return connectedController;
    }
    
}
