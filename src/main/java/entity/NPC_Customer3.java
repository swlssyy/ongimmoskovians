package entity;

import main.GamePanel;

public class NPC_Customer3 extends QueuedCustomer {

    public NPC_Customer3(GamePanel gp) {
        super(gp);
        getImage();
        setDialogue();
    }

    public void getImage() {
        up1 = setup("/NPC/NPCup1");
        up2 = setup("/NPC/NPCup2");
        down1 = setup("/NPC/NPCdown1");
        down2 = setup("/NPC/NPCdown2");
        left1 = setup("/NPC/NPCleft1");
        left2 = setup("/NPC/NPCleft2");
        right1 = setup("/NPC/NPCright1");
        right2 = setup("/NPC/NPCright2");
    }

    public void setDialogue() {
        dialogues[0] = "Hello, can I order?";
        dialogues[1] = "One soda, please.";
        dialogues[2] = "Thank you!";
        dialogues[3] = "Have a nice day!";
    }
}