package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;


import static primitives.Util.alignZero;
import static primitives.Util.isZero;;

/**
 * Cylinder class if a class representing a Cylinder in a 3D space
 * each Cylinder is represented by:  -axisRay: Ray that represent the center ray of the Cylinder
 *                                  -radius: a double value representing the radius
 *                                  -height: a double value representing the length of the Cylinder
 * @author Odelia Albo 214089047
 */
public class Cylinder extends Tube{

    //a double value representing the length of the Cylinder
    private double height;

    /***
     * This constructor receives  all cylinder parameters - center ray, radius and height
     * @param axisRay - Ray that represent the center ray of the Cylinder
     * @param radius - a double value representing the radius
     * @param height - a double value representing the length of the Cylinder
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        this.height = height;
    }

    /***
     *
     * @return height of cylinder
     */
    public double getHeight() {
        return height;
    }

    /**
     * get normal of tube
     * if on side of cylinder:
     * The algebraic operation - normal=normalize(P - O)
     * how to find O?
     * t = distance from p0 to O = axisRayDir * ( p - p0 )
     * O = p0 + t * axisRayDir
     * if on base of cylinder:
     * return axisRayDir normalized
     * @param P - a point on tube that we want normal from
     */
    @Override
    public Vector getNormal(Point3D P)
    {
        Point3D p0 = axisRay.getP0();
        Vector axisRayDir = axisRay.getDir();
        //distance from p0 to O
        double t;
        try {
            t=alignZero(axisRayDir.dotProduct(P.subtract(p0)));
        } catch (IllegalArgumentException e) {//if p=p0, meaning we want normal to base
            return axisRayDir.normalized();//normal will be axisRayDir
        }
        if(t==0||isZero(height-t))//either end of the cylinder, meaning we want normal to base
            return axisRayDir.normalized();//normal will be axisRayDir
        return super.getNormal(P); //else its on the sides of the cylinder
    }

    @Override
    public String toString() {
        return super.toString()+ "height=" + height;
    }

}