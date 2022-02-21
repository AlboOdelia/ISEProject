package primitives;

import java.util.Objects;
/**
 * basic Point for RayTracing project in 3D
 *
 */

public class Point3D {
    //static Point3D for origin point (0,0,0)
    public static final Point3D ZERO=new Point3D(0,0,0);
    public Coordinate x;
    public Coordinate y;
    public Coordinate z;
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
     *
     * @param pt2 Point3d from whom we create the Vector
     *            pointing toward the actual Point3d instance
     * @return
     */
    public Vector subtract(Point3D ePoint3d)
    {
        return new Vector(x.coord-ePoint3d.x.coord, y.coord-ePoint3d.y.coord,z.coord-ePoint3d.z.coord);
    }
    public Point3D add(Vector eVector)
    {
        Point3D headPoint3d= eVector.getHead();
        return new Point3D(headPoint3d.x.coord+x.coord, headPoint3d.y.coord+y.coord, headPoint3d.z.coord+z.coord);
    }
    /**
     * @param other
     * @return (x2 - x1)^2 + (y2-y1)^2 + (z2-z1)^2
     */
    public double distanceSquared (Point3D ePoint3d)
    {
        return (ePoint3d.x.coord-x.coord)*(ePoint3d.x.coord-x.coord)+
                (ePoint3d.y.coord-y.coord)*(ePoint3d.y.coord-y.coord)+
                (ePoint3d.z.coord-z.coord)*(ePoint3d.z.coord-z.coord);
    }
    /**
     * @param point3D
     * @return  distance between 2  3D points
     */
    public double distance (Point3D ePoint3d)
    {
        return Math.sqrt(distanceSquared(ePoint3d));
    }
    /**
     *
     * @param o Object (basicaly another Point3d) to compare
     * @return true or false accordingly
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
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