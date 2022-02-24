package primitives;

import java.util.List;
import java.util.Objects;

import geometries.Intersectable.GeoPoint;

import static primitives.Util.isZero;

/**
 * Class Ray is the basic class representing a ray -
 * containing a vector representing the direction and the starting point of ray
 * @author Albo Odelia 214089047
 * updated
 */
public class Ray {
    //needed for the constructor that receive a normal(vector)
    private static final double DELTA = 0.1;
    //the ray head
    final private Point3D p0;
    //the direction of the ray
    final private Vector dir;

    /**
     * The constructor receives a 3D point and a vector
     * @param p0 - Point3D that determines the starting point of the ray
     * @param dir - Vector that determines the direction of the ray
     */
    public Ray(Point3D p0, Vector dir) {
        super();
        this.p0 = p0;
        this.dir = dir.normalized();
    }

    /***
     * The constructor receives a 3D point, a vector and a normal.
     * It creates a ray using the direction vector and the point, but moves the point by DELTA in the normal direction.
     * The reason: to avoid self-shadowing and self-reflection
     * @param point - the basic head point
     * @param lightDirection - ray direction
     * @param normal - the normal to the point on the geometry
     */
    public Ray(Point3D point, Vector lightDirection, Vector normal) {
        lightDirection.normalize();
        double delta=normal.dotProduct(lightDirection)>0?DELTA:-DELTA;
        p0=point.add(normal.scale(delta));
        dir=lightDirection;
    }

    /**
     * return the direction of ray
     * @return vector representing dir
     */
    public Vector getDir() {
        return dir;
    }

    /**
     * return the starting point of ray
     * @return a Point3D that is p0
     */
    public Point3D getP0() {
        return p0;
    }

    /**
     * returns a specific point on ray using delta
     * @param delta - what is the distance
     * @return the point on ray that is delta par from p0
     */
    public Point3D getPoint(double delta ){
        if (isZero(delta)){
            return  p0;
        }
        return p0.add(dir.scale(delta));
    }

//    /**
//     * find the closest Point to Ray origin
//     * @param pointsList intersections point List
//     * @return closest point
//     */
//    public Point3D findClosestPoint(List<Point3D> pointsList){
//        Point3D result = null;
//        double closeDistance = Double.MAX_VALUE;
//        for (Point3D p: pointsList)
//        {
//            double temp = p.distance(p0);
//            if(temp < closeDistance)
//            {
//                closeDistance =temp;
//                result =p;
//            }
//        }
//        return  result;
//    }

    /**
     * find the closest Point to Ray origin
     * @param pointsList intersections point List
     * @return closest point
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> pointsList){
        if (pointsList == null)
                return null;
        GeoPoint result = null;
        double closeDistance = Double.MAX_VALUE;
        for (GeoPoint p: pointsList)
        {
            double temp = p.point.distance(p0);
            if(temp < closeDistance)
            {
                closeDistance =temp;
                result =p;
            }
        }
        return  result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) return false;
        if (!(obj instanceof Ray)) {
            return false;
        }
        Ray other = (Ray) obj;
        return Objects.equals(dir, other.dir) && Objects.equals(p0, other.p0);
    }
    @Override
    public String toString() {
        return "p0 " + p0+ ", dir: "+ dir;
    }
}