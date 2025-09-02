/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.awt.PopupMenu;
import javax.swing.JFrame;

/**
 *
 * @author khyle
 */
public class main {
        public static void main(String[] args) {
        
        JFrame window = new  JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Hell's Kitchen");
        window.setUndecorated(true);
        
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        
        window.pack();
                
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}

