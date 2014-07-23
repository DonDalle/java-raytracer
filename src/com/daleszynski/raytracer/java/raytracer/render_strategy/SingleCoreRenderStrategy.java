package com.daleszynski.raytracer.java.raytracer.render_strategy;

import com.daleszynski.raytracer.java.camera.Camera;
import com.daleszynski.raytracer.java.geometry.Hit;
import com.daleszynski.raytracer.java.image.Color;
import com.daleszynski.raytracer.java.math.Ray;
import com.daleszynski.raytracer.java.raytracer.Tracer;
import com.daleszynski.raytracer.java.raytracer.World;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

@SuppressWarnings("unused")
public class SingleCoreRenderStrategy implements RenderStrategy {

    @Override
    public BufferedImage render(int width, int height, Camera camera, World world, boolean isNormalImage) {
        final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final WritableRaster raster = image.getRaster();
        final ColorModel model = image.getColorModel();
        double tempProgress = 0;
        final Tracer tracer = new Tracer();
        for (int x = 0; x < width; x++) {
            //Fortschirttsanzeige
            double progress = Math.floor(((double)x/(double)width) * 1000 )/10;
            if(progress != tempProgress) {
                System.out.println(progress +  "% Finished");
                tempProgress = progress;
            }
            for (int y = 0; y < height; y++) {
                final Ray ray = camera.rayFor(width, height, x, y);
                final Hit hit = world.hit(ray);
                int color;
                if(hit == null) {
                    color = world.backgroundColor.toRGB();
                } else if(isNormalImage) {
                    final double r = Math.abs(hit.n.x);
                    final double g = Math.abs(hit.n.y);
                    final double b = Math.abs(hit.n.z);

                    color = new Color(r,g,b).toRGB();
                }
                else {
                    color = hit.geo.m.colorFor(hit,world,tracer).toRGB();
                }

                raster.setDataElements(x, height-1-y, model.getDataElements(color, null));

            }
        }
        return image;
    }
}
