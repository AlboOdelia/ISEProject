package elements;


import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public interface LightSource {
    /***
     *  intensity of specific point effected with light source
     * @param p
     * @return
     */
    public Color getIntensity(Point3D p);

    /***
     * vector from light sorse to point
     * @param p
     * @return
     */
    public Vector getL(Point3D p);

    /**
     * Calculates the distance between the light source and the point
     * @param p - point
     * @return - distance to point (double)
     */
    public double getDistance(Point3D p);

}