package renderer;

import java.util.List;
import static primitives.Util.alignZero;


import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import scene.Scene;

/**
 * an abstract class that is used to color the scene
 * @author Odelia Albo 214089047
 */
public abstract class RayTracerBase {
    //a scene
    protected Scene scene;

    /**
     * constructor used to initialize the scene filed
     * @param scene
     */
    public RayTracerBase(Scene scene) {
        this.scene=scene;
    }

    /**
     * an abstract func that gets a ray, we search the objects the ray hits
     * and trough that we can calculate the value of the color
     * it considers the different factors (ambient light, emmision light, light sources exet.)
     * @param ray -  the traced ray
     * @return the discovered color
     */
    public abstract Color traceRay(Ray ray);
}