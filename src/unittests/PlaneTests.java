package unittests;

import geometries.*;
import geometries.Intersectable.GeoPoint;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
/**
 * @author Keren
 *
 */
public class PlaneTests {

    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormalPoint3D() {
        Plane plane=new Plane(new Point3D(0, 0, 4.5), new Point3D(9, 0, 0), new Point3D(0, 4.5, 0));
        assertEquals(new Vector(0.333333333333, 0.6666666666666, 0.6666666666666), plane.getNormal());
    }

    /**
     * Test method for {@link geometries.Plane#Plane(primitives.Point3D, primitives.Point3D, primitives.Point3D)}.
     */
    @Test
    public void testPlanePoint3DPoint3DPoint3D() {
        // TC11: matching pointes
        try {
            new Plane(new Point3D(0, 0, 1), new Point3D(0, 0, 1),
                    new Point3D(0, 1, 0));
            fail("Constructed a plane with 2 same pointes");
        } catch (IllegalArgumentException e) {}

        // TC12: 3 pointes on the same line
        try {
            new Plane(new Point3D(0, 0, 1), new Point3D(0, 0, 3),
                    new Point3D(0, 0, 2));
            fail("Constructed a plane with 3 points on the same line");
        } catch (IllegalArgumentException e) {}
    }
    @Test
    public void testFindIntersections() {
        Plane plane = new Plane(new Point3D(0, 0, 1) , new Vector(1,1,1));

        // ============ Equivalence Partitions Tests ==============

        // TC01: The ray has a simple intersection with the plane
        List<GeoPoint> result = plane.findGeoIntersections(new Ray(new Point3D(-1, 0, 1), new Vector(1, 0, 0)));
        List<Point3D> pointsList = List.of(result.get(0).point);

        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Bad intersaction point", List.of(new Point3D(0, 0, 1)), pointsList);

        // TC02: Ray's line doesn't cross the plane
        assertNull("Ray's line doesn't cross the plane",
                plane.findGeoIntersections(new Ray(new Point3D(1, 0, 1), new Vector(1, 0, 0))));

        // =============== Boundary Values Tests ==================

        // TC03: Ray is parallel to the plane
        assertNull("Ray's line parallel to the plane",
                plane.findGeoIntersections(new Ray(new Point3D(0, 0, 2), new Vector(1, -1, 0))));

        // TC04: Ray is on the plane
        assertNull("Ray's line is on the plane",
                plane.findGeoIntersections(new Ray(new Point3D(0, 0, 1), new Vector(1, -1, 0))));

        // TC05: Ray is perpendicular to the plane
        result = plane.findGeoIntersections(new Ray(new Point3D(0, 0, -1), new Vector(1, 1, 1)));
        pointsList = List.of(result.get(0).point);
        assertEquals("Ray's line perpendicular to the plane", List.of(new Point3D((double)2/3, (double)2/3, -(double)1/3)),
                pointsList);

        // TC06: Ray's point is on the plane
        assertNull("Ray's point is on the plane",
                plane.findGeoIntersections(new Ray(new Point3D(1, 0, 0), new Vector(1, 2, 3))));
        // TC06: Ray's point is on the plane and equals to the plane's point
        assertNull("Ray's point is on the plane and is equals to the plane's point",
                plane.findGeoIntersections(new Ray(new Point3D(0, 0, 1), new Vector(1, 2, 3))));
    }



}