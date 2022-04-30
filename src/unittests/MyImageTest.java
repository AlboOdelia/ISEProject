
package unittests;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

public class MyImageTest {
    private Scene scene = new Scene("Test scene");
    /**
     * a image with at least 4 geometries
     */
    @Test
    public void creatImageTest() {
        Camera camera = new Camera.BuilderCamera(new Point3D(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)).build() //
                .setViewPlaneSize(2500, 2500).setDistance(10000); //

        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        scene.geometries.add( //
                new Sphere( new Point3D(-950, -900, -1000),400) //
                        .setEmission(new Color(0, 255, 0)) //
                        .setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20).setkT(0.7)),
                new Sphere( new Point3D(-950, -900, -1000),200) //
                        .setEmission(new Color(0, 0, 200)) //
                        .setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20)),
                new Sphere( new Point3D(-950, 0, -1000),400) //
                        .setEmission(new Color(0, 0, 200)) //
                        .setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20).setkT(0.6)),
                new Sphere( new Point3D(-950, 0, -1000),200) //
                        .setEmission(new Color(0, 153, 255)) //
                        .setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20)),
                new Sphere( new Point3D(-950, 900, -1000),400) //
                        .setEmission(new Color(204, 102, 255)) //
                        .setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20).setkT(0.5)),
                new Sphere( new Point3D(-950, 900, -1000),200) //
                        .setEmission(new Color(0, 0, 200)) //
                        .setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20)),

                new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
                        new Point3D(670, 670, 3000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setkR(1)),
                new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
                        new Point3D(-1500, -1500, -2000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setkR(0.5)));

        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point3D(-750, -750, -150), new Vector(-1, -1, -4)) //
                .setkL(0.00001).setkQ(0.000005));
        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point3D(-750, 0, -150), new Vector(-1, -1, -4)) //
                .setkL(0.00001).setkQ(0.000005));
        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point3D(-750, 750, -150), new Vector(-1, -1, -4)) //
                .setkL(0.00001).setkQ(0.000005));

        ImageWriter imageWriter = new ImageWriter("reflectiontry", 500, 500);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene));

        render.renderImage();
        render.writeToImage();
    }

}
