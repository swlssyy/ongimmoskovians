/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author khyle
 */
public class sounds {
    
    Clip clip;
    URL soundURL[] = new URL[30];
    
    public sounds() {
        
        soundURL[0] = getClass().getResource("/sounds/soda.wav");
        soundURL[1] = getClass().getResource("/sounds/musex.wav");
        soundURL[2] = getClass().getResource("/sounds/trashcan.wav");
      
    }
    public void setFile(int i) {
        
        try {
            
                AudioInputStream ais =  AudioSystem.getAudioInputStream(soundURL[i]);
                clip = AudioSystem.getClip();
                clip.open(ais);
            
        }catch(Exception e) {
             
        }
    }
    public void play() {
        
        clip.start();
        
    }
    public void loop() {
        
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        
    }
      public void stop() {
        
          clip.stop();
          
    }  
}
