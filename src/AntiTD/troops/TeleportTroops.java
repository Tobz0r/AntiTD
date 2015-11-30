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

    public TeleportTroops(Image img, Tile pos) {
        super(img, pos);
        tpMoves = 0;
        isTeleporting = false;
    }

    @Override
    public void tick() {
        if (isTeleporting) {
            if (tpMoves == 0) {
                teleportStartTile = this.getPosition();
            } else if (tpMoves < TP_LENGTH) {
                tpMoves++;
            } else {
                isTeleporting = false;
                tpMoves = 0;
                teleportEndTile = this.getPosition();
                teleportStartTile.setTeleportTo(teleportEndTile);
            }
        }
        this.move();
    }

    public void initTeleport() {
        isTeleporting = true;
    }
}
