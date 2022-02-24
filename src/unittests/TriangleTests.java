package unittests;

import static org.junit.Assert.*;

import java.util.List;

import geometries.Intersectable;
import org.junit.Test;

import geometries.Triangle;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import geometries.Intersectable.GeoPoint;

public class TriangleTests {

    /**
     * Test method for
     * {@link geometries.Triangle#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Triangle tr = new Triangle(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals("Bad normal to trinagle", new Vector(sqrt3, sqrt3, sqrt3), tr.getNormal(new Point3D(0, 0, 1)));
    }

//    /**
//     * Test method for {@link geometries.Triangle#findGeoIntersections(primitives.Ray)}.
//     */
//    @Test
//    public void testfindGeoIntersections() {
//        // ============ Equivalence Partitions Tests ==============
//
//        // TC01: Intersection inside triangle
//        Point3D p1=new Point3D(0, 1, 1);
//        Triangle tr = new Triangle(new Point3D(0, 3,0), new Point3D(0,-2,0), new Point3D(0, 0, 2));
//        List<GeoPoint> result=tr.findGeoIntersections(new Ray(new Point3D(2, 1, 1), new Vector(-1, 0, 0)));
//        assertEquals("Wrong number of points", 1, result.size());
//        assertEquals("Intersection inside triangle", List.of(p1), result);
//
//        // TC02: Ray outside triangle against edge
//        assertNull("Ray outside triangle against edge",
//                tr.findGeoIntersections(new Ray(new Point3D(5,1,2), new Vector(-1,0,0))));
//
//        // TC03: Ray outside triangle against vertex
//        assertNull("Ray outside triangle against vertex",
//                tr.findGeoIntersections(new Ray(new Point3D(5,0,3), new Vector(-1,0,0))));
//
//        // =============== Boundary Values Tests ==================
//        // TC11: Intersection on edge of triangle
//        assertNull("Intersection on edge of triangle",
//                tr.findGeoIntersections(new Ray(new Point3D(5,1,0), new Vector(-1,0,0))));
//        // TC12: Intersection in vertex of triangle
//        assertNull("Intersection in vertex of triangle",
//                tr.findGeoIntersections(new Ray(new Point3D(5,0,2), new Vector(-1,0,0))));
//        // TC13: Intersection on triangle edge's continuation
//        assertNull("Intersection on triangle edge's continuation",
//                tr.findGeoIntersections(new Ray(new Point3D(5,4,0), new Vector(-1,0,0))));
//    }



}
