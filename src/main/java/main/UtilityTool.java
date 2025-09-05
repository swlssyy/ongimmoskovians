package main;

import java.awt.image.BufferedImage;

public class UtilityTool {

    public BufferedImage scaledImage (BufferedImage original, int width, int height){

        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        java.awt.Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();

        return scaledImage; 

    }

}
