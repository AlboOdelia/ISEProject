package elements;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

public class Camera {
    //center point of camera
    final private Point3D p0;

    //directional rays:
    // Z-axis of camera's 3D axis system: scene depth
    final private Vector vTo;
    // Y-axis of camera's 3D axis system: scene bottom to top
    final private Vector vUp;
    // X-axis of camera's 3D axis system: scene left to righ
    final private Vector vRight;

    private double distance;
    private double width;
    private double height;

    private Camera(BuilderCamera builder) {
        this.p0 = builder.p0;
        this.vTo = builder.vTo;
        this.vUp = builder.vUp;
        this.vRight = builder.vRight;
        this.height = builder.height;
        this.width = builder.width;
        this.distance = builder.distance;
    }

    public Point3D getP0() {
        return p0;
    }

    public Vector getvTo() {
        return vTo;
    }

    public Vector getvUp() {
        return vUp;
    }

    public Vector getvRight() {
        return vRight;
    }

    public double getDistance() {
        return distance;
    }

    //Camera setter chaining methods
    public Camera setDistance(double distance) {
        this.distance = distance;
        return this;
    }

    public Camera setViewPlaneSize(double width, double height) {
        if(width <= 0 || height <= 0)
            throw new IllegalArgumentException("width and height of the view plane must be positive");
        this.width = width;
        this.height = height;
        return this;
    }

    //Camera getters methods
    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    // constructing a ray passing through pixel(i,j) of the view plane
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        Point3D Pc = this.p0.add(this.vTo.scale(this.distance));

        double Rx = this.width / nX;
        double Ry = this.height / nY;

        Point3D Pij = Pc;

        double Xj = (j - (nX - 1) / 2d) * Rx;
        double Yi = -(i - (nY - 1) / 2d) * Ry;

        if (isZero(Xj) && isZero(Yi)) {
            return new Ray(this.p0, Pij.subtract(this.p0));
        }
        if (isZero(Xj)) {
            Pij = Pij.add(this.vUp.scale(Yi));
            return new Ray(this.p0, Pij.subtract(this.p0));
        }
        if (isZero(Yi)) {
            Pij = Pij.add(this.vRight.scale(Xj));
            return new Ray(this.p0, Pij.subtract(this.p0));
        }

        Pij = Pij.add(this.vRight.scale(Xj).add(this.vUp.scale(Yi)));
        return new Ray(this.p0, Pij.subtract(this.p0));

    }

    /**
     *
     * @param up    delta for this.vUp vector
     * @param right delta for this.vRight vector
     * @param to    delta for this.vTo vector
     * @return
     */
    public Camera moveCamera(double up, double right, double to) {
        if (up == 0 && right == 0 && to == 0) return this;
        if (up != 0) this.p0.add(this.vUp.scale(up));
        if (right != 0) this.p0.add(this.vRight.scale(right));
        if (to != 0) this.p0.add(this.vTo.scale(to));
        return this;
    }


//    /**
//     *
//     * @param axis  turning axis
//     * @param theta angle to turn the camera
//     * @return
//     */
//
//    public Camera turnCamera(Vector axis, double theta) {
//        if (theta == 0) return this;
//        this.vUp.rotateVector(axis, theta);
//        this.vRight.rotateVector(axis, theta);
//        this.vTo.rotateVector(axis, theta);
//        return this;
//    }

    /**
     * Builder Class for Camera
     */
    public static class BuilderCamera {
        final private Point3D p0;
        final private Vector vTo;
        final private Vector vUp;
        final private Vector vRight;

        private double distance = 10;
        private double width = 1;
        private double height = 1;

        public BuilderCamera setDistance(double distance) {
            this.distance = distance;
            return this;
        }


        public BuilderCamera setViewPlaneWidth(double width) {
            this.width = width;
            return this;
        }

        public BuilderCamera setViewPlaneHeight(double height) {
            this.height = height;
            return this;
        }

        public Camera build() {
            Camera camera = new Camera(this);
            return camera;
        }

        public BuilderCamera(Point3D p0, Vector vTo, Vector vUp) {
            this.p0 = p0;

            if (!isZero(vTo.dotProduct(vUp))) {
                throw new IllegalArgumentException("vto and vup are not orthogonal");
            }

            this.vTo = vTo.normalized();
            this.vUp = vUp.normalized();

            this.vRight = this.vTo.crossProduct(this.vUp);

        }
    }
}
