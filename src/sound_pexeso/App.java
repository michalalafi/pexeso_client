/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sound_pexeso;

import java.io.IOException;
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
    
    private static Stage primaryStage;
    
    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setTitle("Sound pexeso");
        
        menu();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
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
            
            Scene scene = new Scene(root);
        
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
}
