/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import object.OBJ_Fridge;
import object.OBJ_Soda;
import object.OBJ_Trash;

/**
 *
 * @author Frein
 */
public class AssetSetter {
    
    GamePanel gp;
    
    public AssetSetter(GamePanel gp){
        this.gp = gp;

    }
    
    public void setObject(){
        
        gp.obj[0] = new OBJ_Soda();
        gp.obj[0].worldX = 14 * gp.tileSize;
        gp.obj[0].worldY = 7 * gp.tileSize;
        
        
        gp.obj[1] = new OBJ_Soda();
        gp.obj[1].worldX = 12 * gp.tileSize;
        gp.obj[1].worldY = 7 * gp.tileSize;
        
        gp.obj[2] = new OBJ_Fridge();
        gp.obj[2].worldX = 8 * gp.tileSize;
        gp.obj[2].worldY = 4 * gp.tileSize;
        
        gp.obj[3] = new OBJ_Trash();
        gp.obj[3].worldX = 7 * gp.tileSize;
        gp.obj[3].worldY = 9 * gp.tileSize;
    }
    
}
