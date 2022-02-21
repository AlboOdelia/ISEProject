package geometries;
import primitives.*;

/**
 * interface for all the geometries that have a normal from them
 * @author Albo odelia
 * updated
 */
public abstract class Geometry implements Intersectable {

    protected Color emission=Color.BLACK;
    protected Material material=new Material();

    /**
     * abstract method getNormal for extending classes
     * @param wPoint3d should be null for flat geometries
     * @return the normal to the geometry
     */
    public abstract Vector getNormal(Point3D wPoint3d);

    /**
     * returns the emission color of the geometry
     * @return the color of the geometry
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * returns the material of the geometry
     * @return the material of the geometry
     */
    public Material getMaterial() {
        return material;
    }


    /**
     * Builder pattern setter to Emission param.
     * @param emission- the color we want the Geometry to be
     * @return the Geometry itself
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * Builder pattern setter to material param.
     * @param material- the material we want the Geometry to be
     * @return the Geometry itself
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

}
