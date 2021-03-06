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

    /**
     * Test method for {@link geometries.Triangle#findGeoIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Triangle triangle = new Triangle(new Point3D(1, 1, 0) , new Point3D(5, 1, 0), new Point3D(1, 4, 0));

        // ============ Equivalence Partitions Tests ==============

        // TC01: The ray has a simple intersection with the triangle
        assertEquals("Bad intersaction point", List.of(new Point3D(2,2,0)), List.of(triangle.findGeoIntersections(new Ray(new Point3D(2, 2, 1), new Vector(0, 0, -1))).get(0).point));

        // TC02: Ray is outside the triangle against edge
        assertNull("Ray is outside the triangle against edge",
                triangle.findGeoIntersections(new Ray(new Point3D(2, 5, 1), new Vector(0, 0, -1))));

        // TC03: Ray is outside the triangle against vertex
        assertNull("Ray is outside the triangle against vertex",
                triangle.findGeoIntersections(new Ray(new Point3D(6, 0.5, 1), new Vector(0, 0, -1))));

        // =============== Boundary Values Tests ==================

        // TC04: Ray is on the edge of the triangle
        assertNull("Ray is outside the triangle against vertex",
                triangle.findGeoIntersections(new Ray(new Point3D(2, 1, 1), new Vector(0, 0, -1))));

        // TC05: Ray is on the vertex of the triangle
        assertNull("Ray is outside the triangle against vertex",
                triangle.findGeoIntersections(new Ray(new Point3D(5, 1, 1), new Vector(0, 0, -1))));

        // TC06: Ray is on the edge's continuation of the triangle
        assertNull("Ray is outside the triangle against vertex",
                triangle.findGeoIntersections(new Ray(new Point3D(6, 1, 1), new Vector(0, 0, -1))));

    }



}
