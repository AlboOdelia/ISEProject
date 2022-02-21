package primitives;
import static primitives.Util.isZero;

public class Coordinate {

    public double coord;

    public Coordinate(double coord) { this.coord = Util.alignZero(coord); }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Coordinate)) return false;
        Coordinate other = (Coordinate)obj;
        return isZero(coord - other.coord);
    }

    @Override
    public String toString() { return "" + coord; }
}