package unittests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import geometries.Geometries;
import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class GeometriesTests {


    @Test
    public void testFindIntsersections() {
        Geometries geometries = new Geometries(new Sphere(1d, new Point3D(1, 0, 0)), new Plane(new Point3D(0, 0, 1), new Vector(1, 1, 1)),
                new Triangle(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, -1, 0)));
        //#EP: TC01: intersect with some of the shapes
        List<Point3D>  result  = geometries.findIntsersections(new Ray(new Point3D(0,0,0.5),
                new Vector(0.11, -0.15, 0.75)));
        assertEquals("dosent intersect with the right amount of points", 2, result.size());

        // =============== Boundary Values Tests ==================
        // TC2: the collection is empty
        Geometries geometries2= new Geometries();
        Geometries geometries3= new Geometries(new Sphere(3,new Point3D(1, 0, 0)),new Plane(new Point3D(0,0,1), new Point3D(1, 0, 0),new Point3D(0, 1, 0)),new Triangle(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0)));
        assertNull("thre are no shapes in the collection", geometries2.findIntsersections(new Ray(new Point3D(0,6,0),new Vector(-6,-6,0))));

        // TC3: there is no shape that intersect
        assertNull("thre are no shapes in the collection", geometries3.findIntsersections(new Ray(new Point3D(0,-4,0),new Vector(-6,4,0))));

        // TC4:  only one shape is intersect
        assertEquals("some shapes are intersect and some not", 1, geometries3.findIntsersections(new Ray(new Point3D(0,6,0),new Vector(-6,-6,0))).size());

        // TC5:  all shapes are intersect
        assertEquals("some shapes are intersect and some not", 4, geometries3.findIntsersections(new Ray(new Point3D(-4,-4,0),new Vector(4,4,0.5))).size());



    }
}
