package elements;


import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * interface representing the basic func of lights.
 * including:   -getIntensity: returns the intensity of specific point effected with light source
 *              -getL: returns the vector from light source to point
 *              -getDistance:  returns the Calculates the distance between the light source and the point
 * @author Odelia Albo 214089047
 */
public interface LightSource {
    /***
     *  returns the intensity of specific point effected with light source
     * @param p
     * @return
     */
    public Color getIntensity(Point3D p);

    /***
     * returns the vector from light source to point
     * @param p
     * @return
     */
    public Vector getL(Point3D p);

    /**
     *  returns the Calculates the distance between the light source and the point
     * @param p - point
     * @return - distance to point (double)
     */
    public double getDistance(Point3D p);

}