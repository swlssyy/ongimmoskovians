/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import entity.Player;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import object.SuperObject;
import tile.TileManager;
/**
 *
 * @author khyle
 */
public class GamePanel extends JPanel implements Runnable{
    
    final int  originalTileSize = 16;
    final int scale = 3;
    
    public final int tileSize  = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    
    
    // WORLD SETTINGS
    public final int maxWorldCol = 22;
    public final int maxWorldRow = 16;
    public final int maxWorldWidth = tileSize * maxWorldCol;
    public final int maxWorldHeight = tileSize * maxWorldCol;
    
    
    
    //FPS
    int FPS = 60;
    
    //System
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    sounds sound = new sounds();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    Thread gameThread;
    
    //Entity and Object
    public Player player = new Player (this,keyH);
    public SuperObject obj[] = new SuperObject[10];

    public GamePanel() {
        
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        
    }
    
    public void setupGame(){
    
        aSetter.setObject();
        
        playMusic(1);
    
    }

    public void startGameThread() {
        
        gameThread = new Thread(this);
        gameThread.start();
        
        
    }
    
    @Override
    public void run() {
        
        
        double drawInterval = 1000000000/FPS; // 0.01666 secs
        double nextDrawTime = System.nanoTime() + drawInterval;
        
        
        while (gameThread != null) {
                
           update();
           
           repaint(); 
           
           
           
           
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;
                
                if (remainingTime < 0){
                    remainingTime = 0;
                }
                
                Thread.sleep((long) remainingTime);
                
                nextDrawTime += drawInterval;
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
       
    }
    
    public void update() {
        
     player.update();
        
    }
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
        
        
        // TILES
        tileM.draw(g2);
        
        // OBJECT
        for (int i = 0; i < obj.length; i++){
            if(obj[i] != null){
                obj[i].draw(g2, this);
            
            }
        }
        
        // PLAYER
        player.draw(g2);
        
        g2.dispose();
        
        
        
        
        
    }
    public void playMusic(int i) {
        
        sound.setFile(i);
        sound.play();
        sound.loop();
    }
    public void stopMusic() {
        
        sound.stop();
    }
    public void playSE(int i) {
        
        sound.setFile(i);
        sound.play();
    }
}
