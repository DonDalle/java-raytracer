package com.daleszynski.raytracer.java.raytracer.render_strategy.producer_consumer;

import com.daleszynski.raytracer.java.camera.Camera;
import com.daleszynski.raytracer.java.image.DisplayThread;
import com.daleszynski.raytracer.java.raytracer.World;
import com.daleszynski.raytracer.java.raytracer.render_strategy.RenderStrategy;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.concurrent.*;

public class ProducerConsumerRenderStrategy implements RenderStrategy {
    @Override
    public BufferedImage render(int width, int height, Camera camera, World world, boolean isNormalImage) {



        final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final WritableRaster raster = image.getRaster();
        final ColorModel model = image.getColorModel();

        DisplayThread displayThread = new DisplayThread(width, height, image);
        displayThread.start();

        //public RenderConsumer (final BlockingQueue<Rectangle> queue, int width, int height,  World world, Camera com.daleszynski.raytracer.java.camera, WritableRaster raster, ColorModel model, boolean isNormalImage) {

        BlockingQueue<Rectangle> q = new LinkedBlockingDeque<>();
        RenderProducer p = new RenderProducer(q, width, height);
        RenderConsumer consumer = new RenderConsumer(q, width, height, world, camera, raster, model, isNormalImage);



        final int cores = Runtime.getRuntime().availableProcessors();
        final ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(p);
        for (int i = 0; i < cores; i++) {
            executor.execute(consumer);
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        return image;
    }
}
