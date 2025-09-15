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
import main.UtilityTool;

/**
 *
 * @author Frein
 */
public class TileManager {

   GamePanel gp;
   public Tile[] tile;
   public int mapTileNum[][];

   public TileManager(GamePanel gp) {

      this.gp = gp;

      tile = new Tile[35];
      mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

      getTileImage();
      loadMap("/maps/world01.txt");
   }

   public void getTileImage() {

      // dirt
      setup(0, "dirt", false);

      // Edge Down
      setup(1, "edgedown", false);

      // Edge Up
      setup(2, "edgeup", false);

      // Edge Right
      setup(3, "edgeleft", false);

      // Edge Left
      setup(4, "edgeright", false);

      // Edge North West (Top RIGHT)
      setup(5, "edgeNW", false);

      // Edge North East (Top LEFT)
      setup(6, "edgeNE", false);

      // Edge South East (Bottom RIGHT)
      setup(7, "edgeSE", false);

      // Edge South West (Bottom LEFT)
      setup(8, "edgeSW", false);

      // Edgeconnector TR
      setup(9, "edgeconnector", false);

      // Edgeconnector TL
      setup(10, "edgeconnectorTL", false);

      // Edgeconnector BR
      setup(11, "edgeconnectorBR", false);

      // Edgeconnector BL
      setup(12, "edgeconnectorBL", false);

      // Black Tile
      setup(13, "blacktile", false);

      // Counter Toaster (solid)
      setup(14, "blender", true);

      // Toaster Top (solid)
      setup(15, "ctoaster", true);

      // Cupboard
      setup(16, "cupboard", false);

      // Blender Top (solid)
      setup(17, "cupboardblender", true);

      // Oven Base (solid)
      setup(18, "cupboardred", true);

      // Ref Base (solid)
      setup(19, "fridge", true);

      // Fryer Top (solid)
      setup(20, "fryer", true);

      // Counter (solid)
      setup(21, "horizontaltable", true);

      // Utensils (solid)
      setup(22, "knivesontable", true);

      // Oven Top (solid)
      setup(23, "microwave", true);

      // Oven Base (solid)
      setup(24, "oven", true);

      // Stove
      setup(25, "stove", false);

      // Counter Bottom Right (solid)
      setup(26, "tablebotright", true);

      // Toaster (solid)
      setup(27, "tabletoaster", true);

      // Counter Table (solid)
      setup(28, "tabletopleft", true);

      // Top Fridge
      setup(29, "topfridge", false);

      // Counter Left (solid)
      setup(30, "verticaltableleft", true);

      // Counter Right (solid)
      setup(31, "verticaltableright", true);

      // White Tile
      setup(32, "whitetile", false);

      // Counter Top Right (solid)
      setup(33, "tabletopright", true);

      // Table Bottom Left
      setup(34, "tablebotleft", false);
   }

   public void setup(int index, String imageName, boolean collision) {

      UtilityTool uTool = new UtilityTool();

      try {
         tile[index] = new Tile();
         tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
         tile[index].image = uTool.scaledImage(tile[index].image, gp.tileSize, gp.tileSize);
         tile[index].collision = collision;

      } catch (IOException e) {
         e.printStackTrace();
      }

   }

   public void loadMap(String filePath) {
      try {
         InputStream is = getClass().getResourceAsStream(filePath);
         BufferedReader br = new BufferedReader(new InputStreamReader(is));

         int row = 0;

         while (row < gp.maxWorldRow) {
            String line = br.readLine();
            if (line == null)
               break; // stop if file ends early

            String numbers[] = line.split(" ");

            for (int col = 0; col < gp.maxWorldCol && col < numbers.length; col++) {
               int num = Integer.parseInt(numbers[col]);
               mapTileNum[col][row] = num;
            }
            row++;
         }

         br.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public void draw(Graphics2D g2) {

      int worldCol = 0;
      int worldRow = 0;

      while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

         int tileNum = mapTileNum[worldCol][worldRow];

         int worldX = worldCol * gp.tileSize;
         int worldY = worldRow * gp.tileSize;
         int screenX = worldX - gp.player.worldX + gp.player.screenX;
         int screenY = worldY - gp.player.worldY + gp.player.screenY;

         if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
               worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
               worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
               worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            g2.drawImage(tile[tileNum].image, screenX, screenY, null);

         }

         worldCol++;

         if (worldCol == gp.maxWorldCol) {
            worldCol = 0;
            worldRow++;

         }
      }
   }
}
