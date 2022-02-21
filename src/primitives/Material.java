package primitives;

/**
 * Class Material is the class representing the material of the geometry
 * @author Albo Odelia 214089047
 * updated
 */
public class Material {
    /**
     * kD - attenuation factor for Diffuse Reflection- the reflection of light from a surface
     * kS - attenuation factor for Specular Reflection, the mirror-like reflection of light from a surface.
     * nShininess - the shining level of the material
     * kT - Transparency level
     * kR - level of reflection
     */
    public double kD = 0;
    public double kS = 0;
    public int nShininess = 0;
    public double kT = 0;
    public double kR = 0;

    /**
     *
     * @param kD-Diffuse value of object
     * @return Material object
     */
    public Material setkD(double kD) {
        this.kD = kD;
        return this;
    }

    /**
     *
     * @param kT-transparency value of object
     * @return Material object
     */
    public Material setkT(double kT) {
        this.kT = kT;
        return this;
    }

    /**
     *
     * @param kR-reflection  value of object
     * @return Material object
     */
    public Material setkR(double kR) {
        this.kR = kR;
        return this;
    }

    /**
     *
     * @param kS- Specular value of object
     * @return Material object
     */
    public Material setkS(double kS) {
        this.kS = kS;
        return this;
    }

    /**
     *
     * @param nShininess- the shininess of the object
     * @return Material object
     */
    public Material setnShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

}