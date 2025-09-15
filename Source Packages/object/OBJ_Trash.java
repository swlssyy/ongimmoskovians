/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;

import java.io.IOException;
import javax.imageio.ImageIO;

import main.GamePanel;

/**
 *
 * @author Frein
 */
public class OBJ_Trash extends SuperObject {

    GamePanel gp;

    public OBJ_Trash(GamePanel gp) {

        Name = "TrashCan";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/trashcan.png"));
            uTool.scaledImage(image, gp.tileSize, gp.tileSize);

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

}
