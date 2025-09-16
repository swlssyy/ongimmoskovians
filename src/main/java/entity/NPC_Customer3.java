package entity;

import main.GamePanel;

public class NPC_Customer3 extends QueuedCustomer {

    public NPC_Customer3(GamePanel gp) {
        super(gp);
        getImage();
        setDialogue();
    }

    public void getImage() {
       up1 = setup("/NPC/Fire_Up1.png");
       up2 = setup("/NPC/Fire_Up2.png");
       down1 = setup("/NPC/Fire_Down1.png");
       down2 = setup("/NPC/Fire_Down2.png");
       left1 = setup("/NPC/Fire_Left1.png");
       left2 = setup("/NPC/Fire_Left2.png");
       right1 = setup("/NPC/Fire_Right1.png");
       right2 = setup("/NPC/Fire_Right2.png");
   }

    public void setDialogue() {
        dialogues[0] = "Hello, can I order?";
        dialogues[1] = "One soda, please.";
        dialogues[2] = "Thank you!";
        dialogues[3] = "Have a nice day!";
    }
}