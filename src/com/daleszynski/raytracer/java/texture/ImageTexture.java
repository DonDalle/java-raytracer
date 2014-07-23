package com.daleszynski.raytracer.java.texture;

import com.daleszynski.raytracer.java.image.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Lädt ein Bild als Textur
 */
public class ImageTexture implements Texture {

    /**
     * Das Bild für die Textur
     */
    final BufferedImage image;

    /**
     * Erstellt die Textur und nimmt ein Pfad zu einem Bild
     * @param pathToImage der Pfad
     * @throws IOException falls es zum Fehlern beim Laden es Bildes kommt.
     */
    public ImageTexture(final String pathToImage) throws IOException {
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
        final int rgb = image.getRGB((int) (x), (int) (y));
        return new Color(rgb);
    }
}
