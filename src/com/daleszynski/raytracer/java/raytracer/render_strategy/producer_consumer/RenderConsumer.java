package com.daleszynski.raytracer.java.raytracer.render_strategy.producer_consumer;

import com.daleszynski.raytracer.java.camera.Camera;
import com.daleszynski.raytracer.java.geometry.Hit;
import com.daleszynski.raytracer.java.image.DisplayThread;
import com.daleszynski.raytracer.java.math.Ray;
import com.daleszynski.raytracer.java.raytracer.Tracer;
import com.daleszynski.raytracer.java.raytracer.World;

import java.awt.*;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.concurrent.BlockingQueue;

//TODO JavaDoc
public class RenderConsumer implements Runnable{

    private final BlockingQueue<Rectangle> queue;
    private final World world;
    private final Camera camera;
    private final WritableRaster raster;
    private final ColorModel model;
    private final boolean isNormalImage;
    private final int height;
    private final int width;


    public RenderConsumer (final BlockingQueue<Rectangle> queue, int width, int height,  World world, Camera camera, WritableRaster raster, ColorModel model, boolean isNormalImage) {
        if (queue == null) {
            throw new IllegalArgumentException("queue must not be null");
        }

        this.height = height;
        this.width = width;
        this.queue = queue;
        this.world = world;
        this.camera = camera;
        this.raster = raster;
        this.model = model;
        this.isNormalImage = isNormalImage;

    }
    @Override
    public void run() {
        try {
            while (true) {
                render(queue.take());
                if(queue.isEmpty()) {
                    break;
                }
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void render(Rectangle rect) {
        final Tracer tracer = new Tracer();
        for (int x =rect.x; x<rect.width;  x++) {
            for (int y = rect.y; y < rect.height; y++) {
                final Ray ray = camera.rayFor(width, height, x, y);
                final Hit hit = world.hit(ray);
                final int color;
                if (hit == null) {
                    color = world.backgroundColor.toRGB();
                } else if(this.isNormalImage) {
                    final double r = Math.abs(hit.n.x);
                    final double g = Math.abs(hit.n.y);
                    final double b = Math.abs(hit.n.z);

                    color = new com.daleszynski.raytracer.java.image.Color(r,g,b).toRGB();
                } else {
                    color = hit.geo.m.colorFor(hit, world, tracer).toRGB();
                }
                raster.setDataElements(x, height - 1 - y, model.getDataElements(color, null));
                DisplayThread.finishedPixels++;
            }
        }
    }
}
