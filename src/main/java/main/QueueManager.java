package main;

import java.util.ArrayList;
import java.util.List;

import entity.QueuedCustomer;

public class QueueManager {

    private final GamePanel gp;
    private final int[][] path;
    private final int spawnTriggerIndex; // when customer hits this waypoint, spawn next
    private final List<QueuedCustomer> customers = new ArrayList<>();

    public QueueManager(GamePanel gp, int[][] path, int spawnTriggerIndex) {
        this.gp = gp;
        this.path = path;
        this.spawnTriggerIndex = spawnTriggerIndex;
    }

    public void register(QueuedCustomer c) {
        c.initQueue(this, path);
        c.setActive(false);
        customers.add(c);
    }

    public void startQueue() {
        if (!customers.isEmpty()) spawn(customers.get(0));
    }

    private void spawn(QueuedCustomer c) {
        int sx = path[0][0] * gp.tileSize;
        int sy = path[0][1] * gp.tileSize;
        c.worldX = sx;
        c.worldY = sy;
        c.resetPathIndex();
        c.setActive(true);
    }

    // Called by a customer whenever it reaches a waypoint
    public void onReachStep(QueuedCustomer c, int stepIndexJustReached) {
        int i = customers.indexOf(c);
        if (stepIndexJustReached == spawnTriggerIndex) {
            if (i + 1 < customers.size() && !customers.get(i + 1).isActive()) {
                spawn(customers.get(i + 1));
            }
        }
    }

    // Called by a customer when it's finished (leaving/finished normal path)
    public void onFinished(QueuedCustomer c) {
        int i = customers.indexOf(c);
        if (i + 1 < customers.size() && !customers.get(i + 1).isActive()) {
            spawn(customers.get(i + 1));
        }
    }

    /**
     * Returns true if the requester is allowed to move into nextIndex tile.
     * This ensures nobody overtakes customers ahead in the list.
     *
     * IMPORTANT: ignore customers that are actively leaving (they no longer block).
     */
    public boolean canProceed(QueuedCustomer requester, int nextIndex) {
        int myIndex = customers.indexOf(requester);
        if (myIndex <= 0) return true; // first in line or not found -> can proceed

        // If any customer ahead hasn't passed nextIndex, requester must wait.
        for (int i = 0; i < myIndex; i++) {
            QueuedCustomer ahead = customers.get(i);

            // NEW: if the customer ahead is leaving, it should not block movement.
            if (ahead.isLeaving()) {
                continue;
            }

            if (ahead.isActive() && ahead.getPathIndex() <= nextIndex) {
                return false;
            }
        }
        return true;
    }
}
