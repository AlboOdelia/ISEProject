package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * PointLight class representing the PointLight in a 3D space.
 * PointLight is a models omnidirectional point source (such as a bulb)
 * PointLight has attenuation with distance meaning it's affected by distance
 * each PointLight is represented by:   - intensity: color of light (from class Light)
 *                                      - Position: a point representing the position of light in scene
 *                                      - kC: the constant attenuation factor
 *                                      - kL: the linear attenuation factor
 *                                      - kQ: the square attenuation factor
 *
 * @author Odelia Albo 214089047
 */
public class PointLight extends Light implements LightSource {



    // a point representing the position of light in scene
    private Point3D position;

    //the way the distance effects the intensity
    // kC - the constant attenuation factor
    private double kC;
    //kL - the linear attenuation factor
    private double kL;
    //kQ - the square attenuation factor
    private double kQ;


    /**
     * Constructor for point light object
     * we get the intensity of the color, position, and Kc, Kl, Kq the attenuation factors
     * @param intensity - the intensity
     * @param position - a point representing the position of light in scene
     * @param kC - the constant attenuation factor
     * @param kL - the linear attenuation factor
     * @param kQ - the square attenuation factor
     */
    public PointLight(Color intensity, Point3D position, double kC, double kL, double kQ) {
        super(intensity);
        this.position = position;
        this.kC = kC;
        this.kL = kL;
        this.kQ = kQ;
    }

    /**
     * default Constructor for point light object
     * we get the intensity of the color, position
     * attenuation factors are default with are 1 0 0
     * @param intensity - the intensity
     * @param position - a point representing the position of light in scene
     */
    public PointLight(Color intensity, Point3D position) {
        super(intensity);
        this.position = position;
        this.kC = 1;
        this.kL = 0;
        this.kQ = 0;
    }

    /**
     * builder pattern set - set the kC parameter - the constant attenuation factor
     * @param kC - constant attenuation factor
     * @return - point light object
     */
    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * builder pattern set - set the kL parameter - the linear attenuation factor
     * @param kL - linear attenuation factor
     * @return - point light object
     */
    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * builder pattern set - set the kq parameter - the square attenuation factor
     * @param kQ - square attenuation factor
     * @return - point light object
     */
    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * Implementation of getIntensity of interface LightSource by calling Light's getIntensity
     * and adding the effects of attenuation with distance
     * @param p- the point that we want to know the light intensity of
     * @return the intensity of the color on a given point
     */
    @Override
    public Color getIntensity(Point3D p) {
        return getIntensity().scale(calcAttenuation(p));
    }

    /**
     * Assistive function for DRY principle
     * returns the attenuation with distance using the following func:
     * 1/ (kc + kl*d + kq*d^2)
     * kC - the constant attenuation factor
     * kL - the linear attenuation factor
     * kQ - the square attenuation factor
     * @param p - specific point we want to know the Attenuation with distance of
     * @return scalar attenuation factor (double)
     */
    private double calcAttenuation(Point3D p) {
        double d = p.distance(position);
        return  (double)(1.0/(double)(kC+ kL*d + kQ*d*d));
    }

    /**
     * Implementation of getL of interface LightSource
     * returning the normalized vector from position to the specific point
     * @param p-  the point that we want to know the light direction of
     * @return the direction of light
     */
    @Override
    public Vector getL(Point3D p) {
        return (p.subtract(position)).normalized();
    }

    /**
     * Implementation of getDistance of interface LightSource
     * distance is defined as the distance from position point
     * @param p - point
     * @return the distance from position point
     */
    @Override
    public double getDistance(Point3D p) {
        return position.distance(p);
    }
}
