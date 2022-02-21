package geometries;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;


/**
 * Interface Intersectable is to represent intersectable shapes
 * @author Albo Odelia 214089047
 * updated
 */
public interface Intersectable {
//    List<GeoPoint> findGeoIntsersections(Ray ray);
//    default List<Point3D> findIntsersections(Ray ray) {
//        var geoList = findGeoIntsersections(ray);
//        return geoList == null ? null
//                : geoList.stream().map(gp -> gp.point).collect(Collectors.toList());
//    }
    /**
     * internal class that connects point to geometry
     *  represent an intersection point including the geometry it is intersected with.
     * The purpose is to access material characteristics of geometry.
     * @author Albo Odelia 214089047
     */
    public static class GeoPoint {
        //the geometry that the point is on.
        public Geometry geometry;
        // the point itself
        public Point3D point;

        /**
         * Constructor for GeoPoint
         * @param q0- the geometry that the point is on.
         * @param geo - the represented point
         */
        public GeoPoint(Point3D q0, Geometry geo) {
            this.point = q0;
            this.geometry = geo;
        }

        /**
         *
         * @param obj Object (basicaly another GeoPoint) to compare
         * @return true or false accordingly
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) return false;
            if (!(obj instanceof GeoPoint)) {
                return false;
            }
            GeoPoint other = (GeoPoint) obj;
            return Objects.equals(geometry, other.geometry) && Objects.equals(point, other.point);
        }
    }
    /**
     * Default implementations of Geometry findGeoIntersections that includes maxDistance param.
     * @param ray - ray of intersection
     * @return result of findGeoIntersections with infinite maxDistance
     */
    default List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * The method receives a ray and returns intersection points with the geometry that are in the range determinated by the maxDistance
     * @param ray - the ray of intersection
     * @param maxDistance - the maximum distance in which we search intersection points
     * @return list of intersection points that are close enough
     */
    List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance);

}