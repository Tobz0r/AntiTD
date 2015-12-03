package AntiTD.troops;

import AntiTD.tiles.Tile;

import java.awt.*;

/**
 * Created by dv13trm on 2015-11-27.
 */
public class TeleportTroops extends Troop {

    private Tile teleportStartTile;
    private Tile teleportEndTile;

    private final int TP_LENGTH = 3;
    private int tpMoves;
    private boolean isTeleporting;

    private final int MAX_HEALTH = 10;
    private final int KILL_DEATH_SCORE = 100;

    public TeleportTroops(Tile pos) {
        super(null, pos);
        tpMoves = 0;
        isTeleporting = false;
        super.health = MAX_HEALTH;
        super.score = KILL_DEATH_SCORE;
    }

    @Override
    public void tick() {
        if (isTeleporting) {
            if (tpMoves == 0) {
                teleportStartTile = this.getTilePosition();
            } else if (tpMoves < TP_LENGTH) {
                tpMoves++;
            } else {
                isTeleporting = false;
                tpMoves = 0;
                teleportEndTile = this.getTilePosition();
                teleportStartTile.setTeleportTo(teleportEndTile);
            }
        }
        this.move();
    }

    @Override
    public void render(Graphics g) {

    }

    public void initTeleport() {
        isTeleporting = true;
    }
}
