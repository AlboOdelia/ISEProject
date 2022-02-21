package geometries;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public interface Intersectable {
    List<GeoPoint> findGeoIntsersections(Ray ray);
    default List<Point3D> findIntsersections(Ray ray) {
        var geoList = findGeoIntsersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).collect(Collectors.toList());
    }


    /**
     * internal class that connects point to geometry
     * @author Odelia
     *
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point3D point;

        /*
         * Constructor
         * @param q0
         * @param geo
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
            if (!(obj instanceof GeoPoint)) {
                return false;
            }
            GeoPoint other = (GeoPoint) obj;
            return Objects.equals(geometry, other.geometry) && Objects.equals(point, other.point);
        }
    }

}
