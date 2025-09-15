package entity;

import main.GamePanel;
import main.QueueManager;

/**
 * QueuedCustomer: NPC that follows a shared queue path and can leave the queue.
 */
public abstract class QueuedCustomer extends entity {

    private QueueManager manager;
    private int[][] path;
    private int pathIndex = 0;   // waypoint pointer
    private boolean leaving = false;

    public QueuedCustomer(GamePanel gp) {
        super(gp);
        direction = "down";
        speed = 1;
    }

    // Called by QueueManager when registering this NPC
    public final void initQueue(QueueManager manager, int[][] path) {
        this.manager = manager;
        this.path = path;
        this.pathIndex = 0;
    }

    public final void resetPathIndex() { this.pathIndex = 0; }
    public int getPathIndex() { return pathIndex; }

    public final boolean isActive() { return this.active; }
    public final void setActive(boolean value) { this.active = value; }

    public boolean isLeaving() { return leaving; }

    // Interaction API (immediate leave)
    public void interact() {
        if (leaving) return;

        // Tell manager the front slot is free (this spawns next)
        if (manager != null) {
            manager.onFinished(this);
        }

        // Detach from manager
        manager = null;

        // Start leaving to (1,11) → (1,18)
        startLeavingTo();
    }

    // Leaving path definition
    public void startLeavingTo() {
        this.leaving = true;

        // Two-step exit path: first (1,11), then (1,18)
        this.path = new int[][] {
            {1, 11},
            {1, 18}
        };
        this.pathIndex = 0;

        this.active = true;
        this.speed = 1;
    }

    private boolean atTile(int col, int row) {
        int tx = col * gp.tileSize;
        int ty = row * gp.tileSize;
        int tol = Math.max(2, gp.tileSize / 4);
        return Math.abs(worldX - tx) <= tol && Math.abs(worldY - ty) <= tol;
    }

    private void moveTowards(int tx, int ty) {
        if (Math.abs(worldX - tx) > Math.abs(worldY - ty)) {
            direction = (worldX > tx) ? "left" : "right";
        } else {
            direction = (worldY > ty) ? "up" : "down";
        }
        speed = 1;
    }

    @Override
    public void setAction() {
        if (!active || path == null || pathIndex >= path.length) return;

        int targetCol = path[pathIndex][0];
        int targetRow = path[pathIndex][1];
        int targetX = targetCol * gp.tileSize;
        int targetY = targetRow * gp.tileSize;

        if (!atTile(targetCol, targetRow)) {
            boolean tileFree = !gp.isTileOccupied(targetCol, targetRow, this);
            boolean allowedByQueue = manager == null || manager.canProceed(this, pathIndex);

            if (tileFree && allowedByQueue) {
                moveTowards(targetX, targetY);
            } else {
                speed = 0;
            }
            return;
        }

        // Reached a waypoint
        if (manager != null) manager.onReachStep(this, pathIndex);
        pathIndex++;

        if (pathIndex >= path.length) {
            // Finished leaving path
            speed = 0;
            direction = "down";

            if (leaving) {
                gp.removeNpc(this); // sets npc[i] = null
                active = false;
            } else {
                if (manager != null) manager.onFinished(this);
                active = false;
            }
        }
    }
}
