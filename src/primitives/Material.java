package primitives;

/**
 * Class Material is the class representing the material of the geometry
 * each Material is represented by: - kD: diffuse reflection constant, the ratio of reflection of the diffuse term of incoming light
 *                                  - kS: specular reflection constant, the ratio of reflection of the specular term of incoming light
 *                                  - nShininess:shininess constant for this material, which is larger for surfaces that are smoother and more mirror-like.
 *                                  When this constant is large the specular highlight is small.
 *                                  - kT: Transparency level
 *                                  - kR: level of reflection
 * @author Albo Odelia 214089047
 */
public class Material {

    //diffuse reflection constant, the ratio of reflection of the diffuse term of incoming light
    public double kD = 0;
    //specular reflection constant, the ratio of reflection of the specular term of incoming light
    public double kS = 0;
    //shininess constant for this material, which is larger for surfaces that are smoother and more mirror-like.
    // When this constant is large the specular highlight is small.
    public int nShininess = 0;
    //Transparency level
    public double kT = 0;
    //level of reflection
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