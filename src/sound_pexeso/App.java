/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sound_pexeso;

import controllers.GameController;
import controllers.LobbyController;
import controllers.WaitingController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamCorruptedException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.imageio.IIOException;

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
    
    private static LobbyController lobby;
    private static WaitingController waiting;
    
    
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
        
        Scanner inputReader = new Scanner(System.in);
       /* while(true){
            System.out.print(">");
            String line = inputReader.nextLine();
            System.out.println("");
            tcpClient.send(line);
        } */
        
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
            
            lobby = (LobbyController) fxmlLoader.getController();
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
            
            waiting = (WaitingController) fxmlLoader.getController();
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
                      
            Scene scene = new Scene(root);
        
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public static LobbyController getLobbyController(){   
        return lobby;
    }
    public static WaitingController getWaitingController(){
        return waiting;
    }
    
}
