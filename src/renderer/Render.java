package renderer;

import java.util.MissingResourceException;

import elements.Camera;
import primitives.Color;
import primitives.Ray;

public class Render {
    private ImageWriter imageWriter;
    private Camera camera;
    private RayTracerBase rayTracerBase;

    // The square root of the number of rays sent through each pixel
    private int superSamp = 9;

    //0- no feature
    //1- super sampling
    private int mode = 1;

    /**
     * set Image Writer
     * @param imageWriter_ new imageWriter_
     * @return this Render
     */
    public Render setImageWriter(ImageWriter imageWriter_)
    {
        imageWriter = imageWriter_;
        return this;
    }

    /**
     * set camera
     * @param camera_ new camera
     * @return this Render
     */
    public Render setCamera(Camera camera_)
    {
        camera = camera_;
        return this;
    }

    /**
     * set ray tracer
     * @param rayTracer_ new ray tracer
     * @return this Render
     */
    public Render setRayTracer(RayTracerBase rayTracer_)
    {
        rayTracerBase = rayTracer_;
        return this;
    }

    /***
     * set super samp
     * @param s the new square root of the number of rays sent through each pixel
     * @return this render
     */
    public Render setSuperSamp(int s) {
        superSamp=s;
        return this;
    }

    /***
     * set mode
     * @param m 0- no feature
     *          1- super sampling
     * @return this render
     */
    public Render setMode(int m) {
        if(m>=0 && m<=2)
            mode = m;
        return this;
    }


    public void renderImage()
    {
        if(imageWriter == null ||camera == null || camera.getHeight() == 0 ||
                camera.getWidth() == 0 ||rayTracerBase == null)
            throw new MissingResourceException("some of the fields are uninitialized","","");
        int Nx = imageWriter.getNx();
        int Ny = imageWriter.getNy();
        Ray ray;
        Color color;

        for(int i = 0 ; i < Ny ; i++)
            for(int j = 0 ; j< Nx; j++)
            {
                color =new Color(0, 0, 0);
                if(mode == 0) {
                    ray = camera.constructRayThroughPixel(Nx, Ny, j, i);
                    color = rayTracerBase.traceRay(ray);
                }
                else if (mode == 1)
                    color =SuperSampling_MiniProject1(Nx, Ny, j, i);
                imageWriter.writePixel(j, i, color);
            }
    }

    public Color SuperSampling_MiniProject1(int nX, int nY, int j, int i) {//4:51 minutes for 81 rays for the ReflectionRefractionTest
        Color averageColor=new Color(0, 0, 0);
        for (int ii=0;ii<superSamp;ii++) { //sum all the colors
            for(int jj=0;jj<superSamp;jj++) {
                Ray ray=camera.constructRayThroughPixel(nX*superSamp, nY*superSamp, j*superSamp+jj, i*superSamp+ii);
                Color c = rayTracerBase.traceRay(ray);
                averageColor= averageColor.add(c);
            }
        }
        averageColor=averageColor.reduce(superSamp*superSamp);//divided by num of colors to get average
        return averageColor;
    }


    /**
     * insert grid lines over the view plane
     * @param interval between lines
     * @param color of the grid
     */
    public void printGrid(int interval, Color color)
    {
        if(imageWriter == null)
            throw new MissingResourceException("imageWriter is uninitialized", "", "");
        int Nx = imageWriter.getNx();
        int Ny = imageWriter.getNy();
        for(int i = 0 ; i < Nx ; i++)
            for(int j = 0 ; j< Ny; j++)
                if(i % interval == 0 || j % interval == 0)
                    imageWriter.writePixel(i, j, color);
    }

    /**
     * this function create the png file of the image (if possible)
     */
    public void writeToImage()
    {
        if(imageWriter == null)
            throw new MissingResourceException("not all the fields are full", "Image writer", "is null");

        imageWriter.writeToImage();
    }



}

