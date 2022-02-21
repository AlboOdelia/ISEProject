package geometries;
import primitives.*;
/**
 * interface for all the geometries that have a normal from them
 */
public abstract class Geometry implements Intersectable {

    protected Color emission=Color.BLACK;
    protected Material material=new Material();

    /**
     *
     * @param point should be null for flat geometries
     * @return the normal to the geometry
     */
    public abstract Vector getNormal(Point3D wPoint3d);

    /**
     * returns the emmission color of the geomtry
     * @return
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * returns the material of the geomtry
     * @return
     */
    public Material getMaterial() {
        return material;
    }


    /**
     *
     * @param emmission- the color we want the Geometry to be
     * @return the Geometry itself
     */
    public Geometry setEmission(Color emmission) {
        this.emission = emmission;
        return this;
    }

    /**
     *
     * @param material- the material we want the Geometry to be
     * @return the Geometry itself
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

}
