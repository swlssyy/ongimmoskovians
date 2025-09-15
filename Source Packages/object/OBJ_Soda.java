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
public class OBJ_Soda extends SuperObject {

    GamePanel gp;

    public OBJ_Soda(GamePanel gp) {

        Name = "Soda";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/sodacan.png"));
            uTool.scaledImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

}
