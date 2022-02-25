package renderer;

import java.util.MissingResourceException;

import elements.Camera;
import primitives.Color;
import primitives.Ray;

public class Render {
    private ImageWriter imageWriter;
    private Camera camera;
    private RayTracerBase rayTracerBase;

    /**
     * set Image Writer
     * @param new imageWriter_
     * @return this Render
     */
    public Render setImageWriter(ImageWriter imageWriter_)
    {
        imageWriter = imageWriter_;
        return this;
    }

    /**
     * set camera
     * @param new camera
     * @return this Render
     */
    public Render setCamera(Camera camera_)
    {
        camera = camera_;
        return this;
    }

    /**
     * set ray tracer
     * @param new ray tracer
     * @return this Render
     */
    public Render setRayTracer(RayTracerBase rayTracer_)
    {
        rayTracerBase = rayTracer_;
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

        for(int i = 0 ; i < Nx ; i++)
            for(int j = 0 ; j< Ny; j++)
            {
                ray = camera.constructRayThroughPixel(Nx, Ny, j, i);
                color = rayTracerBase.traceRay(ray);
                imageWriter.writePixel(j, i, color);
            }
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

