package geometries;

import java.util.List;

//import java.text.Normalizer;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

/**
 * Plane class if a class representing a plane in a 3D space
 * each Place is represented by: -point: Point3D on the plane
 *                               -normal: Vector to the plane
 * @author Odelia Albo 214089047
 */
public class Plane extends Geometry{
    //point on the plane
    private Point3D q0;
    //normal to the plane
    private Vector normal;

    /**
     * Constructor- receives all values
     * @param q0- point on the plane
     * @param normal- normal to the plane
     */
    public Plane(Point3D q0, Vector normal) {
        super();
        this.q0 = q0;
        this.normal = normal.normalized();
    }

    /**
     * Constructor
     * @param q0- a point on the plane
     * @param p1- a point on the plane
     * @param p2- a point on the plane
     */
    public Plane(Point3D q0, Point3D p1, Point3D p2) {
        super();
        this.q0 = q0; //the first point is taken as the point on plane
        //if two of the points are equal subtract
        // will result in IllegalArgumentException() as wanted
        Vector v1 = q0.subtract(p1);
        Vector v2 = q0.subtract(p2);
        //if point are on same line crossProduct will result in ZERO head
        // values an IllegalArgumentException() will be raised as wanted
        Vector crossProductVector = v1.crossProduct(v2);
        this.normal = crossProductVector.normalized();
    }

    /**
     * this is a vector so wPoint3d isn't needed so we will call getNormal()
     * @param wPoint3d should be null for flat geometries
     * @return
     */
    @Override
    public Vector getNormal(Point3D wPoint3d) {
        return getNormal();
    }

    /**
     * @return normal of plane
     */
    public Vector getNormal() {
        return normal.normalize();
    }

    /**
     *return point on plane
     * @return Q0 field of plane
     */
    public Point3D getQ0() {
        return q0;
    }

    /**
     * output format
     */
    @Override
    public String toString() {
        return "Plane{" +
                " q0=" + q0 +
                ", normal=" + normal +
                '}';
    }


    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        //settings
        Point3D rayP0 = ray.getP0();
        Vector rayDir = ray.getDir();
        Vector nVector = getNormal();

        //if ray  begins in the same point which appears as
        // reference point in the plane-> there is no intersection point
        if(q0.equals(rayP0))
            return null;

        //creat dir vector from p0 to q0
        Vector p0q0=q0.subtract(rayP0);

        //the numerator.
        //if dir vector from p0 to q0 and normal to plane are orthogonal
        //meaning the P0 is on the plane
        //-> the result will be zero
        double normalp0q0= alignZero(nVector.dotProduct(p0q0));

        //if P0 is on the plane we don't count that as an intersection
        if(isZero(normalp0q0))
            return null;

        //the denominator
        //if dir vector of ray and normal to plane are orthogonal
        //meaning dir vector is parallel to plane
        //-> the result will be zero
        double k=alignZero(nVector.dotProduct(rayDir));

        //if dir vector is parallel to plane we don't count that as
        if(isZero(k))
            return null;

        //representing the distance from start: the scalar to multiply ray to get intersection
        double t= alignZero(normalp0q0/k);

        //if scalar is negative-> the ray is in the opposite direction
        //meaning there is no intersection
        if (t<=0)
            return null;
        //if distance is further then the max distance assigned
        if (alignZero(t - maxDistance) > 0)
            return null;
        Point3D p=ray.getPoint(t);
        return List.of(new GeoPoint(p, this));
    }
}