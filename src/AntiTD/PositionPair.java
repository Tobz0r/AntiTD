package AntiTD;

/**
 * Created by id12men on 2015-12-11.
 */
public class PositionPair {

    private final Long left;
    private final Long right;

    public PositionPair(Long left, Long right) {
        this.left = left;
        this.right = right;
    }

    public Long getX() { return left; }
    public Long getY() { return right; }

    @Override
    public int hashCode() { return left.hashCode() ^ right.hashCode(); }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PositionPair)) return false;
        PositionPair pairo = (PositionPair) o;
        return this.left.equals(pairo.getX()) &&
                this.right.equals(pairo.getY());
    }

}
