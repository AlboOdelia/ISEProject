package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Util;
import primitives.Vector;

/**
 * SpotLight class representing the SpotLight in a 3D space.
 * SpotLight is a models point light source with direction (such as a luxo lamp)
 * SpotLight has attenuation with distance meaning it's affected by distance
 * and shows only in the specifier direction vector
 * each PointLight is represented by:   - intensity: color of light (from class Light)
 *                                      - Position: a point representing the position of light in scene
 *                                      - direction: a vector representing the dir of light
 *                                      - kC: the constant attenuation factor
 *                                      - kL: the linear attenuation factor
 *                                      - kQ: the square attenuation factor
 *
 * @author Odelia Albo 214089047
 */
public class SpotLight extends PointLight {

    //a vector representing the dir of light
    private Vector direction;

    /**
     * Constructor for point light object
     * we get the intensity of the color, position, director and Kc, Kl, Kq the attenuation factors
     * @param intensity - intensity of point light source
     * @param position - location of point light object on scene
     * @param kC - the constant attenuation factor
     * @param kL - the linear attenuation factor
     * @param kQ - the square attenuation factor
     * @param direction - direction vector for the light of spot light object on scene
     */
    public SpotLight(Color intensity, Point3D position, double kC, double kL, double kQ, Vector direction) {
        super(intensity, position, kC, kL, kQ);
        this.direction = direction.normalized();
    }

    /**
     * default Constructor for point light object
     * we get the intensity of the color, position
     * attenuation factors are default with are 1 0 0
     * @param intensity - the intensity
     * @param position - a point representing the position of light in scene
     */
    public SpotLight(Color intensity, Point3D position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalized();
    }

    /**
     * Implementation of getIntensity of interface LightSource by calling Light's getIntensity
     * and adding the effects of attenuation with distance
     * spotLight func for intensity is max(0,dir*l)*PointLight.getIntensity(p)
     * we need to know the dot product to determine if light will reach the point
     * @param p- the point that we want to know the light intensity of
     * @return the intensity of the color on a given point
     */
    @Override
    public Color getIntensity(Point3D p) {
        double dotProduct = Util.alignZero(direction.dotProduct(getL(p)));
        if (dotProduct < 0)
            dotProduct = 0;
        return super.getIntensity(p).scale(dotProduct);
    }
}
