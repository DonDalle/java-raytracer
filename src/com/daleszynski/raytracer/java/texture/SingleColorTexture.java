package com.daleszynski.raytracer.java.texture;

import com.daleszynski.raytracer.java.image.Color;

/**
 * Stellt eine einfarbige Textur dar
 */
public class SingleColorTexture implements Texture {

    public final Color color;

    public SingleColorTexture(final Color color) {
        if (color == null) {
            throw new IllegalArgumentException("com.daleszynski.raytracer.java.texture must not be null");
        }

        this.color = color;
    }

    @Override
    public Color getColor(double u, double v) {
        return color;
    }
}
