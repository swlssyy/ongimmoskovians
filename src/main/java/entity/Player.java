/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Fridge;
import object.OBJ_Soda;

/**
 *
 * @author khyle
 */
public class Player extends entity{
    
    
    
   GamePanel gp;
   KeyHandler keyH;
   
   public final int screenX;
   public final int screenY;
   
   int hasSoda = 0;

   // what type of soda player has (0 = none, 1 = raw, 2 = cooled)
   int sodaType = 0;

   // fridge state
   boolean sodaInFridge = false;
   int fridgeSodaType = 0;   // 0 = none, 1 = raw, 2 = cooled
   long fridgeStartTime = 0;

    
   
   public Player(GamePanel gp, KeyHandler keyH) {
       
       this.gp = gp;
       this.keyH = keyH;
       
       screenX = gp.screenWidth/2 - (gp.tileSize/2);
       screenY = gp.screenHeight/2 - (gp.tileSize/2);
       
       solidArea = new Rectangle();
       solidArea.x = 8;
       solidArea.y = 16;
       
       solidAreaDefaultX = solidArea.x;
       solidAreaDefaultY = solidArea.y;
       
       solidArea.width = 32;
       solidArea.height = 32;       
               
       setDefaultValues();
       getPlayerImage();
   }
    public void setDefaultValues() {
        
        worldX = gp.tileSize * 21/2;
        worldY = gp.tileSize * 15/2;
        speed = 4;
        direction = "down";
                  
    }
    public void getPlayerImage(){
    
         try {
           
             up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
             up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
             down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
             down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
             left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
             left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
             right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
             right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
             
         } catch(IOException e){
             e.printStackTrace();
         
         }

    }

    
    public void update() {
        
        if(keyH.upPressed == true || keyH.downPressed == true 
                    || keyH.leftPressed == true || keyH.rightPressed == true){
        
             if(keyH.upPressed==true){
            direction = "up";
               
            }
            else if (keyH.downPressed == true){
            direction = "down";
                
            }
            else if (keyH.leftPressed == true){
            direction = "left";
                 
            }
            else if (keyH.rightPressed == true){
            direction = "right";
                 
            }
             
             // CHECK TILE COLLISION
             collisionOn = false;
             gp.cChecker.checkTile(this);
             
             // CHECK OBJECT COLLISION
             int objIndex = gp.cChecker.checkObject(this, true);
             pickUpObject(objIndex);
             
             
             //IF COLLISON IS FALSE , PLAYER CAN MOVE
             if(collisionOn == false){
             
                 switch(direction){
                 case"up":  worldY -= speed; break;
                 case"down":   worldY += speed; break;    
                 case"left":   worldX -= speed; break;    
                 case"right":   worldX += speed; break;    
                     

                 }
             
             }

            spriteCounter++;
            if(spriteCounter > 12){
            if(spriteNum == 1){
                spriteNum =2;
            
            }
            else if (spriteNum == 2){
                spriteNum = 1;
            
            }
            spriteCounter = 0;
        }
    }    
 }
    
    public void pickUpObject (int i){
         
        if (i != 999) {
        String objectName = gp.obj[i].Name;

        switch (objectName) {
            case "Soda":
                hasSoda++;
                gp.obj[i] = null; // remove soda from map
                System.out.println("Picked up a soda. hasSoda=" + hasSoda);
                break;

            case "Fridge":
             if (hasSoda > 0 && !sodaInFridge) {
            sodaInFridge = true;
            fridgeSodaType = sodaType;   // raw or cooled
            hasSoda = 0;
            sodaType = 0;
        
            if (fridgeSodaType == 1) { // raw soda → start cooling timer
             fridgeStartTime = System.currentTimeMillis();
             System.out.println("Raw soda placed in fridge. Cooling...");
            } else {
              System.out.println("Cooled soda stored in fridge.");
            }
         }

            // --- If soda is inside fridge ---
            else if (sodaInFridge) {
            long now = System.currentTimeMillis();

            // Raw soda becomes cooled after 5s
            if (fridgeSodaType == 1 && now - fridgeStartTime >= 5000) {
            fridgeSodaType = 2;
            System.out.println("Soda cooled in fridge!");
            }

             // Take soda back (either cooled or raw if not enough time)
            if (hasSoda == 0) {
            hasSoda = 1;
            sodaType = fridgeSodaType; // pick back same type
            sodaInFridge = false;
            fridgeSodaType = 0;

            if (sodaType == 2) {
                System.out.println("Took cooled soda from fridge.");
            } else {
                System.out.println("Took raw soda from fridge.");
                }
            }
        }
         break;

            case "TrashCan":
                if (hasSoda > 0) {
                    // throw soda into trash
                    hasSoda--;
                    OBJ_Soda sodaTrash = new OBJ_Soda();
                    sodaTrash.worldX = gp.obj[i].worldX; // same as trash location
                    sodaTrash.worldY = gp.obj[i].worldY;
                    // Optionally add soda to the object list if you want to show it visually
                    System.out.println("Threw soda in trash. hasSoda=" + hasSoda);
                }
                break;
        }
    }
    }
    
    public void draw(Graphics2D g2) {
        

        BufferedImage image = null;
        
        switch(direction){
            case "up":
                if (spriteNum == 1){
                    image = up1;
                }
                if (spriteNum == 2){
                    image = up2;
                }
                
                 break;
            case "down":
                 if (spriteNum == 1){
                    image = down1;
                }
                if (spriteNum == 2){
                    image = down2;
                }
                 break;     
            case "left":
                 if (spriteNum == 1){
                    image = left1;
                }
                if (spriteNum == 2){
                    image = left2;
                }
                 break;
            case "right":
                 if (spriteNum == 1){
                    image = right1;
                }
                if (spriteNum == 2){
                    image = right2;
                }
                 break;     
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
