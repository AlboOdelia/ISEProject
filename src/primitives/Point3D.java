package primitives;

import java.util.Objects;
/**
 * basic Point for RayTracing project in 3D
 *
 * @author odelia
 * updated
 */

public class Point3D {
    // 3 Coordinate values representing 1 point
    final Coordinate x;
    final Coordinate y;
    final Coordinate z;

    //static Point3D for origin point (0,0,0)
    public final static Point3D ZERO=new Point3D(0,0,0);

    /**
     * constructor for Point3D
     *
     * @param x coordinate for X axis
     * @param y coordinate for Y axis
     * @param z coordinate for Z axis
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        super();
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * primary constructor for Point3D
     * @param x coordinate value for X axis
     * @param y coordinate value for Y axis
     * @param z coordinate value for Z axis
     */
    public Point3D(double x, double y, double z) {
        this.x = new Coordinate(x);
        this.y = new Coordinate(y);
        this.z = new Coordinate(z);
    }

    /**
     * Returns the field x of the point
     * @return x - the first coordinate of the point
     */
    public double getX() {
        return x.coord;
    }
    /**
     * Returns the field y of the point
     * @return y - the second coordinate of the point
     */
    public double getY() {
        return y.coord;
    }
    /**
     * Returns the field z of the point
     * @return z - the third coordinate of the point
     */
    public double getZ() {
        return z.coord;
    }

    /**
     * Addition of a vector to the point
     * The algebraic operation - p + v = (p1+v1, p2+v2, p3+v3)
     * @param v - the vector
     * @return the new point after the vector addition
     */
    public Point3D add(Vector v)
    {
        return new Point3D(this.getX() + v.getHead().getX(), this.getY() + v.getHead().getY(), this.getZ() + v.getHead().getZ());
    }

    /**
     * Subtraction between two points by algebraic operation
     * The algebraic operation - q - p = (q1-p1, q2-p2, q3-p3)
     * @param p - the second point
     * @return a vector direction from p to this point
     */
    public Vector subtract(Point3D p) {
        return new Vector(this.getX() - p.getX(), this.getY() - p.getY(), this.getZ() - p.getZ());
    }

    /**
     * Find the square of the distance by algebraic operation
     * The algebraic operation - ((q1-p1)^2 + (q2-p2)^2 + (q3-p3)^2)
     * @param p - the second point
     * @return the Square of the distance between the points
     */
    public double distanceSquared(Point3D p)
    {
        return ((this.getX() - p.getX())*(this.getX() - p.getX())
                + (this.getY() - p.getY())*(this.getY() - p.getY())
                + (this.getZ() - p.getZ())*(this.getZ() - p.getZ()));
    }

    /**
     * Find the distance by using the distanceSquared function
     * returns the positive square root of the result of the distanceSquared function
     * @param p - the second point
     * @return the distance between the points
     */
    public double distance(Point3D p)
    {
        return Math.sqrt(this.distanceSquared(p));
    }

    /**
     *checks if an object is equal to this Point3D
     * @param obj Object  to compare
     * @return true or false accordingly
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) return false;
        if (!(obj instanceof Point3D)) {
            return false;
        }
        Point3D other = (Point3D) obj;
        return Objects.equals(x, other.x) && Objects.equals(y, other.y) && Objects.equals(z, other.z);
    }

    @Override
    public String toString() {
        return "Point3D [x=" + x + ", y=" + y + ", z=" + z + "]";
    }

}