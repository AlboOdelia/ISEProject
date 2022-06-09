package renderer;

import java.util.LinkedList;
import java.util.MissingResourceException;

import elements.Camera;
import primitives.Color;
import primitives.Ray;

public class Render {
    private ImageWriter imageWriter;
    private Camera camera;
    private RayTracerBase rayTracerBase;

    // The square root of the number of rays sent through each pixel
    private int super_sample_num = 9;
    private int MAX_LEVEL_ADAPTIVE_SS = 3;

    //0- no feature
    //1- super sampling
    //2- adaptive super samp
    private int mode = 0;

    //multi threading
    public int threadsNumber = 0;
    private final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
    private boolean print = false; // printing progress percentage


    /**
     * set Image Writer
     * @param imageWriter_ new imageWriter_
     * @return this Render
     */
    public Render setImageWriter(ImageWriter imageWriter_)
    {
        imageWriter = imageWriter_;
        return this;
    }

    /**
     * set camera
     * @param camera_ new camera
     * @return this Render
     */
    public Render setCamera(Camera camera_)
    {
        camera = camera_;
        return this;
    }

    /**
     * set ray tracer
     * @param rayTracer_ new ray tracer
     * @return this Render
     */
    public Render setRayTracer(RayTracerBase rayTracer_)
    {
        rayTracerBase = rayTracer_;
        return this;
    }

    /***
     * set super samp
     * @param s the new square root of the number of rays sent through each pixel
     * @return this render
     */
    public Render set_Super_sample_num(int s) {
        super_sample_num =s;
        return this;
    }

    /***
     * set mode
     * @param m 0- no feature
     *          1- super sampling
     *          2- adaptive super samp
     * @return this render
     */
    public Render setMode(int m) {
        if(m>=0 && m<=2)
            mode = m;
        return this;
    }

