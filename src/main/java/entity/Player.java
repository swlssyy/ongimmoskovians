package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.KeyHandler;

public class Player extends entity {

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public int hasSoda = 0;          // 0 = none, 1 = carrying
    public int sodaType = 0;         // 0 = none, 1 = raw, 2 = cooled

    // fridge state
    boolean sodaInFridge = false;
    int fridgeSodaType = 0;          // 0 = none, 1 = raw, 2 = cooled
    long fridgeStartTime = 0;
    boolean fridgeJustCooled = false;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 21 / 2;
        worldY = gp.tileSize * 15 / 2;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        up1 = setup("/player/boy_up_1");
        up2 = setup("/player/boy_up_2");
        down1 = setup("/player/boy_down_1");
        down2 = setup("/player/boy_down_2");
        left1 = setup("/player/boy_left_1");
        left2 = setup("/player/boy_left_2");
        right1 = setup("/player/boy_right_1");
        right2 = setup("/player/boy_right_2");
    }

    public void update() {
        // --- MOVEMENT ---
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) direction = "up";
            else if (keyH.downPressed) direction = "down";
            else if (keyH.leftPressed) direction = "left";
            else if (keyH.rightPressed) direction = "right";

            collisionOn = false;
            gp.cChecker.checkTile(this);

            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            if (!collisionOn) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            spriteCounter++;
            if (spriteCounter > 12) {
                spriteNum = (spriteNum == 1 ? 2 : 1);
                spriteCounter = 0;
            }
        }

        // --- ALWAYS CHECK OBJECTS, EVEN IF NOT MOVING ---
        int objIndex = gp.cChecker.checkObject(this, true);
        if (objIndex != 999) {
            pickUpObject(objIndex);
        }

        // --- AUTO FRIDGE COOLING CHECK ---
        if (sodaInFridge && fridgeSodaType == 1) {
            long now = System.currentTimeMillis();
            if (now - fridgeStartTime >= 5000 && !fridgeJustCooled) {
                fridgeSodaType = 2;
                fridgeJustCooled = true;
                gp.ui.showMessage("The soda in the fridge has cooled!");
                System.out.println("The soda in the fridge has cooled!");
            }
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {
            String objectName = gp.obj[i].Name;

            switch (objectName) {
                case "Soda" -> {
                    if (keyH.ePressed) {
                        gp.playSE(0);
                        hasSoda = 1;
                        sodaType = 1; // raw soda
                        gp.obj[i] = null;
                        gp.ui.showMessage("Picked up a soda.");
                        System.out.println("You picked up a raw soda.");
                        keyH.ePressed = false;
                    }
                }

                case "Fridge" -> handleFridge();

                case "TrashCan" -> {
                    if (keyH.ePressed && hasSoda > 0) {
                        gp.playSE(2);
                        hasSoda = 0;
                        sodaType = 0;
                        gp.ui.showMessage("You threw the soda away.");
                        System.out.println("You threw the soda in the trash.");
                        keyH.ePressed = false;
                    }
                }

                case "Counter" -> handleCounter();
            }
        }
    }

    private void handleFridge() {
        // --- PUT soda in fridge (E) ---
        if (keyH.ePressed) {
            if (hasSoda > 0 && !sodaInFridge) {
                sodaInFridge = true;
                fridgeSodaType = sodaType;
                hasSoda = 0;
                sodaType = 0;
                fridgeJustCooled = false;

                if (fridgeSodaType == 1) {
                    fridgeStartTime = System.currentTimeMillis();
                    gp.ui.showMessage("Placed raw soda in the fridge. Cooling...");
                    System.out.println("You placed a raw soda in the fridge. It will cool in 5 seconds.");
                } else {
                    gp.ui.showMessage("Placed cooled soda in the fridge.");
                    System.out.println("You placed a cooled soda in the fridge.");
                }
            }
            keyH.ePressed = false;
        }

        // --- TAKE soda from fridge (Q) ---
        if (keyH.qPressed) {
            if (sodaInFridge) {
                if (hasSoda == 0) {
                    hasSoda = 1;
                    sodaType = fridgeSodaType;
                    sodaInFridge = false;
                    fridgeSodaType = 0;

                    if (sodaType == 2) {
                        gp.ui.showMessage("You took a cooled soda from the fridge.");
                        System.out.println("You took a cooled soda from the fridge.");
                    } else {
                        gp.ui.showMessage("You took a raw soda from the fridge.");
                        System.out.println("You took a raw soda from the fridge.");
                    }
                }
            }
            keyH.qPressed = false;
        }
    }

    private void handleCounter() {
        if (keyH.ePressed) {
            int counterX = 9 * gp.tileSize;
            int counterY = 11 * gp.tileSize;

            boolean foundCustomer = false;

            for (int i = 0; i < gp.npc.length; i++) {
                if (gp.npc[i] instanceof QueuedCustomer qc &&
                        qc.worldX == counterX && qc.worldY == counterY &&
                        qc.isActive() && !qc.isLeaving()) {

                    foundCustomer = true;

                    if (hasSoda > 0 && sodaType == 2) {
                        qc.interact();
                        hasSoda--;
                        sodaType = 0;
                        gp.ui.showMessage("You served a cooled soda!");
                        System.out.println("You served a cooled soda to the customer.");
                    } else {
                        gp.ui.showMessage("You need a cooled soda to serve!");
                        System.out.println("You tried to serve, but you don't have a cooled soda.");
                    }
                    break;
                }
            }

            if (!foundCustomer) {
                System.out.println("There is no customer at the counter.");
            }

            keyH.ePressed = false;
        }
    }

    public void interactNPC(int i) {
        if (i != 999 && gp.keyH.enterPressed) {
            gp.gameState = gp.dialogueState;
            gp.npc[i].speak();
        }
        gp.keyH.enterPressed = false;
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = switch (direction) {
            case "up" -> (spriteNum == 1 ? up1 : up2);
            case "down" -> (spriteNum == 1 ? down1 : down2);
            case "left" -> (spriteNum == 1 ? left1 : left2);
            case "right" -> (spriteNum == 1 ? right1 : right2);
            default -> null;
        };
        g2.drawImage(image, screenX, screenY, null);
    }
}
