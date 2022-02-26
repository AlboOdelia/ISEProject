package scene;

import java.util.LinkedList;
import java.util.List;

import elements.AmbientLight;
import elements.LightSource;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.Color;

/**
 * Scene class if a class representing a Scene in a 3D space
 * each Scene is represented by:   - name of scene
 *                                  -background color for scene
 *                                  -ambient-light for scene
 *                                  -geometries that are in the scene
 *                                  -lights that are in and effect the scene
 * @author Odelia Albo 214089047
 */
public class Scene {

    //name of scene
    private final String name;
    //ambient-light for scene
    public AmbientLight ambientlight= new AmbientLight();
    //background color for scene
    public Color background=Color.BLACK;
    //geometries that are in the scene
    public Geometries geometries = null;
    //lights that are in and effect the scene
    public List<LightSource> lights=new LinkedList<LightSource>();

    //Constructor
    public Scene(String nameS) {
        name = nameS;
        geometries = new Geometries();
    }

    //chaining methods
    public Scene setBackground(Color backgroundS) {
        this.background = backgroundS;
        return  this;
    }

    public Scene setGeometries(Geometries geometriesS) {
        this.geometries = geometriesS;
        return  this;
    }

    public Scene setAmbientLight(AmbientLight ambientLightS) {
        this.ambientlight = ambientLightS;
        return this;
    }

    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

    /**
     * @param geometry - the new geometry to add to the scene
     * @return the scene object
     */
    public Scene addGeometry(Intersectable geometry) {
        if(geometry != null)
            this.geometries.add(geometry);
        return this;
    }

}