/**
 *
 */
package unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import primitives.Point3D;
import primitives.Vector;

/**
 * @author odelia
 *
 */
public class Point3DTests {

    /**
     * Test method for {@link primitives.Point3D#subtract(primitives.Point3D)}.
     */
    @Test
    public void testSubtract() {
        Point3D v1=new Point3D(-1, 0, 1);
        Point3D v2=new Point3D(1, 2, 3);
        Vector result=new Vector(-2, -2, -2);
        assertEquals(v1.subtract(v2), result);
    }

    /**
     * Test method for {@link primitives.Point3D#add(primitives.Vector)}.
     */
    @Test
    public void testAdd() {
        Point3D v1=new Point3D(-1, 0, 1);
        Vector v2=new Vector(1, 2, 3);
        Point3D result=new Point3D(0, 2, 4);
        assertEquals(v1.add(v2), result);
    }

    /**
     * Test method for {@link primitives.Point3D#distanceSquared(primitives.Point3D)}.
     */
    @Test
    public void testDistanceSquared() {
        //Distance from zero
        Point3D v2=new Point3D(0, 0, 0);
        Point3D v1=new Point3D(-1, 0, 1);
        assertEquals("", 2, v1.distanceSquared(v2),1e-10);

        //Distance from another point
        Point3D v3=new Point3D(1, 2, 3);

        assertEquals("", 12, v1.distanceSquared(v3),1e-10);
    }

    /**
     * Test method for {@link primitives.Point3D#distance(primitives.Point3D)}.
     */
    @Test
    public void testDistance() {
        //Distance from zero
        Point3D v2=new Point3D(0, 0, 0);
        Point3D v1=new Point3D(1, 0, 1);
        assertEquals("", Math.sqrt(2), v1.distance(v2),1e-10);

        //Distance from another point
        Point3D v3=new Point3D(1, 2, 3);

        assertEquals("", Math.sqrt(8), v1.distance(v3),1e-10);
    }

}
