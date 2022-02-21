package primitives;
import static primitives.Point3D.ZERO;
import static primitives.Util.isZero;

public class Vector {

    private Point3D head;


    /**
     * primary  constructor for Vector class
     *
     * @param head
     */
    public Vector(Point3D head) {
        super();
        if (head.equals(ZERO)) {
            throw new IllegalArgumentException("Vector head cannot be Point(0,0,0)");
        }
        this.head = head;
    }

    public Vector(double coord1, double coord2, double coord3) {

        this(new Point3D(coord1, coord2, coord3));
    }

    /**
     * constructor for Vector class
     *
     * @param x  X Coordinate of the head Point
     * @param y  Y Coordinate of the head Point
     * @param z  Z Coordinate of the head Point
     */
    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        this(new Point3D(x, y, z));
    }

    /**
     * get accessor for head Point
     *
     * @return a new Point3D corresponding to the head
     */
    public Point3D getHead() {
        return new Point3D(head.x.coord, head.y.coord, head.z.coord);
    }

    @Override
    public boolean equals(Object obj) {//overriding equals
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Vector)) return false;
        Vector other = (Vector)obj;
        return this.head.equals(other.head);
    }
    /**
     *
     * @param vec Vector
     * @return new Vector(u-v)
     */
    public Vector subtract(Vector v) {
        double x = head.x.coord - v.head.x.coord;
        double y = head.y.coord - v.head.y.coord;
        double z = head.z.coord - v.head.z.coord;

        return new Vector(new Point3D(x, y, z));
    }

    /**
     *
     * @param vec Vector
     * @return new Vector (u+v)
     */
    public Vector add(Vector v) {
        double x =head.x.coord + v.head.x.coord;
        double y = head.y.coord + v.head.y.coord;
        double z = head.z.coord + v.head.z.coord;

        return new Vector(new Point3D(x, y, z));
    }
    /**
     * creating a new Vector corresponding to the actual one
     * scaled by scaling factor
     *
     * @param scalar scaling
     */
    public Vector scale(double scalar) {
        if (isZero(scalar)) {
            throw new IllegalArgumentException("scaling factor == 0");
        }
        return new Vector(new Point3D(scalar * head.x.coord, scalar * head.y.coord, scalar * head.z.coord));
    }

    /**
     * @return length
     */
    public double length()
    {
        return Math.sqrt(lengthSquared());
    }

    public double lengthSquared()
    {
        return head.x.coord*head.x.coord
                +head.y.coord*head.y.coord
                +head.z.coord*head.z.coord;
    }
    /**
     * normalizing the current Vector
     *
     * @return this Vector normalized
     */
    public Vector normalize()
    {
        double length = this.length();

        //cannot divide by 0 there can not be a ZERO vector
        //if (length == 0)
        //	throw new ArithmeticException("divide by Zero");

        double x = this.head.x.coord;
        double y = this.head.y.coord;
        double z = this.head.z.coord;

        this.head = new Point3D((double)x / length,(double)y / length,(double)z / length);

        return this;
    }

    /**
     * creating a new Vector corresponding to the current values normalized
     *
     * @return new Vector normalized
     */
    public Vector normalized()
    {
        return new Vector(head).normalize();
    }


    /*
     * dot product between two vectors (scalar product)
     * @param v the right vector of U.V
     * @return scaler value of dot product
     */

    public double dotProduct(Vector v)
    {
        return head.x.coord*v.head.x.coord
                +head.y.coord*v.head.y.coord
                +head.z.coord*v.head.z.coord ;
    }

    /*
     *
     * @param vec
     * @return new Vector resulting from cross product
     */
    public Vector crossProduct (Vector vec)
    {
        return new Vector(new Point3D(head.y.coord*vec.head.z.coord-head.z.coord*vec.head.y.coord,
                head.z.coord*vec.head.x.coord-head.x.coord*vec.head.z.coord,
                head.x.coord*vec.head.y.coord-head.y.coord*vec.head.x.coord));
    }

    /**
     * @param axis axis of rotation
     * @param theta angle of rotation
     *
     * @author Yona Szmerla
     */
    public void rotateVector(Vector axis, double theta) {
        double x = this.head.x.coord;
        double y = this.head.y.coord;
        double z = this.head.z.coord;

        double u = axis.head.x.coord;
        double v = axis.head.y.coord;
        double w = axis.head.z.coord;

        double v1 = u * x + v * y + w * z;

        double thetaRad = Math.toRadians(theta);

        double xPrime = u * v1 * (1d - Math.cos(thetaRad))
                + x * Math.cos(thetaRad)
                + (-w * y + v * z) * Math.sin(thetaRad);

        double yPrime = v * v1 * (1d - Math.cos(thetaRad))
                + y * Math.cos(thetaRad)
                + (w * x - u * z) * Math.sin(thetaRad);

        double zPrime = w * v1 * (1d - Math.cos(thetaRad))
                + z * Math.cos(thetaRad)
                + (-v * x + u * y) * Math.sin(thetaRad);

        this.head = new Point3D(xPrime, yPrime, zPrime);
    }


    @Override
    public String toString() {//overriding the printing func
        return "Vector [head=" + head + "]";
    }
}