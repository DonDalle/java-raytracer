package com.daleszynski.raytracer.java.raytracer.render_strategy;

import com.daleszynski.raytracer.java.camera.Camera;
import com.daleszynski.raytracer.java.raytracer.World;

import java.awt.image.BufferedImage;

/**
 * Implementierung des Strategy patterns. Ermöglicht das einfache austauschen von verschiedenen Strategien zum rendern.
 * Wie zum Beispiel mit oder ohne Multithreading
 */
public interface RenderStrategy {

    public BufferedImage render(final int width, final int height, final Camera camera, final World world, boolean isNormalImage);
}
