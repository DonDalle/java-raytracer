package com.daleszynski.raytracer.java.raytracer.render_strategy.thread_pool;

import com.daleszynski.raytracer.java.camera.Camera;
import com.daleszynski.raytracer.java.image.Color;
import com.daleszynski.raytracer.java.image.DisplayThread;
import com.daleszynski.raytracer.java.math.Ray;
import com.daleszynski.raytracer.java.raytracer.Tracer;
import com.daleszynski.raytracer.java.raytracer.World;

import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.List;

/**
 * Der eigentliche Raytracing algorithmus.
 * Sollte in der Regel immer in einem Thread ausgefhrt werden
 * Gerendert wird immer in einem Pixelbereich: angefangen bei startHeight bis zu maxHeight
 */
public class ThreadPoolRunnable implements Runnable {

    /**
     * Das Raster
     */
    private WritableRaster raster;

    /**
     * Das Farbmodell
     */
    private final ColorModel model;

    /**
     * Die Kamera
     */
    private final Camera camera;

    /**
     *  Gesamtbreite des Bildes
     */
    private final int width;

    /**
     * Gesamthöhe des Bildes
     */
    private final int height;

    /**
     * start Höhe
     */
    private final int startHeight;

    /**
     * Maximale Hähe
     */
    private final int maxHeight;

    /**
     * True, wenn die Normalen gerendert werden sollen
     */
    private final boolean isNormalImage;

    /**
     * Die zu rendernde Welt
     */
    private final World world;


    /**
     * Erstellt einen neuen ausführbaren Raytracer und weist die render Bereiche zu
     */
    public ThreadPoolRunnable(int startHeight, int maxHeight, int height, int width, boolean isNormalImage, World world, Camera camera, WritableRaster raster, ColorModel model) {
        this.startHeight = startHeight;
        this.maxHeight = maxHeight;
        this.height = height;
        this.width = width;
        this.model = model;
        this.raster = raster;
        this.camera = camera;
        this.world = world;
        this.isNormalImage = isNormalImage;
    }
    //TODO normalmapping
    @Override
    public void run() {
        if(isNormalImage) {
            //throw new UnsupportedOperationException("normalmapping not yet implemented");
        }
        final Tracer tracer = new Tracer();
        for (int x =0; x<width;  x++) {
            for (int y = startHeight; y < maxHeight; y++) {
                final List<Ray> rays = camera.raysFor(width, height, x, y);
                Color sum = new Color(0,0,0);
                for (final Ray ray: rays) {
                    sum = sum.add(tracer.colorFor(ray, world));
                }
                final int rgb = sum.div(rays.size()).toRGB();
                raster.setDataElements(x, height - 1 - y, model.getDataElements(rgb, null));
                DisplayThread.finishedPixels++;
            }
        }
    }
}

