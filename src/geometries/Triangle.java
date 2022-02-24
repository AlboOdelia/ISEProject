package geometries;

import java.util.List;

import geometries.Intersectable.GeoPoint;
import primitives.Point3D;
import primitives.Ray;

/**
 * Sphere Triangle if a class representing a Triangle in a 3D space
 * each Triangle is represented by: 3 pointed poligon
 * @author Odelia Albo 214089047
 * needed: implement findGeoIntersectionss
 * */
public class Triangle extends Polygon {
    /***
     * Constructor:
     * the Triangle is represented trought a 3 sided polygon
     * so we creat one in the constructor
     * @param p1- the first vertex
     * @param p2- the second vertex
     * @param p3- the third vertex
     */
    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        super(p1, p2, p3);
    }

//    @Override
//    public String toString() {
//        return "Triangle [p0="  + ", p1="  + ", p1=" +"]";
//    }

    public List<GeoPoint> findGeoIntersections(Ray ray) {
        var geoList = super.findGeoIntersections(ray);
        return geoList == null ? null
                :  List.of(new GeoPoint(geoList.get(0).point, this));
    }
}
