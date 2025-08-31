/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;

import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Frein
 */
public class OBJ_Trash extends SuperObject {
    
    public OBJ_Trash(){
        
        Name = "TrashCan";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/trashcan.png"));
        
        }catch (IOException e){
            e.printStackTrace();
        
        }
       
    
    
    }
    
}
