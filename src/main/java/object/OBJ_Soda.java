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
public class OBJ_Soda extends SuperObject{
    
    public OBJ_Soda(){
        
        Name = "Soda";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/sodacan.png"));
        
        }catch (IOException e){
            e.printStackTrace();
        
        }
    
    
    }
    
}
