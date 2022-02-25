package elements;

import primitives.*;

/**
 * An ambient light source represents a fixed-intensity
 * and fixed color light source that affects all objects in the scene equally
 * each view-plane is represented by: intensity.
 * using the following equation:
 *      intensity=Ip=Ka*Ia
 * @author Odelia albo 21408904
 */
public class AmbientLight extends Light{


    /**
     * constructor that calculates the Ambient light intensity
     * using the following equation:
     * intensity=Ip=Ka*Ia
     * @param Ia - the color
     * @param Ka - the attenuation factor
     */
    public AmbientLight(Color Ia, double Ka)
    {
        super(Ia.scale(Ka));
    }


    /**
     * Default constructor- set the color to black
     */
    public AmbientLight()
    {
        super(Color.BLACK);
    }


}
