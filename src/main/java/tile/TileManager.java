/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import main.GamePanel;

/**
 *
 * @author Frein
 */
public class TileManager {
    
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];
    
    
    public TileManager(GamePanel gp){
    
       this.gp = gp;
       
       tile = new Tile[35];
       mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
       
       getTileImage();
       loadMap("/maps/world01.txt");
    }
    
    
    public void getTileImage(){
        
        
        try {
            
             //DIRT
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/dirt.png"));
            
            //Edge Down
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/edgedown.png"));
            
            // Edge UP
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/edgeup.png"));
            
            // Edge right
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/edgeleft.png"));
            
            // Edge left
            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/edgeright.png"));
            
            // Edge North West (Top RIGHT)
            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/edgeNW.png"));
            
            // Edge North East (Top LEFT)
            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/edgeNE.png"));
            
            // Edge South East (Bottom RIGHT)
            tile[7] = new Tile();
            tile[7].image = ImageIO.read(getClass().getResourceAsStream("/tiles/edgeSE.png"));
            
            // Edge South West (Bottom LEFT)
            tile[8] = new Tile();
            tile[8].image = ImageIO.read(getClass().getResourceAsStream("/tiles/edgeSW.png"));
            
            // Edgeconnector TR
            tile[9] = new Tile();
            tile[9].image = ImageIO.read(getClass().getResourceAsStream("/tiles/edgeconnector.png"));
            
            // EdgeconnectorTL
            tile[10] = new Tile();
            tile[10].image = ImageIO.read(getClass().getResourceAsStream("/tiles/edgeconnectorTL.png"));
            
            // EdgeconnectorBR
            tile[11] = new Tile();
            tile[11].image = ImageIO.read(getClass().getResourceAsStream("/tiles/edgeconnectorBR.png"));
            
            // EdgeconnectorBL
            tile[12] = new Tile();
            tile[12].image = ImageIO.read(getClass().getResourceAsStream("/tiles/edgeconnectorBL.png"));
            
            //
            tile[13] = new Tile();
            tile[13].image = ImageIO.read(getClass().getResourceAsStream("/tiles/blacktile.png"));
            
            //COUNTER TOASTER
         //   // SOLID TILE         //   // SOLID TILE
            tile[14] = new Tile();
            tile[14].image = ImageIO.read(getClass().getResourceAsStream("/tiles/blender.png"));
            tile[14].collision = true;
            
            //TOASTER TOP
         //   // SOLID TILE
            tile[15] = new Tile();
            tile[15].image = ImageIO.read(getClass().getResourceAsStream("/tiles/ctoaster.png"));
            tile[15].collision = true;
            
            //
            tile[16] = new Tile();
            tile[16].image = ImageIO.read(getClass().getResourceAsStream("/tiles/cupboard.png"));
            
            //BLENDERTOP
         //   //SOLID TILE
            tile[17] = new Tile();
            tile[17].image = ImageIO.read(getClass().getResourceAsStream("/tiles/cupboardblender.png"));
            tile[17].collision = true;
            
            //OVEN BASE
         //   //SOLID TILE
            tile[18] = new Tile();
            tile[18].image = ImageIO.read(getClass().getResourceAsStream("/tiles/cupboardred.png"));
            tile[18].collision = true;
            
            //Ref Base
          //  //SOLID TILE
            tile[19] = new Tile();
            tile[19].image = ImageIO.read(getClass().getResourceAsStream("/tiles/fridge.png"));
            tile[19].collision = true;
            
            //FRYER TOP
         //   // SOLID TILE
            tile[20] = new Tile();
            tile[20].image = ImageIO.read(getClass().getResourceAsStream("/tiles/fryer.png"));
            tile[20].collision = true;
            
            // COUNTER
          //  // SOLID TILE
            tile[21] = new Tile();
            tile[21].image = ImageIO.read(getClass().getResourceAsStream("/tiles/horizontaltable.png"));
            tile[21].collision = true;
            
            //UTENSILS
         //   //SOLID TILE
            tile[22] = new Tile();
            tile[22].image = ImageIO.read(getClass().getResourceAsStream("/tiles/knivesontable.png"));
            tile[22].collision = true;
            
            //OVEN TOP
         //   //SOLID TILE
            tile[23] = new Tile();
            tile[23].image = ImageIO.read(getClass().getResourceAsStream("/tiles/microwave.png"));
            tile[23].collision = true;
            
            //OVEN BASE
         //   // SOLID TILE
            tile[24] = new Tile();
            tile[24].image = ImageIO.read(getClass().getResourceAsStream("/tiles/oven.png"));
            tile[24].collision = true;
            
            //
            tile[25] = new Tile();
            tile[25].image = ImageIO.read(getClass().getResourceAsStream("/tiles/stove.png"));
            
            //COUNTER BOTTOM RIGHT
         //   // SOLID TILE
            tile[26] = new Tile();
            tile[26].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tablebotright.png"));
            tile[26].collision = true;
            
            //TOASTER
          //   // SOLID TILE
            tile[27] = new Tile();
            tile[27].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tabletoaster.png"));
            tile[27].collision = true;
            
            //COUNTER TABLE
         //   // SOLID TILE
            tile[28] = new Tile();
            tile[28].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tabletopleft.png"));
            tile[28].collision = true;
            
            //
            tile[29] = new Tile();
            tile[29].image = ImageIO.read(getClass().getResourceAsStream("/tiles/topfridge.png"));
            
            //COUNTER LEFT
          //  // SOLID TILE
            tile[30] = new Tile();
            tile[30].image = ImageIO.read(getClass().getResourceAsStream("/tiles/verticaltableleft.png"));
            tile[30].collision = true;
            
            //COUNTER RIGHT
         //   // SOLID TILE
            tile[31] = new Tile();
            tile[31].image = ImageIO.read(getClass().getResourceAsStream("/tiles/verticaltableright.png"));
            tile[31].collision = true;
            
            //
            tile[32] = new Tile();
            tile[32].image = ImageIO.read(getClass().getResourceAsStream("/tiles/whitetile.png"));
            
            //COUNTER TOP RIGHT
         //   // SOLID TILE
            tile[33] = new Tile();
            tile[33].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tabletopright.png"));
            tile[33].collision = true;
            
            //
            tile[34] = new Tile();
            tile[34].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tablebotleft.png"));
            
       
        
        } catch(IOException e){
            e.printStackTrace();
        }

    }
    
    public void loadMap (String filePath) {
        
        
        try {
            
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader (new InputStreamReader(is));
            
            int col = 0;
            int row = 0;
            
            while (col < gp.maxWorldCol && row < gp.maxWorldRow){
            
                String  line = br.readLine();
                
                while (col < gp.maxWorldCol){
                    
                    String numbers[] = line.split(" ");
                
                    int num = Integer.parseInt(numbers[col]);
                    
                    mapTileNum[col][row] = num;
                    col++;
                   
                }
                if (col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            br.close();
            
        } catch(Exception e){
            
        }
        
    }
    public void draw(Graphics2D g2){
        
        int worldCol = 0;
        int worldRow = 0;
       
        
        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            
            int tileNum = mapTileNum [worldCol][worldRow];
            
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;
           
            
            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize< gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize> gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize< gp.player.worldY + gp.player.screenY) {
                
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            
            }
           
            worldCol++;
            
             if(worldCol == gp.maxWorldCol) {
                 worldCol = 0;
                 worldRow++;
              
             }
        }
    }
}
