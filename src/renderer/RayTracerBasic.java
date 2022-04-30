package renderer;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import java.util.*;
import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import primitives.Vector;
import scene.*;

/**
 * the RayTracerBasic implements the class RayTracerBase and implements the traceRay method.
 * @author Odelia Albo 214089047
 */
public class RayTracerBasic extends RayTracerBase {

    private static final double INITIAL_K = 1.0;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;


    /**
     * constructor used to initialize the scene filed
     * using RayTracerBase constructor
     * @param scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }


    /**
     * implement abstract func. the func gets a ray, we search the objects the ray hits
     * and trough that we can calculate the value of the color
     * it considers the different factors (ambient light, emmision light, light sources exet.)
     * @param ray -  the traced ray
     * @return the discovered color
     */
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
    }

    /**
     * calculating the color using The Phong Reflectance Mode
     * private method - main method of color calculation - used by trace ray
     * @param intersection- the geometric point
     * @param ray- the ray that we are working on
     * @return calculating the color- final color of point with ambient light
     */
    private Color calcColor(GeoPoint intersection, Ray ray) {
        return calcColor(intersection, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientlight.getIntensity());
    }

    /**
     * calculating the color using The Phong Reflectance Mode
     * private method - used by main calcColor
     * ambient + diffuse + specular = phong reflection
     * @param intersection- the geometric point
     * @param ray- the ray that we are working on
     * @return calculating the color- final color of point with ambient light
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {
        Color color = intersection.geometry.getEmission().add(calcLocalEffects(intersection, ray, k));
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
    }

    /**
     * calculating the diffuse + specular in The Phong Reflectance Mode
     * for each light calculate and add :
     * if it not a shadow spot: lightIntensity plus calcDiffusive and calcSpecular
     * @param geopoint - the geometric point
     * @param ray- the ray that we are working on
     * @return calculating the color- final color of point with ambient light
     */
    private Color calcLocalEffects(GeoPoint geopoint, Ray ray,double k) {
        Vector v = ray.getDir ();
        Vector n = geopoint.geometry.getNormal(geopoint.point); //normal to point
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) //vectors orthogonal - no effect
            return Color.BLACK;
        Material material = geopoint.geometry.getMaterial();
        int nShininess = material.nShininess;
        Color color = Color.BLACK; //the end color
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(geopoint.point);//vector from light source to point
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv), if light affects the point and the camera sees it
                double ktr = transparency(lightSource,l,n,geopoint);
                if (ktr*k >MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(geopoint.point).scale(ktr); //get the lightIntensity
                    color = color.add(calcDiffusive(material.kD, l, n, lightIntensity), //adds the diffuse relative to the light for the end color
                            calcSpecular(material.kS, l, n, v, nShininess, lightIntensity)); //adds the specular relative to the light for the end color
                }
            }
        }
        return color;
    }


    /***
     * calculation of global effects on the color- reflaction and refraction
     * main func
     * @param gp -  The point for which the color is calculated
     * @param ray - the ray from the camera to the point
     * @param level - current level of depth in recursion
     * @param k - the intensity of impact of secondary rays
     * @return color - the color calculated for the point according to the reflection and refraction effects
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, double k) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        double kkr = k * material.kR; //in each recursive iteration the impact of the reflection decreases
        if (kkr > MIN_CALC_COLOR_K)
            color = calcGlobalEffect(constructReflectedRay(gp, ray, n), level, material.kR, kkr);
        double kkt = k * material.kT;
        if (kkt > MIN_CALC_COLOR_K)
            color = color.add(calcGlobalEffect(constructRefractedRay(gp, ray, n), level, material.kT, kkt));
        return color;
    }

    /***
     * calculates the color by calling calcColor with level-1
     * called by calcGlobalEffects main func
     * @param ray - the ray that we want to calculate the color
     * @param level - the levele of recursive calculatin
     * @param kx - material reflection transparency 0-1
     * @param kkx - material recursive impact on original color 0-1
     * @return
     */
    private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx) {
        GeoPoint gp = findClosestIntersection (ray);
        return (gp == null) ? scene.background : calcColor(gp, ray, level - 1, kkx).scale(kx);
    }

    /**
     * func determining how much there is shadow in a specific point based on the question of
     * if there's a geometry between point and light source
     * @param light-light source
     * @param l- vector from light source to point
     * @param n- normal to geometry point
     * @param geopoint- the geometric point
     * @return
     */
    private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source

        // add const delta to start of ray to make it closer to light source to prevent unwanted shadows
        Ray lightRay = new Ray(geopoint.point, lightDirection, n); // refactored ray head move

        //check if there's a geometry between point and light source
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) return 1.0;

        double ktr = 1.0;
        double lightDistance = light.getDistance(geopoint.point);
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geopoint.point) - lightDistance) <= 0) {
                ktr *= gp.geometry.getMaterial().kT;
                if (ktr < MIN_CALC_COLOR_K)
                    return 0.0;
            }
        }
        return alignZero(ktr);
    }




    /**
     * Calculation of specular light component using the following func:
     * ks*(-dotProduct(-v,r)^nShininess)
     * r = reflectance vector
     * @param kS - Attenuation coefficient for specular light component
     * @param l - direction vector from light to point
     * @param n - normal to point
     * @param v - direction of ray shoted to point
     * @param nShininess -  Light is exponentially reduced (at the order of nShininess - the object’s shininess )
     * @param lightIntensity - the color and intensity of light source
     * @return Color - the calculated color of specular light component
     */
    private Color calcSpecular(double kS, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = calcReflectanceVector(l, n);
        return lightIntensity.scale(kS* Math.pow((v.scale(-1)).dotProduct(r), nShininess));
    }

    /**
     * Calculating reflectance vector:
     * r= l-2(l*n)*n
     * @param l- direction vector from light to point
     * @param n- normal to point
     * @return
     */
    private Vector calcReflectanceVector(Vector l, Vector n) {
        return l.subtract(n.scale(2*l.dotProduct(n))).normalized();
    }

    /**
     * calculate the refracted ray from the intersection point
     * the ray dir is same as the ray that entered
     * @param normalToPoint - normal to the intersected object
     * @param geopoint - the specific point of which the color is calculated
     * @param ray - the ray from the camera to the point
     * @return refracted ray
     */
    private Ray constructRefractedRay( GeoPoint geopoint, Ray ray, Vector normalToPoint) {
        return new Ray(geopoint.point, ray.getDir().normalized(), normalToPoint);
    }

    /**
     * calculate the reflected ray from the intersection point
     * ray dir is same as in specular
     * @param normalToPoint - normal to the intersected object
     * @param geopoint - the specific point of which the color is calculated
     * @param ray - the ray from the camera to the point
     * @return reflected ray
     */
    private Ray constructReflectedRay( GeoPoint geopoint, Ray ray, Vector normalToPoint) {
        Vector v = ray.getDir();
        if(isZero(v.dotProduct(normalToPoint))) //so that we won't use scale with scalar as 0 because return exception
           return new Ray(geopoint.point, v);
        Vector vector = calcReflectanceVector(v, normalToPoint);
        Ray reflectedRay = new Ray(geopoint.point, vector, normalToPoint); //use the constructor with the normal
        // for moving the head a little with delta
        return reflectedRay;
    }

    /**
     * Calculation of diffusion light component using the following func:
     * kD * abs(dotProduct(l, n))
     * @param kd - Attenuation coefficient for diffusion light component
     * @param l - direction vector from light to point
     * @param n - normal to point
     * @param lightIntensity - the color and intensity of light source
     * @return Color - the calculated color of diffusion light component
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        return lightIntensity.scale(kd*Math.abs(l.dotProduct(n)));
    }

    /**
     *
     * @param ray
     * @return the closest intersection point to the refracted ray
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));

    }

//    /*
//     * trace ray returns the color of the closes object the ray intersects with
//     * affected by light and more
//     */
//    public Color traceRay(Ray ray) {
//        GeoPoint closestPoint = findClosestIntersection(ray);
//        return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
//
//    }
//    private Color calcLocalEffects(GeoPoint intersection, Ray ray, double k) {
//        Vector v = ray.getDir();
//        Vector n = intersection.geometry.getNormal(intersection.point);
//        double nv = Util.alignZero(n.dotProduct(v));
//        if (nv == 0)
//            return Color.BLACK;
//        Material material = intersection.geometry.getMaterial();
//        int nShininess = material.nShininess;
//        double kd = material.kD;
//        double ks = material.kS;
//        Color color = Color.BLACK;
//
//        for (LightSource lightSource : scene.lights) {
//            Vector l = lightSource.getL(intersection.point);
//            double nl = Util.alignZero(n.dotProduct(l));
//            if (nl * nv > 0) { // sign(nl) == sing(nv)
////				if (unshaded(lightSource, l, intersection.geometry.getNormal(intersection.point), intersection)) {
//                double ktr = transparency(lightSource, l, intersection.geometry.getNormal(intersection.point),intersection);
//                if (ktr*k > MIN_CALC_COLOR_K) {
//                    Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
//                    color = color.add(calcDiffusive(kd, l, n, lightIntensity, dotProduct(n, l)),
//                            calcSpecular(ks, l, n, v, nShininess, lightIntensity, dotProduct(n, l)));
//                }
//            }
//        }
//        return color;
//
//    }
//
//
//
//    /*
//     * ks*(-v*r)^nsn il r== l-2(l*n)*n
//     *
//     * @param ks
//     * @param l
//     * @param n
//     * @param v
//     * @param nShininess
//     * @param lightIntensity
//     * @return
//     */
//    public Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity,double dot) {
//        Vector r = l.subtract(n.scale(2 * (dot)));
//        r = r.normalized();
//        Vector vv = v.normalized().scale(-1);
//        double temp = Math.pow(Math.max(0, vv.dotProduct(r)), nShininess);
//        return lightIntensity.scale(temp).scale(ks);
//    }
//
//
//    /*
//     * Il(kd*|l*n|+ks)
//     *
//     * @param kd
//     * @param l
//     * @param n
//     * @param lightIntensity
//     * @return
//     */
//
//    protected Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity, double dot) {
//        return lightIntensity.scale(Math.abs(dot)*kd);
//        //	return lightIntensity.scale((Math.abs(l.normalize().dotProduct(n.normalize()))) * (kd));
//    }
//
//
//    /*
//     *
//     * @param l-
//     * @param n
//     * @param gp
//     * @return
//     */
//    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint gp) {
//        Vector lightDirection = l.scale(-1); // from point to light source
//
//        Ray lightRay = new Ray(gp.point, lightDirection.normalized(), n); // refactored ray head move
//        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
//        if (intersections == null)
//            return true;
//        double lightDistance = light.getDistance(gp.point);
//        for (GeoPoint gpp : intersections) {
//            if (Util.alignZero(gpp.point.distance(gp.point) - lightDistance) <= 0 && gpp.geometry.getMaterial().kT == 0)
//                return false;
//        }
//        return true;
//
//    }
//
//
//    /*
//     *
//     * @param gp- the geometric point
//     * @param ray- the ray that we are working on
//     * @return calculating the color
//     */
//    private Color calcColor(GeoPoint gp, Ray ray) {
//        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientlight.getIntensity());
//    }
//
//
//
//    /**
//     * a help function to the main calcColor- from the file
//     */
//    private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {
//        Color color = intersection.geometry.getEmission();
//        color = color.add(calcLocalEffects(intersection, ray,k));
//        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
//    }
//
//
//    /**
//     * calculate global effects and add them to the picture
//     *
//     * @param geopoint
//     * @param ray
//     * @param level
//     * @param k
//     * @return
//     */
//    private Color calcGlobalEffects(GeoPoint geopoint, Ray ray, int level, double k) {
//        Color color = Color.BLACK;
//        Material material = geopoint.geometry.getMaterial();
//        double kr = material.kR;
//        double kkr = k * kr;
//        Vector n = geopoint.geometry.getNormal(geopoint.point);
//        Vector inRay = ray.getDir();
//        if (kkr > MIN_CALC_COLOR_K) {
//            Ray reflectedRay = constructReflectedRay(n, geopoint.point, inRay);
//            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
//            if (reflectedPoint != null)
//                color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
//        }
//        double kt = material.kT;
//        double kkt = k * kt;
//        if (kkt > MIN_CALC_COLOR_K) {
//            Ray refractedRay = constructRefractedRay(n, geopoint.point, inRay);
//            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
//            if (refractedPoint != null)
//                color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
//        }
//        return color;
//    }
//
//
//    /***
//     * method that returns the reflaced ray
//     * @param point
//     * @param v- direction of ray
//     * @param n- normal to surpas
//     * @return r=v-2(v*n)*n
//     */
//    private Ray constructReflectedRay(Vector n, Point3D point, Vector v) {
//        Vector rVector = v.subtract(n.scale(v.dotProduct(n) * 2));
//        return new Ray(point, rVector.normalized(), n);
//    }
//
//    /***
//     *
//     * @param point
//     * @param v
//     * @param n
//     * @return
//     */
//    private Ray constructRefractedRay(Vector n, Point3D point, Vector v) {
//        return new Ray(point, v.normalized(), n);
//    }
//
//    /***
//     * refactoring- help func that returns the closes point
//     * @param r
//     * @return
//     */
//    public GeoPoint findClosestIntersection(Ray r)
//    {
//        List<GeoPoint> listOdPoinsGeoPoints=scene.geometries.findGeoIntersections(r);
//        if(listOdPoinsGeoPoints==null||listOdPoinsGeoPoints.size()==0)
//            return null;
//        GeoPoint point=r.findClosestGeoPoint(listOdPoinsGeoPoints);
//        return point;
//    }
//    public double dotProduct(Vector n, Vector l) { // refactoring
//        return l.normalized().dotProduct(n.normalized());
//    }
//
//    /**
//     * the function checks if there is a shadows on a point on the shape
//     * @param light -
//     * @param l-  vector from light source to the point
//     * @param n-  normal to geoPoint
//     * @param geopoint- the geoPoint
//     * @return if there is a shadows on a point on the shape
//     */
//    private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
//        Vector lightDirection = l.scale(-1); // from point to light source
//        Ray lightRay = new Ray(geopoint.point, lightDirection, n);
//        double lightDistance = light.getDistance(geopoint.point);
//        var intersections = scene.geometries.findGeoIntersections(lightRay);
//        if (intersections == null) return 1.0;
//        double ktr = 1.0;
//        for (GeoPoint gp : intersections) {
//            if (Util.alignZero(gp.point.distance(geopoint.point) - lightDistance) <= 0) {
//                ktr *= gp.geometry.getMaterial().kT;
//                if (ktr < MIN_CALC_COLOR_K) return 0.0;
//            }
//        }
//        return ktr;
//    }
}




