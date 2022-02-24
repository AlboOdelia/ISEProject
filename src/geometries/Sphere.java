package geometries;

import java.util.List;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.alignZero;

/**
 * Sphere class if a class representing a Sphere in a 3D space
 * each Sphere is represented by: -center: Point3D that represent the center of the Sphere
 *                               -radius: a double value representing the radius
 * @author Odelia Albo 214089047
 */
public class Sphere extends Geometry{
    //-center: Point3D that represent the center of the Sphere
    private Point3D center;
    //-radius: a double value representing the radius
    private double radius;

    /**
     * Contractor receiving all values
     * @param center- Point3D that represent the center of the Sphere
     * @param radius- a double value representing the radius
     */
    public Sphere(Point3D center, double radius) {
        super();
        this.center = center;
        this.radius = radius;
    }

    /**
     *The algebraic operation - (senter-wPoint3d).normalized()
     * @param wPoint3d the point that the normal goes out from
     * @return normalized normal to sphere in point3D
     */
    @Override
    public Vector getNormal(Point3D wPoint3d) {
        return (wPoint3d.subtract(center)).normalize();
    }

    /**
     * @return center field of sphere
     */
    public Point3D getCenter() {
        return center;
    }

    /**
     * @return radius field of sphere
     */
    public double getRadius() {
        return radius;
    }

    /***
     * output format
     */
    @Override
    public String toString() {
        return "Sphere [center=" + center + ", radius=" + radius + "]";
    }


    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        Point3D P0 = ray.getP0();
        Vector v = ray.getDir();

        //case: ray starts at the center-> scale of radius from p0 will give the intersection point
        if (P0.equals(center)) {

            return List.of(new GeoPoint(center.add(v.scale(radius)), this));
        }

        //creat direction vector from P0 to center
        Vector U = center.subtract(P0);

        //calculate dot product of the dir of ray direction vector from P0 to center
        // -> if they are the same Tm will result in 0
        double tm = alignZero(v.dotProduct(U));

        //length between O and ray
        //-> if ray goes through O d will be 0
        double d = alignZero(Math.sqrt(U.lengthSquared() - tm * tm));


        // no intersections : the ray direction is above the sphere, to far
        if (d >= radius) {
            return null;
        }

        //length between the closest point to O and sphere edge
        //-> if ray goes through O d will be radios
        double th = alignZero(Math.sqrt(radius * radius - d * d));

        //t1,2: will represent the length from P0 to the edge of the sphere
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

        //if both are positive-> there is 2 intersections and length is appropriate.
        if ((t1 > 0 && t2 > 0)&&((alignZero(t1 - maxDistance) <= 0))&&(alignZero(t2 - maxDistance) <= 0)) {
            Point3D P1 =ray.getPoint(t1);
            Point3D P2 =ray.getPoint(t2);
            return List.of(new GeoPoint(P1, this),new GeoPoint(P2, this));
        }

        //if only t1 is positive-> only they represent the length to the intersection and length is appropriate.
        if ((t1 > 0) && alignZero(t1 - maxDistance) <= 0) {
            Point3D P1 =ray.getPoint(t1);
            return List.of(new GeoPoint(P1,this));
        }

        //if only t1 is positive-> only they represent the length to the intersection and length is appropriate.
        if ((t2 > 0)&&alignZero(t1 - maxDistance) <= 0) {
            Point3D P2 =ray.getPoint(t2);
            return List.of(new GeoPoint(P2, this));
        }
        return null;
    }
}