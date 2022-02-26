package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * DirectionalLight class representing the DirectionalLight in a 3D space.
 * DirectionalLight is a light source that is infinitely far
 * but has no attenuation with distance meaning it's not affected by distance
 * each DirectionalLight is represented by: - intensity: color of light (from class Light)
 *                                          - direction: a vector representing the dir of light
 * @author Odelia Albo 214089047
 */
public class DirectionalLight extends Light implements LightSource {

    //a vector representing the dir of light
    private Vector direction;

    /**
     * Constructor
     * we get the intensity of the color and the direction of the light
     * we assign the filed with the given params (dir normalized)
     * @param intensity the intensity(color)
     * @param direction a vector representing the dir of light
     */
    public  DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction=direction.normalized();
    }

    /**
     * Implementation of getIntensity of interface LightSource by calling Light's getIntensity
     * in DirectionalLight the intensity is not affected by distance
     * meaning the intensity is same no mather the point
     * @param p- the point that we want to know the light intensity of
     * @return the intensity of the color on a given point
     */
    @Override
    public Color getIntensity(Point3D p) {
        return getIntensity();
    }

    /**
     * Implementation of getL of interface LightSource
     * returning the dir of DirectionalLight
     * @param p-  the point that we want to know the light direction of
     * @return the direction of light
     */
    @Override
    public Vector getL(Point3D p) {
        return direction;
    }

    /**
     * Implementation of getDistance of interface LightSource
     * distance is infinite - const variable Double.POSITIVE_INFINITY
     * @param p - point
     * @return infinite distance
     */
    @Override
    public double getDistance(Point3D p) {
        return Double.POSITIVE_INFINITY;
    }
}
