/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author michal
 */
public class SoundPlayer {
    private static MediaPlayer mediaPlayer;
    
    public static void playSound(String soundPath){
        System.out.println("SoundPath: "+soundPath);
        Media sound = new Media(new File(soundPath).toURI().toString());
        
        mediaPlayer = new MediaPlayer(sound);
        
        mediaPlayer.setStopTime(new Duration(3000));
        mediaPlayer.play();
        
    }
}
