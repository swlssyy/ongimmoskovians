/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.OBJ_Soda;

/**
 *
 * @author khyle
 */ 
public class UI {
    
    GamePanel gp;
    Graphics2D g2;
    Font arial_20;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;


    
    public UI(GamePanel gp) {
        this.gp =gp;
        
        arial_20 = new Font("Arial", Font.PLAIN, 20);
        OBJ_Soda key = new OBJ_Soda();
        keyImage = key.image;
    }
    
    public void showMessage(String text) {
        
        message = text;
        messageOn = true;
        
    }
    
    public void draw(Graphics2D g2) {
        
        this.g2 = g2;

        g2.setFont(arial_20);
        g2.setColor(Color.white); 

        if(gp.gameState == gp.playState) {
            //do playstate stuff
        }
        if(gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
    }
    public void drawPauseScreen() {

        g2.setFont(g2.getFont().deriveFont(40F));
        String text = "PAUSED";
        int x = gp.screenWidth/2 - g2.getFontMetrics().stringWidth(text)/2;
        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
    }
    



    public void drawhud(Graphics2D g2) {
        
        g2.setFont(arial_20);
        g2.setColor(Color.white);
        g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
        g2.drawString("x"+ gp.player.hasSoda, 74, 55);

        if(messageOn == true) {

            g2.setFont(g2.getFont().deriveFont(20F));
            g2.drawString(message, 30, 250);

            messageCounter++;
            if(messageCounter > 120) {
                messageCounter = 0;
                messageOn = false;
            }   
        }
    }
}
