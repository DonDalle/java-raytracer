package com.daleszynski.raytracer.java.texture;

import com.daleszynski.raytracer.java.image.Color;

/**
 * Eine Textur für ein Schachbrettmuster
 */
public class CheckerBoardTexture implements Texture {

    /**
     * Anzahl der Felder in X Richtung
     */
    private final double squareAmountX;

    /**
     * Anzahl der Felder in Y RIchtung
     */
    private final double squareAmountY;

    /**
     * erste Farbe für die Felder
     */
    private final Color color1;

    /**
     * Zweite Farbe für die Felder
     */
    private final Color color2;

    /**
     * Initialisiert die Textur mit den Farben schwarz und weiß
     * @param squareAmountX Anzahl der Felder in X Richtung
     * @param squareAmountY Anzahl der Felder in Y Richtung
     */
    public CheckerBoardTexture(double squareAmountX, double squareAmountY) {
        this(squareAmountX, squareAmountY, new Color(0,0,0), new Color(1,1,1));
    }

    /**
     * Initialisiert die Textur mit individuellen Farben.
     * @param squareAmountX Anzahl der Felder in X Richtung
     * @param squareAmountY Anzahl der Felder in Y Richtung
     * @param color1 die erste Farbe
     * @param color2 die zweite Farbe
     */
    public CheckerBoardTexture(final double squareAmountX, final double squareAmountY, final Color color1, final Color color2) {
        if (color1 == null) {
            throw new IllegalArgumentException("color1 must not be null");
        }
        if (color2 == null) {
            throw new IllegalArgumentException("color2 must not be null");
        }

        this.color1 = color1;
        this.color2 = color2;
        this.squareAmountX = squareAmountX;
        this.squareAmountY = squareAmountY;
    }

    @Override
    public Color getColor(final double u, final double v) {
        final double stepWidth = 0.5;
        final double stepX = stepWidth / squareAmountX;
        final double stepY = stepWidth / squareAmountY;
        boolean odd = isOdd(Math.abs((int) (u / stepX))) == isOdd((int) (Math.abs(v / stepY)));
        if (u < 0) {
            odd = !odd;
        }
        if(v < 0) {
            odd = !odd;
        }
        if (odd) {
            return color1;
        } else {
            return color2;
        }
    }

    private boolean isOdd(final double value) {
        return value % 2 == 1;
    }
}
