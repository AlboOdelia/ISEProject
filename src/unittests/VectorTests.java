/**
 *
 */
package unittests;

import static org.junit.Assert.*;
import static java.lang.System.out;
import static primitives.Util.isZero;

import org.junit.Test;

import primitives.Vector;

/**
 * @author odelia albo
 *
 */
public class VectorTests {

    /**
     * Test method for {@link primitives.Vector#subtract(primitives.Vector)}.
     */
    @Test
    public void testSubtract() {
        Vector v1=new Vector(-1, 0, 1);
        Vector v2=new Vector(1, 2, 3);
        Vector result=new Vector(-2, -2, -2);
        assertEquals(v1.subtract(v2), result);
    }

    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    public void testAdd() {
        Vector v1=new Vector(-1, 0, 1);
        Vector v2=new Vector(1, 2, 3);
        Vector result=new Vector(0, 2, 4);
        assertEquals(v1.add(v2), result);
    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    public void testScale() {
        Vector v1=new Vector(-1, 0, 1);
        Vector result=new Vector(-2, 0, 2);
        assertEquals(v1.scale(2), result);
    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared()}.
     */
    @Test
    public void testLengthSquared() {
        Vector v1=new Vector(-1, 0, 1);
        assertEquals("", 2, v1.lengthSquared(),1e-10);
    }

    /**
     * Test method for {@link primitives.Vector#normalize()}.
     */
    @Test
    public void testNormalize() {
        Vector v = new Vector(3.5,-5,10);
        v.normalize();
        assertEquals("", 1, v.length(),1e-10);

        try {
            v = new Vector(0,0,0); //CANT CREATE VECTOR 0
            v.normalize();			//those we don't come across the divisitoin of 0
            fail("Didn't throw divide by zero exception!");
        }
        catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    /**
     * Test method for {@link primitives.Vector#normalized()}.
     */
    @Test
    public void testNormalized() {
        Vector v = new Vector(3.5,-5,10);
        Vector u=v.normalized();
        assertEquals("", 1, u.length(),1e-10);
        assertNotEquals(u, v);
    }

    /**
     * Test method for {@link primitives.Vector#length()}.
     */
    @Test
    public void testLength() {
        Vector v1 = new Vector(1, 2, 3);
        if (!isZero(v1.lengthSquared() - 14))
            out.println("ERROR: lengthSquared() wrong value");
        if (!isZero(new Vector(0, 3, 4).length() - 5))
            out.println("ERROR: length() wrong value");	}

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    public void testDotProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);
        if (!isZero(v1.dotProduct(v3)))
            out.println("ERROR: dotProduct() for orthogonal vectors is not zero");
        if (!isZero(v1.dotProduct(v2) + 28))
            out.println("ERROR: dotProduct() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     *
     * שלהם- לשנות
     */
    @Test
    public void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals("crossProduct() wrong result length", v1.length() * v2.length(), vr.length(), 0.00001);

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue("crossProduct() result is not orthogonal to 1st operand", isZero(vr.dotProduct(v1)));
        assertTrue("crossProduct() result is not orthogonal to 2nd operand", isZero(vr.dotProduct(v2)));

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-product of co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertFalse("crossProduct()", isZero(v1.dotProduct(v3)));
        // try {
        //     v1.crossProduct(v2);
        //     fail("crossProduct() for parallel vectors does not throw an exception");
        // } catch (Exception e) {}
    }
}