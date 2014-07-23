package com.daleszynski.raytracer.java.image;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;


/**
 * EIn Kantendetektor
 */
public class Edgedetector {

    /**
     * Schwellenwert für die Kanten
     */
    @SuppressWarnings("FieldCanBeLocal")
    private final double LEVEL = 0.7;

    /**
     * Erstellt ein Kantenbild aus einem Normalenbild
     * @param input das Normalenbild
     * @return das Kantenbild
     */
    public BufferedImage render(final BufferedImage input) {
        final BufferedImage result = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_RGB);
        final WritableRaster write = result.getRaster();
        final ColorModel model = result.getColorModel();


        for (int x = 1; x < input.getWidth() - 2; x++) {
            for (int y = 1; y < input.getHeight() - 2; y++) {
                final Color c = new Color(input.getRGB(x, y));
                final double r = c.r;
                final double g = c.g;
                final double b = c.b;

                double res = 1;

                for (int i = x-1; i < x+1 ; i++) {
                    for (int j = y-1; j < y+1; j++) {
                        if(i==x && j==y) {
                            continue;
                        }
                        res -= (Math.abs(new Color(input.getRGB(i, j)).r - r))
                                + (Math.abs(new Color(input.getRGB(i, j)).g - g))
                                + (Math.abs(new Color(input.getRGB(i, j)).b - b)) / 3;
                    }
                }

                res = Math.max(0, res);
                if(res > LEVEL) {
                    res = 1;
                }
                write.setDataElements(x, y, model.getDataElements(new Color(res, res, res).toRGB(), null));
            }
        }
        return result;
    }

    /*
    public static BufferedImage render(final BufferedImage input) {

        final BufferedImage result = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_RGB);

        final WritableRaster write = result.getRaster();
        final ColorModel model = result.getColorModel();

        for (int x = 1; x < input.getWidth() - 2; x++) {
            for (int y = 1; y < input.getHeight() - 2; y++) {
                final int reference = input.getRGB(x, y);
                final Color diffuse;
                if (input.getRGB(x - 1, y - 1) == reference && input.getRGB(x, y - 1) == reference
                        && input.getRGB(x + 1, y - 1) == reference && input.getRGB(x - 1, y) == reference
                        && input.getRGB(x + 1, y) == reference && input.getRGB(x - 1, y + 1) == reference
                        && input.getRGB(x, y + 1) == reference && input.getRGB(x + 1, y + 1) == reference) {
                    diffuse = new Color(1,1,1);

                } else {
                    diffuse = new Color(0,0,0);
                }
                write.setDataElements(x, y, model.getDataElements(diffuse.toRGB(), null));
            }
        }
        return result;
    } */

}
