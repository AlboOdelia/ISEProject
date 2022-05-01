/**
 *
 */
package unittests;

import geometries.*;
import org.junit.Test;

import elements.*;
import primitives.*;
import renderer.*;
import scene.Scene;

public class miniProject1 {
    private Scene scene = new Scene("Test scene");


    /**
     * Produce a picture of a two triangles lighted by a spot light with a partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void ourPicture() {
        Camera camera = new Camera.BuilderCamera( new Point3D(0, 0, 1000),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0)) //
                .build().setViewPlaneSize(200, 127).setDistance(800);
        //teal
        scene.setAmbientLight(new AmbientLight(new Color(209, 240, 251), 0.1));

        setGeometries(scene);


        //brown
        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point3D(60, 50, 0), new Vector(0, 0, -1)) //
                .setkL(4E-5).setkQ(2E-7));
        //light brown
        scene.lights.add(new SpotLight(new Color(200, 180, 180), new Point3D(914, 826, -130), new Vector(-959.0, -826.0, -65.0)) //
                .setkL(4E-5).setkQ(2E-7));
        //grey
        scene.lights.add(new PointLight(new Color(60,60,60), new Point3D(215, 368, 0))//
                .setkL(0.00001).setkQ(0.000001));


        ImageWriter imageWriter2 = new ImageWriter("miniProject1.p1", 900, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter2) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene)).setMode(1);

        render.renderImage();
        render.writeToImage();
    }//end
    private void setGeometries(Scene scene) {

        scene.geometries.add(

                // surface
                new Polygon(
                        new Point3D(-100, -50, -150),
                        new Point3D(-100, -50, 200),
                        new Point3D(100, -50, 200),
                        new Point3D(100, -50, -150))
                        .setEmission(new Color(224, 179, 255))
                        .setMaterial(new Material()),

                //reflective sphere

                new Sphere(new Point3D(40, -28, 0), 22)
                        .setEmission(new Color(30,40,50))
                        .setMaterial(new Material()
                                .setkR(0.8)),


                //sphere

                new Sphere(new Point3D(-30, -10, 0), 40)
                        .setEmission(new Color(0, 0, 100))
                        .setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20).setkT(0.7)),

                new Sphere(new Point3D(-30, -10, 0), 20)
                        .setEmission(new Color(96, 0, 128))
                        .setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20)),
                //sphere

                new Sphere(new Point3D(10, 0, 0), 20)
                        .setEmission(new Color(0, 0, 100))
                        .setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20).setkT(0.6)),

                new Sphere(new Point3D(10, 0, 0), 10)
                        .setEmission(new Color(191, 0, 255))
                        .setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20)),


                //sphere

                new Sphere(new Point3D(30, 10, 0), 10)
                        .setEmission(new Color(0, 0, 100))
                        .setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20).setkT(0.5)),

                new Sphere(new Point3D(30, 10, 0), 5)
                        .setEmission(new Color(223, 128, 255))
                        .setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20)),
                //front block
                //1
                new Polygon(
                        new Point3D(-100, -50, 75),
                        new Point3D(-100, 30, 75),
                        new Point3D(-62, 30, 75),
                        new Point3D(-62, -50, 75))
                        .setEmission(new Color(0, 0, 0))
                        .setMaterial(new Material()
                                .setkT(0.9)),
                new Polygon(
                        new Point3D(-62, -50, 75),
                        new Point3D(-62, 30, 75),
                        new Point3D(-58, 30, 75),
                        new Point3D(-58, -50, 75))
                        .setEmission(new Color(77, 0, 102))
                        .setMaterial(new Material()
                                .setkD(0.6).setkS(0.4).setkT(0.2)
                                .setnShininess(50)),
                new Polygon(
                        new Point3D(-58, -50, 75),
                        new Point3D(-58, 30, 75),
                        new Point3D(-38 ,30, 75),
                        new Point3D(-38, -50, 75))
                        .setEmission(new Color(40, 40, 40))
                        .setMaterial(new Material()
                                .setkT(0.9)),
                //2
                new Polygon(
                        new Point3D(-38, -50, 75),
                        new Point3D(-38, 30, 75),
                        new Point3D(0, 30, 75),
                        new Point3D(0, -50, 75))
                        .setEmission(new Color(40, 40, 40))
                        .setMaterial(new Material()
                                .setkT(0.9)),
                new Polygon(
                        new Point3D(0, -50, 75),
                        new Point3D(0, 30, 75),
                        new Point3D(4, 30, 75),
                        new Point3D(4, -50, 75))
                        .setEmission(new Color(191, 0, 255))
                        .setMaterial(new Material()
                                .setkD(0.6).setkS(0.4).setkT(0.2)
                                .setnShininess(50)),
                new Polygon(
                        new Point3D(4, -50, 75),
                        new Point3D(4, 30, 75),
                        new Point3D(42 ,30, 75),
                        new Point3D(42, -50, 75))
                        .setEmission(new Color(80, 80, 80))
                        .setMaterial(new Material()
                                .setkT(0.9)),
                //3
                new Polygon(
                        new Point3D(42, -50, 75),
                        new Point3D(42, 30, 75),
                        new Point3D(62, 30, 75),
                        new Point3D(62, -50, 75))
                        .setEmission(new Color(80, 80, 80))
                        .setMaterial(new Material()
                                .setkT(0.9)),
                new Polygon(
                        new Point3D(62, -50, 75),
                        new Point3D(62, 30, 75),
                        new Point3D(66, 30, 75),
                        new Point3D(66, -50, 75))
                        .setEmission(new Color(223, 128, 255))
                        .setMaterial(new Material()
                                .setkD(0.6).setkS(0.4).setkT(0.2)
                                .setnShininess(50)),
                new Polygon(
                        new Point3D(66, -50, 75),
                        new Point3D(66, 30, 75),
                        new Point3D(100 ,30, 75),
                        new Point3D(100, -50, 75))
                        .setEmission(new Color(100, 100, 100))
                        .setMaterial(new Material()
                                .setkT(0.9)),

                //back panel
                new Polygon(
                    new Point3D(-38, -50, -75),
                    new Point3D(-38, 30, -75),
                    new Point3D(42, 30, -75),
                    new Point3D(42, -50, -75))
                    .setEmission(new Color(77, 0, 102))
                    .setMaterial(new Material()
                            .setkD(0.6).setkS(0.4).setkT(0.2)
                            .setnShininess(50)),
                //top panel
                new Polygon(
                        new Point3D(-38, 30, -75),
                        new Point3D(42, 30, -75),
                        new Point3D(66, 30, 75),
                        new Point3D(-62, 30, 75))
                        .setEmission(new Color(191, 0, 255))
                        .setMaterial(new Material()
                                .setkD(0.6).setkS(0.4).setkT(0.2)
                                .setnShininess(50))

        );
    }
}