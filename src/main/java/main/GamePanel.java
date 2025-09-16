package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import entity.QueuedCustomer;
import entity.entity;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // WORLD SETTINGS
    public final int maxWorldCol = 25;
    public final int maxWorldRow = 19;
    public final int maxWorldWidth = tileSize * maxWorldCol;
    public final int maxWorldHeight = tileSize * maxWorldCol;

    public QueueManager queueManager;

    // FPS
    int FPS = 60;

    // System
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    sounds music = new sounds();
    sounds se = new sounds();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;

    // Entity and Object
    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[10];
    public entity npc[] = new entity[10];

    // GAME STATE
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();
        playMusic(1);
        gameState = playState;

        queueManager = new QueueManager(this, QUEUE_PATH, SPAWN_TRIGGER_INDEX);

        // Register NPCs
        aSetter.setNPC();

        // Start queue
        queueManager.startQueue();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) remainingTime = 0;

                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        if (gameState == playState) {
            // PLAYER
            player.update();

            // NPC
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].update();
                }
            }

            // Interaction with E
            if (keyH.ePressed) {
                int npcIndex = getNpcIndexFacingPlayer();

                if (npcIndex != 999 && npc[npcIndex] instanceof QueuedCustomer qc) {
                    qc.interact(); // Leaves & triggers next one
                   
                } 

                keyH.ePressed = false;
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);

        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) obj[i].draw(g2, this);
        }

        for (int i = 0; i < npc.length; i++) {
            if (npc[i] != null) npc[i].draw(g2);
        }

        player.draw(g2);
        ui.draw(g2);

        g2.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic() { music.stop(); }
    public void pauseMusic() { if (music.clip != null) music.clip.stop(); }
    public void resumeMusic() {
        if (music.clip != null) {
            music.clip.start();
            music.clip.loop(javax.sound.sampled.Clip.LOOP_CONTINUOUSLY);
        }
    }
    public void playSE(int i) { se.setFile(i); se.play(); }

    public static final int[][] EXIT_PATH = {
        {8,10}, {7,10}, {6,10}, {5,10},
        {4,10}, {3,10}, {2,10}, {1,10},
        {1,9}
    };

    public static final int[][] QUEUE_PATH = {
        {9,17}, {2,17}, {2,16}, {9,16},
        {9,15}, {2,15}, {2,14}, {9,14}, {9,10}
    };

    public static final int SPAWN_TRIGGER_INDEX = 1;

    public boolean isTileOccupied(int col, int row, entity requester) {
        int tileX = col * tileSize;
        int tileY = row * tileSize;
        int tol = tileSize / 4;

        for (entity e : npc) {
            if (e == null || e == requester) continue;
            if (e instanceof entity && e.active) {
                if (Math.abs(e.worldX - tileX) <= tol && Math.abs(e.worldY - tileY) <= tol) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getNpcIndexFacingPlayer() {
        int tol = tileSize / 4;
        int fx = player.worldX;
        int fy = player.worldY;

        switch (player.direction) {
            case "up": fy -= tileSize; break;
            case "down": fy += tileSize; break;
            case "left": fx -= tileSize; break;
            case "right": fx += tileSize; break;
        }

        for (int i = 0; i < npc.length; i++) {
            if (npc[i] == null) continue;
            if (!npc[i].active) continue;
            if (Math.abs(npc[i].worldX - fx) <= tol && Math.abs(npc[i].worldY - fy) <= tol) {
                return i;
            }
        }
        return 999;
    }

    public void removeNpc(entity e) {
        for (int i = 0; i < npc.length; i++) {
            if (npc[i] == e) {
                npc[i] = null;
                break;
            }
        }
    }
}
