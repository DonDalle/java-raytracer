package com.daleszynski.raytracer.java.texture;

import com.daleszynski.raytracer.java.image.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.Math.ceil;
import static java.lang.Math.floor;

/**
 * Eine Textur mit einem interpoliertem Bild
 */
public class InterpolatedImageTexture implements Texture {
    /**
     * Das Bild für die Textur
     */
    final BufferedImage image;

    /**
     * Erstellt die Textur und nimmt ein Pfad zu einem Bild
     *
     * @param pathToImage der Pfad
     * @throws java.io.IOException falls es zum Fehlern beim Laden es Bildes kommt.
     */
    public InterpolatedImageTexture(final String pathToImage) throws IOException {
        if (pathToImage == null) {
            throw new IllegalArgumentException("pathToImage must not be null");
        }
        this.image = ImageIO.read(new File(pathToImage));
    }

    @Override
    public Color getColor(double u, double v) {
        u = u % 1.0;
        v = v % 1.0;
        if (u < 0.0) {
            u++;
        }
        if (v < 0.0) {
            v++;
        }

        final double x = u * (image.getWidth() - 1);
        final double y = (image.getHeight() - 1) - v * (image.getHeight() - 1);

        final double xa = x - floor(x);
        final double ya = y - floor(y);

        final int rgb1 = image.getRGB((int) floor(x), (int) floor(y));
        final int rgb2 = image.getRGB((int) ceil(x), (int) floor(y));
        final int rgb3 = image.getRGB((int) floor(x), (int) ceil(y));
        final int rgb4 = image.getRGB((int) ceil(x), (int) ceil(y));

        final Color color1 = new Color(rgb1);
        final Color color2 = new Color(rgb2);
        final Color color3 = new Color(rgb3);
        final Color color4 = new Color(rgb4);

        final Color result1 = color1.mul(1.0 - xa).add(color2.mul(xa));
        final Color result2 = color3.mul(1.0 - xa).add(color4.mul(xa));

        return result1.mul(1.0 - ya).add(result2.mul(ya));
    }
}
