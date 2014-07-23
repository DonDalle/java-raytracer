package com.daleszynski.raytracer.java.raytracer;

import com.daleszynski.raytracer.java.camera.Camera;
import com.daleszynski.raytracer.java.image.Color;
import com.daleszynski.raytracer.java.image.Edgedetector;
import com.daleszynski.raytracer.java.raytracer.render_strategy.RenderStrategy;
import com.daleszynski.raytracer.java.raytracer.render_strategy.thread_pool.ThreadPoolRenderStrategy;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

/**
 * der com.daleszynski.raytracer.java.raytracer.Raytracer. nimmt eine Welt entgegen und rendert diese
 */
public class Raytracer {

    public final World world;
    private boolean isNormalImage;
    /**
     * Wenn true, werden Hilfslinien zum Positionieren gesetzt, (z.B. zum testen von der Transformation
     */
    //private final boolean helpingLines = false;

    public Raytracer(final World world) {
        if (world == null) {
            throw new IllegalArgumentException("world must not be null");
        }
        this.world = world;
    }


    /**
     * rendert das Bild
     * @param camera Kamera
     * @param width breite
     * @param height höhe
     * @return das Bild
     */
    public BufferedImage render(Camera camera, final int width, final int height) {
        if (camera == null) {
            throw new IllegalArgumentException("com.daleszynski.raytracer.java.camera must not be null");
        }
        if(width < 0 ) {
            throw new IllegalArgumentException("width must not be negative");
        }
        if(height < 0 ) {
            throw new IllegalArgumentException("height must not be negative");
        }


        double start = System.currentTimeMillis();

        final RenderStrategy strategy = new ThreadPoolRenderStrategy();
        BufferedImage image = strategy.render(width, height, camera, world, this.isNormalImage);

        double time = System.currentTimeMillis() - start;
        System.out.println("Finished in " + time / 1000 + " Seconds");

        return image;


    }


    /**
     * Rendert die Szene als Normalenbild
     */
    public BufferedImage renderNormalImage(Camera camera, final int width, final int height) {
        if (camera == null) {
            throw new IllegalArgumentException("com.daleszynski.raytracer.java.camera must not be null");
        }
        if(width < 0 ) {
            throw new IllegalArgumentException("width must not be negative");
        }
        if(height < 0 ) {
            throw new IllegalArgumentException("height must not be negative");
        }

        this.isNormalImage = true;
        final BufferedImage normalImage = this.render(camera, width, height);
        this.isNormalImage = false;
        return normalImage;
    }

    /**
     * Rendert die Szene als Kantenbild
     */
    public BufferedImage renderEdgeImage(Camera camera, final int width, final int height) {
        if (camera == null) {
            throw new IllegalArgumentException("com.daleszynski.raytracer.java.camera must not be null");
        }
        if(width < 0 ) {
            throw new IllegalArgumentException("width must not be negative");
        }
        if(height < 0 ) {
            throw new IllegalArgumentException("height must not be negative");
        }

        final Edgedetector edgedetector = new Edgedetector();
        return edgedetector.render(this.renderNormalImage(camera, width, height));
    }

    /**
     * Rendert das Bild mit Celshading
     */
    @SuppressWarnings("unused")
    public BufferedImage renderCelShadedImage(Camera camera, final int width, final int height) {
        BufferedImage edgeImage = renderEdgeImage(camera, width, height);
        BufferedImage image = render(camera, width, height);
        final WritableRaster raster = image.getRaster();
        final ColorModel model = image.getColorModel();
        final Color edgeColor = new Color(0,0,0);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                final Color color = new Color(edgeImage.getRGB(x, y));
                if(color.equals(edgeColor)) {
                    raster.setDataElements(x, y, model.getDataElements(color.toRGB(), null));
                }
            }
        }
        return image;
    }




    @Override
    public String toString() {
        return "com.daleszynski.raytracer.java.raytracer.Raytracer{" +
                "world=" + world +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Raytracer)) return false;

        Raytracer raytracer = (Raytracer) o;

        return world.equals(raytracer.world);

    }

    @Override
    public int hashCode() {
        return world.hashCode();
    }
}


