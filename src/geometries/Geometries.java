package geometries;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import primitives.Point3D;
import primitives.Ray;
import java.util.Collections;


import geometries.Intersectable.GeoPoint;

/**
 * Geometries is the collection of geometries as one geometry
 * @author Odelia Albo
 * updated
 */
public class Geometries implements Intersectable
{
    /**
     * the intersectables is a list of intersectable geometry
     */
    private List<Intersectable> intersectables = new LinkedList<Intersectable>();

    /**
     * constractor that adds the list of geometrys to the intersectables list
     * uses the add func
     * @param geos- list of intersecteble geometries
     */
    public Geometries(Intersectable... geos) {
        add(geos);
    }

    /**
     * adds the list of geometrys to the intersectables list
     * @param geos- list of intersecteble geometries
     */
    public void add(Intersectable... geos) {
        //Collections.addAll(intersectables, geos);-- null exeption
        for (Intersectable g: List.of(geos)) {
            if(g != null)
                intersectables.add(g);
        }
    }

    /**
     * get number of geometries in intersectables object
     * @return the number of geometries in intersectables list
     */
    public int getNumberOfGeometries() {
        return intersectables.size();
    }

    /**
     * Implementation of findGeoIntersections method from Intersectable
     * the func goes to the list of intersectables (list of geometries)
     * for each adds the intersection points that are within a certain distance range
     * @param ray - the ray of intersection
     * @param maxDistance - the maximum distance in which we search intersection points
     * @return the list of all intersections with geometries
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        List<GeoPoint> interactionsList= new LinkedList<GeoPoint>();
        for(Intersectable shape: intersectables)
        {
            List<GeoPoint> lst = shape.findGeoIntersections(ray, maxDistance);
            if(lst!=null)
                interactionsList.addAll(lst);
        }
        if(interactionsList.size()!=0)
            return interactionsList;
        return null;
    }
    // TODO: decide if is needed
    //  not sure way is implemented
//    /**
//     *
//     * @param ray
//     * @return
//     */
//    public List<Point3D> findIntsersections(Ray ray)
//    {
//        var geoList = findGeoIntersections(ray);
//        return geoList == null ? null
//                : geoList.stream().map(gp -> gp.point).collect(Collectors.toList());
//    }
}
