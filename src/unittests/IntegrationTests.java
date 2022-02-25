package unittests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import elements.Camera;
import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class IntegrationTests {

    /**
     * help function that gets camera and Intersectable shape and find how many
     * intersection points there are between the camera rays and the shape.
     * @param ca- the camera
     * @param in- the geometric list
     * @return the number of intersection points.
     */
    int funcSumOfPoints(Camera ca,Intersectable in){
        int sumOfPoints=0;
        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                Ray r=ca.setViewPlaneSize(3, 3).constructRayThroughPixel(3, 3, i, j);
                if(in.findGeoIntersections(r)!=null)
                    sumOfPoints+=in.findGeoIntersections(r).size();
            }
        }
        return sumOfPoints;
    }

    /**
     * Test method for
     * {@link elements.Camera#constructRayThroughPixel(int, int, int, int)&sphere.findIntersections(ray)}.
     */
    @Test
    public void testIntegrationSphere() {
        // TC01 First test case- r=1
        Sphere sp=new Sphere(new Point3D(0, 0, -3),1);
        Camera ca=new Camera.BuilderCamera(new Point3D(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0)).setDistance(1).build();
        assertEquals("Bad number of Intersections with sphere-First case", funcSumOfPoints(ca,sp),2);

        // TC02 Second test case (r=2.5)
        sp=new Sphere(new Point3D(0, 0, -2.5),2.5);
        ca=new Camera.BuilderCamera(new Point3D(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0)).setDistance(1).build();
        assertEquals("Bad number of Intersections with sphere-Second case", funcSumOfPoints(ca,sp),18);

        // TC03 Third test case (r=2)
        sp=new Sphere(new Point3D(0, 0, -2),2);
        assertEquals("Bad number of Intersections with sphere-Third case", funcSumOfPoints(ca,sp),10);

        // TC04 Fourth test case  (r=4)
        sp=new Sphere(new Point3D(0, 0, -2),4);
        ca=new Camera.BuilderCamera(new Point3D(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0)).setDistance(1).build();
        assertEquals("Bad number of Intersections with sphere-Fourth case", funcSumOfPoints(ca,sp),9);

        // TC05 Fifth test case (r=0.5)
        sp=new Sphere(new Point3D(0, 0, 1),0.5);
        assertEquals("Bad number of Intersections with sphere-Fourth case", funcSumOfPoints(ca,sp),0);

    }

    /**
     * Test method for
     * {@link elements.Camera#constructRayThroughPixel(int, int, int, int)&Plane.findIntersections(ray)}.
     */
    @Test
    public void testIntegrationPlane() {
        // TC01 First test case- the plane is parallel
        Plane pl=new Plane(new Point3D(0, 0, -4),new Vector(0, 0, -1));
        Camera ca=new Camera.BuilderCamera(new Point3D(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0)).setDistance(1).build();
        assertEquals("Bad number of Intersections with sphere-first case", funcSumOfPoints(ca,pl),9);

        // TC02 Second test case plane
        pl=new Plane(new Point3D(0, 3,3),new Point3D(0, -3,3),new Point3D(-1,0, 0));
        ca=new Camera.BuilderCamera(new Point3D(1,0,1.5), new Vector(-1, 0, 0), new Vector(0, 0, 1)).setDistance(1).build();
        assertEquals("Bad number of Intersections with plane-Second case", funcSumOfPoints(ca,pl),9);

        // TC03 Third test case plane
        pl=new Plane(new Point3D(0, 3,3),new Point3D(0, -3,3),new Point3D(-6,0,1.5));
        ca=new Camera.BuilderCamera(new Point3D(1,0,1.5), new Vector(-1, 0, 0), new Vector(0, 0, 1)).setDistance(1).build();
        assertEquals("Bad number of Intersections with plane-Third case", funcSumOfPoints(ca,pl),6);
    }

    /**
     * Test method for
     * {@link elements.Camera#constructRayThroughPixel(int, int, int, int)&Triangle.findIntersections(ray)}.
     */
    @Test
    public void testIntegrationTriangle() {
        // TC01 First test case Triangle
        Triangle tr=new Triangle(new Point3D(0,1,-2),new Point3D(1,-1,-2),new Point3D(-1, -1,-2));
        Camera ca=new Camera.BuilderCamera(new Point3D(0, 0, 10), new Vector(0, 0, -1), new Vector(0, 1, 0)).setDistance(1).build();
        assertEquals("Bad number of Intersections with triangle-first case", funcSumOfPoints(ca,tr),1);

        // TC02 Second test case triangle
        tr=new Triangle(new Point3D(0,20,-2),new Point3D(1,-1,-2),new Point3D(-1, -1,-2));
        ca=new Camera.BuilderCamera(new Point3D(0, 0, 5), new Vector(0, 0, -1), new Vector(0, 1, 0)).setDistance(1).build();
        assertEquals("Bad number of Intersections with triangle-second case", funcSumOfPoints(ca,tr),2);

    }

}