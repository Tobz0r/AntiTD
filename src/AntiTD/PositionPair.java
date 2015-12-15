package AntiTD;

/**
 * Created by id12men on 2015-12-11.
 */
public class PositionPair {

    private final Long x;
    private final Long y;

    public PositionPair(Long x, Long y) {
        this.x = x;
        this.y = y;
    }

    public Long getX() { return x; }
    public Long getY() { return y; }

    @Override
    public int hashCode() { return x.hashCode() ^ y.hashCode(); }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PositionPair)) return false;
        PositionPair pairo = (PositionPair) o;
        return this.x.equals(pairo.getX()) &&
                this.y.equals(pairo.getY());
    }

}
