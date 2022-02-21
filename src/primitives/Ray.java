package primitives;

import java.util.List;
import java.util.Objects;

import geometries.Intersectable.GeoPoint;

import static primitives.Util.isZero;

public class Ray {
    private static final double DELTA = 0.1;
    private Point3D p0;
    private Vector dir;
    public Ray(Point3D p0, Vector dir) {
        super();
        this.p0 = p0;
        this.dir = dir.normalized();
    }
    public Ray(Point3D point, Vector lightDirection, Vector n) {
        double delta=n.dotProduct(lightDirection)>0?DELTA:-DELTA;
        p0=point.add(n.scale(delta));
        dir=lightDirection;
    }
    public Vector getDir() {
        return dir;
    }
    public Point3D getP0() {
        return p0;
    }
    public Point3D getPoint(double delta ){
        if (isZero(delta)){
            return  p0;
        }
        return p0.add(dir.scale(delta));
    }

    /**
     * find the closest Point to Ray origin
     * @param pointsList intersections point List
     * @return closest point
     */
    public Point3D findClosestPoint(List<Point3D> pointsList){
        Point3D result = null;
        double closeDistance = Double.MAX_VALUE;
        for (Point3D p: pointsList)
        {
            double temp = p.distance(p0);
            if(temp < closeDistance)
            {
                closeDistance =temp;
                result =p;
            }
        }
        return  result;
    }
    /**
     * find the closest Point to Ray origin
     * @param pointsList intersections point List
     * @return closest point
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> pointsList){
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
        if (!(obj instanceof Ray)) {
            return false;
        }
        Ray other = (Ray) obj;
        return Objects.equals(dir, other.dir) && Objects.equals(p0, other.p0);
    }
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return p0+ ", "+ dir;
    }
}