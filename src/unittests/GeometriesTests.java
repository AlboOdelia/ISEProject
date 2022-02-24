package unittests;

import static org.junit.Assert.*;

import java.util.List;

import geometries.*;
import org.junit.Test;


import static org.junit.Assert.*;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import org.junit.Test;

public class GeometriesTests {

    @Test
    public void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: the simple case of adding geometries
        Sphere sphere = new Sphere(Point3D.ZERO, 1);
        Point3D point3d = new Point3D(1, 2, 3);
        Plane plane = new Plane(point3d, new Vector(point3d));
        //Plane plane2 = new Plane(point3d, new Vector(0,0,1));
        Geometries geometries1 = new Geometries(sphere);
        Geometries geometries2 = new Geometries();
        geometries1.add(plane);
        assertEquals("wrong number of geometries", 2, geometries1.getNumberOfGeometries());

        //TC02: add geometries to an empty object
        geometries2.add(sphere, plane);
        assertEquals("wrong number of geometries", 2, geometries2.getNumberOfGeometries());
    }

    @Test
    public void testfindGeoIntersections() {
        Geometries geometries = new Geometries(new Sphere( new Point3D(1, 0, 0),1d), new Plane(new Point3D(0, 0, 1), new Vector(1, 1, 1)),
                new Triangle(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, -1, 0)));
        //#EP: TC01: intersect with some of the shapes
        List<Intersectable.GeoPoint>  result  = geometries.findGeoIntersections(new Ray(new Point3D(0,0,0.5),
                new Vector(0.11, -0.15, 0.75)));
        assertEquals("dosent intersect with the right amount of points", 2, result.size());

        // =============== Boundary Values Tests ==================
        // TC2: the collection is empty
        Geometries geometries2= new Geometries();
        assertNull("there are no shapes in the collection", geometries2.findGeoIntersections(new Ray(new Point3D(0,6,0),new Vector(-6,-6,0))));

        Geometries geometries3= new Geometries(new Sphere(new Point3D(1, 0, 0),3),new Plane(new Point3D(0,0,1), new Point3D(1, 0, 0),new Point3D(0, 1, 0)),new Triangle(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0)));
        // TC3: there is no shape that intersect
        assertNull("there are no shapes in the collection", geometries3.findGeoIntersections(new Ray(new Point3D(0,-4,0),new Vector(-6,4,0))));

        // TC4:  only one shape is intersect
        assertEquals("some shapes are intersect and some not", 1, geometries3.findGeoIntersections(new Ray(new Point3D(0,6,0),new Vector(-6,-6,0))).size());

        // TC5:  all shapes are intersected
        assertEquals("some shapes are intersect and some not", 4, geometries3.findGeoIntersections(new Ray(new Point3D(-4,-4,0),new Vector(4,4,0.5))).size());



    }
}
