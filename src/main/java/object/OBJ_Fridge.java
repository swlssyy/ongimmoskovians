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
public class OBJ_Fridge extends SuperObject {
    
    
    public OBJ_Fridge(){
        
        Name = "Fridge";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/fridge.png"));
        
        }catch (IOException e){
            e.printStackTrace();
        
        }
    
    
    }
    
    
}
