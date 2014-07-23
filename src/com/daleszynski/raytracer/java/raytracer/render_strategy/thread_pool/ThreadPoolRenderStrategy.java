package com.daleszynski.raytracer.java.raytracer.render_strategy.thread_pool;


import com.daleszynski.raytracer.java.camera.Camera;
import com.daleszynski.raytracer.java.image.Color;
import com.daleszynski.raytracer.java.image.DisplayThread;
import com.daleszynski.raytracer.java.raytracer.World;
import com.daleszynski.raytracer.java.raytracer.render_strategy.RenderStrategy;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolRenderStrategy implements RenderStrategy {
    @Override
    public BufferedImage render(int width, int height, Camera camera, World world , boolean isNormalImage) {
        final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        try {
            final WritableRaster raster = image.getRaster();
            final ColorModel model = image.getColorModel();

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    raster.setDataElements(i, height - 1 - j, model.getDataElements(new Color(Math.random(), Math.random(), Math.random()).toRGB(), null));
                }
            }

            final DisplayThread display = new DisplayThread(width, height, image);
            display.start();

            final int cores = Runtime.getRuntime().availableProcessors();
            final ExecutorService executor = Executors.newCachedThreadPool();

            final int steps = height / cores;
            double start = System.currentTimeMillis();
            for (int i = 0; i < cores; i++) {
                final ThreadPoolRunnable thread = new ThreadPoolRunnable(i * steps, i * steps + steps, height, width, isNormalImage, world, camera, raster, model);
                executor.execute(thread);
            }

            executor.shutdown();
            executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.DAYS);


            double time = System.currentTimeMillis() - start;
            System.out.println("Finished in " + time / 1000 + " Seconds");

            display.close();

            return image;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return image;

    }

}
