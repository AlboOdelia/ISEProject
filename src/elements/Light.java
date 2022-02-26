package elements;

import primitives.Color;

/**
 * abstract class representing the basic elements of lights.
 * the field is intensity with is the color of the lights
 * @author Odelia Albo 214089047
 */
abstract class Light {
    private Color intensity;

    /**
     * Constructor
     * we get the intensity of the color
     * @param intensity the intensity
     */
    protected Light(Color intensity)
    {
        this.intensity=intensity;
    }

    /**
     * getter
     * @return the intensity of the color
     */
    public Color getIntensity() {
        return intensity;
    }

}
