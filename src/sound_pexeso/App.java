/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sound_pexeso;

import controllers.GameController;
import controllers.IConnectedController;
import controllers.LobbyController;
import controllers.WaitingController;
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
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){      
        launch(args);     
    }
    
    @Override
    public void stop(){
        System.out.println("Program se zavira");
        Platform.exit();
    }
    public static void menu(){
        try{
            
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(MENU_FXML));
            Parent root = fxmlLoader.load();
                       
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
            //Pozadat server o pocet puzzli
            GameController.numberOfPexesos = 8;
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
