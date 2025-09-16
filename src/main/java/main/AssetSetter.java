package main;

import java.util.Random;

import entity.NPC_Customer1;
import entity.NPC_Customer2;
import entity.NPC_Customer3;
import entity.QueuedCustomer;
import entity.entity;
import object.OBJ_Fridge;
import object.OBJ_Soda;
import object.OBJ_Trash;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.obj[0] = new OBJ_Soda(gp);
        gp.obj[0].worldX = 14 * gp.tileSize;
        gp.obj[0].worldY = 7 * gp.tileSize;

        gp.obj[1] = new OBJ_Soda(gp);
        gp.obj[1].worldX = 12 * gp.tileSize;
        gp.obj[1].worldY = 7 * gp.tileSize;

        gp.obj[2] = new OBJ_Fridge(gp);
        gp.obj[2].worldX = 8 * gp.tileSize;
        gp.obj[2].worldY = 4 * gp.tileSize;

        gp.obj[3] = new OBJ_Trash(gp);
        gp.obj[3].worldX = 7 * gp.tileSize;
        gp.obj[3].worldY = 9 * gp.tileSize;
    }
    

    public void setNPC() {
   Random rand = new Random();
   for (int i = 0; i < 10; i++) {
       QueuedCustomer npc;
       int type = rand.nextInt(3); // 0,1,2
       switch (type) {
           case 0 -> npc = new NPC_Customer1(gp);
           case 1 -> npc = new NPC_Customer2(gp);
           default -> npc = new NPC_Customer3(gp);
       }
       gp.npc[i] = npc;              // store in array so GamePanel can update/draw
       gp.queueManager.register(npc); // register for queue pathing
   }
    }
}