    /**
     * Set multithreading <br>
     * - if the parameter is 0 - number of coress less SPARE (2) is taken
     * @param threads number of threads
     * @return the Render object itself
     */
    public Render setMultithreading(int threads) {
        if (threads < 0) throw new IllegalArgumentException("Multithreading must be 0 or higher");
        if (threads != 0) this.threadsNumber = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            this.threadsNumber = cores <= 2 ? 1 : cores;
        }
        return this;
    }

    /**
     * Set debug printing on
     * @return the Render object itself
     */
    public Render setDebugPrint() { print = true; return this; }

    /***
     * helper func that cast rays from camera to image in order to color pixel
     * @param nX resolution on X axis (number of pixels in row)
     * @param nY resolution on Y axis (number of pixels in colum)
     * @param col index of col
     * @param row index of row
     */
    private void castRay(int nX, int nY, int col, int row) {
        Ray ray;
        Color color;
        color = new Color(0, 0, 0);
        //how to calculate color
        if (mode == 0) { //basic
            ray = camera.constructRayThroughPixel(nX, nY, row, col);
            color = rayTracerBase.traceRay(ray);
        } else if (mode == 1) //super sampling
            color = SuperSampling_MiniProject1(nX, nY, row, col);
        else if (mode == 2) { //adaptive super sampling
            color = AdaptiveSuperSampling_MiniProject2(nX, nY, col, row, MAX_LEVEL_ADAPTIVE_SS);
        }
        //write color to pixel
        imageWriter.writePixel(row,col,color);
    }

    /***
     * render the image by calculating the color of each pixel
     */
    public void renderImage()
    {
        if(imageWriter == null ||camera == null || camera.getHeight() == 0 ||
                camera.getWidth() == 0 ||rayTracerBase == null)
            throw new MissingResourceException("some of the fields are uninitialized","","");
        if(threadsNumber == 0) {//if not threaded
            int Nx = imageWriter.getNx();
            int Ny = imageWriter.getNy();
            Ray ray;
            Color color;

            for (int i = 0; i < Ny; i++)
                for (int j = 0; j < Nx; j++) {
                    //set color for the pixel
                    castRay(Nx,Ny,i,j);
                }
        }//use given func renderImageThreaded
        else renderImageThreaded();
    }

    /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object - with multi-threading
     */
    private void renderImageThreaded() {
        final int nX = imageWriter.getNx();
        final int nY = imageWriter.getNy();
        final Pixel thePixel = new Pixel(nY, nX);
        // Generate threads
        Thread[] threads = new Thread[threadsNumber];
        for (int i = threadsNumber - 1; i >= 0; --i) {
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel();
                while (thePixel.nextPixel(pixel))
                    //set color for pixel
                    castRay(nX, nY, pixel.row, pixel.col);
            });
        }
        // Start threads
        for (Thread thread : threads)
            thread.start();

        // Print percents on the console
        thePixel.print();

        // Ensure all threads have finished
        for (Thread thread : threads)
            try {
                thread.join();
            } catch (Exception e) {
            }

        if (print)
            System.out.print("\r100%\n");
    }

    /***
     * the function calculates the color of pixel using super sampling.
     * meaning it creates super_sample_num*super_sample_num rays for each pixel and returns the average
     * @param nX-View Plane X axis resolution
     * @param nY-View Plane Y axis resolution
     * @param j-index of row
     * @param i-index of col
     * @return
     */
    public Color SuperSampling_MiniProject1(int nX, int nY, int j, int i) { //23:07 minutes for 81 rays miniProject1
                                                                            //16:19 minutes for 81 rays miniProject1 3 threads
        Color averageColor=new Color(0, 0, 0);
        //calculates super_sample_num*super_sample_num
        for (int ii = 0; ii< super_sample_num; ii++) {
            for(int jj = 0; jj< super_sample_num; jj++) {
                //get the specific ray out of super_sample_num*super_sample_num rays
                Ray ray=camera.constructRayThroughPixel(nX* super_sample_num, nY* super_sample_num, j* super_sample_num +jj, i* super_sample_num +ii);
                //gets the color of the ray
                Color c = rayTracerBase.traceRay(ray);
                //adds to color
                averageColor= averageColor.add(c);
            }
        }
        //device the sum of all the colors by the number of rays cast
        averageColor=averageColor.reduce(super_sample_num * super_sample_num);//divided by num of colors to get average
        return averageColor;
    }


    /**
     * the function calculates the color of pixel using adaptive super sampling.
     * achieving adaptive super sampling by producing more than 1 ray only if needed
     * @param nX-View Plane X axis resolution
     * @param nY-View Plane Y axis resolution
     * @param i - col index of col
     * @param j-  index of row
     * @param level - the depth of the recursion
     * @return
     */
    public Color AdaptiveSuperSampling_MiniProject2(int nX, int nY, int i, int j,int level) {   //10:10 minutes for max 64 rays miniProject1(no threads)
                                                                                                //5:57 minutes for max 64 rays miniProject1(with threading)
        Color averageColor = new Color(0, 0, 0);
        var colors = new LinkedList<Color>();

        //start by creating 4 main rays, and calculate color
        for (int ii = 0; ii < 2; ii++) {
            for (int jj = 0; jj < 2; jj++) {
                Ray ray = camera.constructRayThroughPixel(nX * 2, nY * 2, j * 2 + jj, i * 2 + ii);
                colors.add(rayTracerBase.traceRay(ray));
            }
        }

        //if we reach the maximum level of recursion, return the average of the 4 rays
        if (level == 1) {
            for (Color c : colors)
                averageColor = averageColor.add(c);
            return averageColor.reduce(4);
        }

        //get color center of pixel
        Color centerColor = rayTracerBase.traceRay(camera.constructRayThroughPixel(nX, nY, j, i));
        //compare the created colors to the center
        for (int ii = 0; ii < 2; ii++) { //sum all the colors
            for (int jj = 0; jj < 2; jj++) {
                if (!(colors.get(ii * 2 + jj)).equals(centerColor)) //if colors are not equal
                    //calculate AdaptiveSuperSampling for this quarter and add to the average
                    averageColor = averageColor.add(AdaptiveSuperSampling_MiniProject2(nX * 2, nY * 2, i * 2 + ii, j * 2 + jj, level - 1));
                else //if colors are equals
                    //add to the average
                    averageColor = averageColor.add(colors.get(ii * 2 + jj));
            }
        }
        //divide by 4
        return averageColor.reduce(4);
    }


    /**
     * insert grid lines over the view plane
     * @param interval between lines
     * @param color of the grid
     */
    public void printGrid(int interval, Color color)
    {
        if(imageWriter == null)
            throw new MissingResourceException("imageWriter is uninitialized", "", "");
        int Nx = imageWriter.getNx();
        int Ny = imageWriter.getNy();
        for(int i = 0 ; i < Nx ; i++)
            for(int j = 0 ; j< Ny; j++)
                if(i % interval == 0 || j % interval == 0)
                    imageWriter.writePixel(i, j, color);
    }

    /**
     * this function create the png file of the image (if possible)
     */
    public void writeToImage()
    {
        if(imageWriter == null)
            throw new MissingResourceException("not all the fields are full", "Image writer", "is null");

        imageWriter.writeToImage();
    }


    /**
     * Pixel is an internal helper class whose objects are associated with a Render
     * object that they are generated in scope of. It is used for multithreading in
     * the Renderer and for follow up its progress.<br/>
     * There is a main follow up object and several secondary objects - one in each
     * thread.
     *
     * @author Dan
     *
     */
    private class Pixel {
        private long maxRows = 0;
        private long maxCols = 0;
        private long pixels = 0;
        public volatile int row = 0;
        public volatile int col = -1;
        private long counter = 0;
        private int percents = 0;
        private long nextCounter = 0;

        /**
         * The constructor for initializing the main follow up Pixel object
         *
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols) {
            this.maxRows = maxRows;
            this.maxCols = maxCols;
            this.pixels = (long) maxRows * maxCols;
            this.nextCounter = this.pixels / 100;
            if (Render.this.print)
                System.out.printf("\r %02d%%", this.percents);
        }

        /**
         * Default constructor for secondary Pixel objects
         */
        public Pixel() {
        }

        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object
         * - this function is critical section for all the threads, and main Pixel
         * object data is the shared data of this critical section.<br/>
         * The function provides next pixel number each call.
         *
         * @param target target secondary Pixel object to copy the row/column of the
         *               next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print,
         *         if it is -1 - the task is finished, any other value - the progress
         *         percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++col;
            ++this.counter;
            if (col < this.maxCols) {
                target.row = this.row;
                target.col = this.col;
                if (Render.this.print && this.counter == this.nextCounter) {
                    ++this.percents;
                    this.nextCounter = this.pixels * (this.percents + 1) / 100;
                    return this.percents;
                }
                return 0;
            }
            ++row;
            if (row < this.maxRows) {
                col = 0;
                target.row = this.row;
                target.col = this.col;
                if (Render.this.print && this.counter == this.nextCounter) {
                    ++this.percents;
                    this.nextCounter = this.pixels * (this.percents + 1) / 100;
                    return this.percents;
                }
                return 0;
            }
            return -1;
        }

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         *
         * @param target target secondary Pixel object to copy the row/column of the
         *               next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percent = nextP(target);
            if (Render.this.print && percent > 0)
                synchronized (this) {
                    notifyAll();
                }
            if (percent >= 0)
                return true;
            if (Render.this.print)
                synchronized (this) {
                    notifyAll();
                }
            return false;
        }

        /**
         * Debug print of progress percentage - must be run from the main thread
         */
        public void print() {
            if (Render.this.print)
                while (this.percents < 100)
                    try {
                        synchronized (this) {
                            wait();
                        }
                        System.out.printf("\r %02d%%", this.percents);
                        System.out.flush();
                    } catch (Exception e) {
                    }
        }
    }



}

