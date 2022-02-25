package elements;

import primitives.Color;

abstract class Light {
    private Color intensity;
    /***
     * Constructor
     * we get the intensity of the color
     * @param i the intensity
     */
    protected Light(Color i)
    {
        intensity=i;
    }
    /***
     * getter
     * @return the intensity of the color
     */
    public Color getIntensity() {
        return intensity;
    }

}
