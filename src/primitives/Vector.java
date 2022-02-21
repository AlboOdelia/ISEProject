package primitives;
import static primitives.Point3D.ZERO;
import static primitives.Util.isZero;


/**
 * Vector in 3D for RayTracing:
 * Class Vector is the basic class representing a Vector by three variables - x,y,z,
 * the vector starts at the beginning of the axes, and a point representing the head of vector
 * @author odelia albo 214089047
 * updated
 */
public class Vector {

    private Point3D head;


    /**
     * The constructor receives a 3D point and creates the vector with the same x,y,z
     * The constructor sends an exception if the give point is ZERO
     * @param head - a 3D point of the format (x, y, z)
     */
    public Vector(Point3D head) {
        super();
        if (head.equals(ZERO)) {
            throw new IllegalArgumentException("Vector head cannot be Point(0,0,0)");
        }
        this.head = new Point3D(head.getX(), head.getY(), head.getZ());
    }

    /**
     * The constructor receives 3 real numbers (type Double)
     * and creates a new vector of the format (x, y, z) represented by a 3D point
     * The constructor sends an exception if the given point is (0, 0, 0)
     * @param x - distance of the point from axis x in the Cartesian 3-Dimensional coordinate system.
     * @param y - distance of the point from axis y in the Cartesian 3-Dimensional coordinate system.
     * @param z - distance of the point from axis z in the Cartesian 3-Dimensional coordinate system.
     */
    public Vector(double x, double y, double z) {
        this.head = new Point3D(x, y, z);
        if(head.equals(Point3D.ZERO))
            throw new IllegalArgumentException();
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
     * @return a new Point3D corresponding to the head
     */
    public Point3D getHead() {
        return new Point3D(head.getX(), head.getY(), head.getZ());
    }

    /**
     * Addition between two vectors by algebraic operation
     * The algebraic operation - v + u = (v1+u1, v2+u2, v3+u3)
     * @param other - the second vector
     * @return the new vector resulted in the addition
     */
    public Vector add(Vector other) {
        return new Vector(head.add(other));
    }


    /**
     * Subtraction between two vectors by algebraic operation
     * The algebraic operation - v - u = (v1-u1, v2-u2, v3-u3)
     * @param other - the second vector
     * @return the new vector resulted in the subtraction
     */
    public Vector subtract(Vector other) {
        return head.subtract(other.getHead());
    }

    /**
     * creating a new Vector corresponding to the actual one
     * scaled by scaling factor
     * Multiplication of a vector in scalar by algebraic operation
     * The algebraic operation - a*v = (a*v1, a*v2, a*v3)
     * @param scalar - a scalar number (type double)
     * @return the new vector resulted in the scalar multiplication
     */
    public Vector scale(double scalar) {
        if (isZero(scalar)) {
            throw new IllegalArgumentException("scaling factor == 0");
        }
        return new Vector(scalar * head.getX(), scalar * head.getY(), scalar * head.getZ());
    }


    /**
     * dot product between two vectors (scalar product)
     * The algebraic operation - v*u = v1*u1 + v2*u2 + v3-u3
     * @param v the right vector of U*V
     * @return scaler value of dot product
     */
    public double dotProduct(Vector v)
    {
        Point3D p = v.getHead();
        return p.getX()*head.getX() + p.getY()*head.getY() + p.getZ()*head.getZ();
    }

    /**
     *cross product between two vectors
     *The algebraic operation - VxU = (v2u3-v3u2,v1u3-v3u1,v1u2-v2u1)
     * @param vec the second vector
     * @return new Vector resulting from cross product
     */
    public Vector crossProduct (Vector vec)
    {
        Point3D p = vec.getHead();
        return new Vector(head.getY()*p.getZ() - head.getZ()*p.getY(),
                head.getZ()*p.getX() - head.getX()*p.getZ(),
                head.getX()*p.getY() - head.getY()*p.getX());
    }

    /**
     * The length of the vector squared
     * @return the squared length (type Double)
     */
    public double lengthSquared() {
        return head.getX()*head.getX() + head.getY()*head.getY() + head.getZ()*head.getZ();
    }

    /**
     * the real length of vector
     * @return length- double type
     */
    public double length()
    {
        return Math.sqrt(lengthSquared());
    }

    /**
     * normalizing the current Vector
     *doing this by dividing the vector by its length
     * @return this Vector normalized
     */
    public Vector normalize() {
        head = scale(Math.abs(1/this.length()))                                                                                                                                                                     .getHead();
        return this;
    }

    /**
     * creating a new Vector corresponding to the current values normalized
     * @return new Vector normalized
     */
    public Vector normalized()
    {
        return new Vector(head).normalize();
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

    @Override
    public String toString() {//overriding the printing func
        return "Vector [head=" + head + "]";
    }

//    /*
//    /**
//     * @param axis axis of rotation
//     * @param theta angle of rotation
//     *
//     * @author Yona Szmerla
//     */
//    public void rotateVector(Vector axis, double theta) {
//        double x = this.head.x.coord;
//        double y = this.head.y.coord;
//        double z = this.head.z.coord;
//
//        double u = axis.head.x.coord;
//        double v = axis.head.y.coord;
//        double w = axis.head.z.coord;
//
//        double v1 = u * x + v * y + w * z;
//
//        double thetaRad = Math.toRadians(theta);
//
//        double xPrime = u * v1 * (1d - Math.cos(thetaRad))
//                + x * Math.cos(thetaRad)
//                + (-w * y + v * z) * Math.sin(thetaRad);
//
//        double yPrime = v * v1 * (1d - Math.cos(thetaRad))
//                + y * Math.cos(thetaRad)
//                + (w * x - u * z) * Math.sin(thetaRad);
//
//        double zPrime = w * v1 * (1d - Math.cos(thetaRad))
//                + z * Math.cos(thetaRad)
//                + (-v * x + u * y) * Math.sin(thetaRad);
//
//        this.head = new Point3D(xPrime, yPrime, zPrime);
//    }
}