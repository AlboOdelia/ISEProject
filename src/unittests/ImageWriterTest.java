package unittests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import renderer.ImageWriter;
import elements.Camera;
import geometries.Triangle;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class ImageWriterTest {


    @Test
    public void testWriteToImage() {
        ImageWriter imageWriter = new ImageWriter("purpleRectangleSS",800,500);
        for (int i = 0; i < 800; i++) {
            for (int j = 0; j < 500; j++) {
                // 800/16 = 50
                if (i % 50 == 0) {
                    imageWriter.writePixel(i, j, new Color(255d, 242d, 229d));
                }
                // 500/10 = 50
                else if (j % 50 == 0) {
                    imageWriter.writePixel(i, j, new Color(255d, 242d, 229d));
                } else {
                    imageWriter.writePixel(i, j, new Color(100d, 10d, 100d));
                }
            }
        }
        imageWriter.writeToImage();
    }

}

