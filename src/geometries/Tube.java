package geometries;


import java.util.List;

import primitives.*;

import static primitives.Util.isZero;

/**
 * Tube class if a class representing a Tube in a 3D space
 * each Tube is represented by:  -axisRay: Ray that represent the center ray of the Tube
 *                               -radius: a double value representing the radius
 * @author Odelia Albo 214089047
 * needed: implement findGeoIntersectionss
 */
public class Tube extends Geometry{
    //Ray that represent the center ray of the Tube
    final Ray axisRay;
    //a double value representing the radius
    private double radius;

    /***
     * The constructor receives the axis ray and the radius to create the tube
     * @param axisRay - center ray of the Tube
     * @param radius - a double value representing the radius
     */
    public Tube(Ray axisRay, double radius) {
        super();
        this.axisRay = axisRay;
        this.radius = radius;
    }

    /**
     * get normal of tube
     * The algebraic operation - normal=normalize(P - O)
     * how to find O?
     * t = distance from p0 to O = axisRayDir * ( p - p0 )
     * O = p0 + t * axisRayDir
     * @param P - a point on tube that we want normal from
     */
    @Override
    public Vector getNormal(Point3D P) {
        Point3D p0 = axisRay.getP0();
        Vector axisRayDir = axisRay.getDir();
        //distance from p0 to O
        double t = axisRayDir.dotProduct(P.subtract(p0));
        Point3D O = p0;
        if(!isZero(t)) //meaning there is some distance
            O = p0.add(axisRayDir.scale(t));
        return (P.subtract(O)).normalized();
    }

    /**
     * @return AxisRay of tube
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    /**
     * @return radius of tube
     */
    public double getRadius() {
        return radius;
    }

    /***
     * output format
     */
    @Override
    public String toString() {
        return "Tube [axisRay=" + axisRay + ", radius=" + radius + "]";
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return null;
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return null;
    }
}
