package unittests;

import static org.junit.Assert.*;

import java.util.List;

import primitives.*;

import org.junit.Test;

import geometries.Geometries;
import geometries.Intersectable.GeoPoint;
import geometries.Plane;
import geometries.Sphere;

public class RayTests {



//    @Test
//    public void testFindClosestGeoPoint() {
//        Sphere sphere1 = new Sphere(new Point3D(8, 0, 0), 1d);
//        Plane plane = new Plane(new Point3D(1, 1, 3), new Vector(0,0,1));
//        Sphere sphere2 = new Sphere(new Point3D(0, 0, 11), 1);
//
//        Ray ray = new Ray(new Point3D(0, 0, -2), new Vector(0, 0, 1));
//
//        // ============ Equivalence Partitions Tests ==============
//        // TC01: The closest point is in the middle of the list
//        Geometries geometries = new Geometries(sphere1, plane, sphere2);
//
//        List<GeoPoint> result = geometries.findGeoIntersections(ray);
//        GeoPoint closestGeoPoint3d = ray.findClosestGeoPoint(result);
//
//        assertEquals("wrong closest intersection point when point is in the middle", new Point3D(0,0,3), closestGeoPoint3d.point);
//
//
//        // ============ Boundary Partitions Tests ==============
//        // TC02: The closest point is in the beginning of the list
//        geometries = new Geometries(plane, sphere2, sphere1);
//        result = geometries.findGeoIntersections(ray);
//        closestGeoPoint3d = ray.findClosestGeoPoint(result);
//        assertEquals("wrong closest intersection point when point is at the beginning", new Point3D(0,0,3), closestGeoPoint3d.point);
//
//        //TC03: The closest point is in the end of the list
//        geometries = new Geometries(sphere2, sphere1, plane);
//        result = geometries.findGeoIntersections(ray);
//        closestGeoPoint3d = ray.findClosestGeoPoint(result);
//        assertEquals("wrong closest intersection point when point is at the the", new Point3D(0,0,3), closestGeoPoint3d.point);
//
//        //TC04: The list is null, there are no intersection points
//        Ray ray2 = new Ray(new Point3D(0, 0, -2), new Vector(0, 0, -1));
//        result = geometries.findGeoIntersections(ray2);
//        closestGeoPoint3d = ray.findClosestGeoPoint(result);
//        assertNull("There are no intersection points", closestGeoPoint3d);
//
//    }

}