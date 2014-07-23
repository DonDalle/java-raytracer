package com.daleszynski.raytracer.java.raytracer.render_strategy.thread_pool;

import com.daleszynski.raytracer.java.camera.Camera;
import com.daleszynski.raytracer.java.geometry.Hit;
import com.daleszynski.raytracer.java.image.Color;
import com.daleszynski.raytracer.java.image.DisplayThread;
import com.daleszynski.raytracer.java.math.Ray;
import com.daleszynski.raytracer.java.raytracer.Tracer;
import com.daleszynski.raytracer.java.raytracer.World;

import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

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

    @Override
    public void run() {
        final Tracer tracer = new Tracer();
        for (int x =0; x<width;  x++) {
            for (int y = startHeight; y < maxHeight; y++) {
                final Ray ray = camera.rayFor(width, height, x, y);
                final Hit hit = world.hit(ray);
                final int color;
                if (hit == null) {
                    color = world.backgroundColor.toRGB();
                } else if(this.isNormalImage) {
                    final double r = Math.abs(hit.n.x);
                    final double g = Math.abs(hit.n.y);
                    final double b = Math.abs(hit.n.z);

                    color = new Color(r,g,b).toRGB();
                } else {
                    color = hit.geo.m.colorFor(hit, world, tracer).toRGB();
                }
                raster.setDataElements(x, height - 1 - y, model.getDataElements(color, null));
                DisplayThread.finishedPixels++;
            }
        }
    }
}

