package unittests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import geometries.Plane;
import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
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
 //   @Test
//    public void testFindIntersections() {
//        // ============ Equivalence Partitions Tests ==============
//        // TC01:  Ray intersects the plane
//        Plane p=new Plane(new Point3D(0,0,1), new Point3D(1, 0, 0),new Point3D(0, 1, 0));
//        List<Point3D> temp=p.findIntsersections(new Ray(new Point3D(2, 0, 0), new Vector(new Point3D(-2, -2, 0))));
//        assertTrue("Wrong number of points", Util.isZero(temp.size()-1));
//        assertEquals("Ray crosses the plane", List.of(new Point3D(1.5,-0.5,0)), temp);
//
//        // TC02: Ray does not intersect the plane
//        assertNull("Ray's line out of plane",p.findIntsersections(new Ray(new Point3D(2, 0, 0), new Vector(new Point3D(2, 2, 0)))));
//
//        // =============== Boundary Values Tests ==================
//        // TC11:Ray is parallel to the plane-not included in the plane
//        assertNull("Ray is parallel to the plane-not included in the plane",p.findIntsersections(new Ray(new Point3D(0, 2, 0), new Vector(new Point3D(2, -2, 0)))));
//
//        // TC12:Ray is parallel to the plane-included in the plane
//        assertNull("Ray is parallel to the plane-included in the plane", p.findIntsersections(new Ray(new Point3D(0, 1, 0), new Vector(new Point3D(2, -2, 0)))));
//
//        // TC13:Ray is orthogonal to the plane- f0 before the plane
//        assertEquals("Ray is orthogonal to the plane-f0 before the plane ", List.of(new Point3D((double)1/3,(double)1/3,(double)1/3)), p.findIntsersections(new Ray(new Point3D(2, 2, 2), new Vector(new Point3D(-1, -1, -1)))));
//
//        // TC13:Ray is orthogonal to the plane- f0 after the plane
//        assertNull("Ray is orthogonal to the plane-f0 after the plane", p.findIntsersections(new Ray(new Point3D(0, 0, 0), new Vector(new Point3D(-1, -1, -1)))));
//
//        // TC13:Ray is orthogonal to the plane- f0 in the plane
//        assertNull("Ray is orthogonal to the plane- f0 in the plane", p.findIntsersections(new Ray(new Point3D(0, 0, 1), new Vector(new Point3D(1, 1, 1)))));
//
//        // TC13:f0 in the plane
//        assertNull("Ray starts in the plane", p.findIntsersections(new Ray(new Point3D(-1, 1, 1), new Vector(new Point3D(1, 1, 0)))));
//
//        // TC13:Ray is neither orthogonal nor parallel to the plane and begins in the same point which appears as reference point in the plane
//        assertNull("p0=q0", p.findIntsersections(new Ray(new Point3D(0, 0, 1), new Vector(new Point3D(1, 1, 0)))));
//
//
//    }



}