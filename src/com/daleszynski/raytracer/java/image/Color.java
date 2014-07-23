package com.daleszynski.raytracer.java.image;

/**
 * Repräsentiert eine Farbe im RGB Farbraum
 */
@SuppressWarnings("UnusedDeclaration")
public class Color {

    /**
     * Rotwert zwischen 0 und 1
     */
    public final double r;

    /**
     * Grünwert zwischen 0 und 1
     */
    public final double g;

    /**
     * Blauwert zwischen 0 und 1
     */
    public final double b;


    /**
     * Erstellt das objekt aus einem rgb int
     * @param rgb die farbe
     */
    public Color(int rgb) {
        this.b = (double)(rgb & 0xFF) / 255;
        this.g = (double)((rgb >> 8)  & 0xFF) / 255.0;
        this.r = (double)((rgb >> 16) & 0xFF) / 255.0;
    }

    /**
     * Erstellt eine neue Farbe
     *
     * @param r Rotwert zwischen 0 und 1
     * @param g Grünwert zwischdn 0 und 1
     * @param b Blauwert zwischen 0 und 1
     */
    public Color(double r, double g,  double b) {
        if (r < 0) {
            throw new IllegalArgumentException("r must be positive");
        }
        if (g < 0) {
            throw new IllegalArgumentException("g must be positive");
        }
        if (b < 0) {
            throw new IllegalArgumentException("r must be positive");
        }
        /*
        if(r > 1) {
            r = 1;
        }
        if(b > 1)
            b  =1;
        if(g > 1)
            g = 1;
        */
        this.r = r;
        this.g = g;
        this.b = b;
    }

    /*
    /**
     * Wandelt die Farbe in einen RGB integer um.
     * @return das Ergebnis
     */
    public int toRGB() {

        //System.out.println( this );
        double r = Math.min(this.r, 1.0);
        double g = Math.min(this.g, 1.0);
        double b = Math.min(this.b, 1.0);


        return ((int)(r*255)) << 16 | (((int)(g*255)) << 8 | ((int)(b*255))) ;
        //return ((int)(r*255)) << 16 | (((int)(g*255)) << 8 | ((int)(b*255))) ;
    }


    /**
     * Addiert eine andere Farbe
     *
     * @param c die andere Farbe
     * @return das Ergebnis
     */
    public Color add(Color c) {
        if (c == null) {
            throw new IllegalArgumentException("c must not be null");
        }
        return new Color(r + c.r, g + c.g, b + c.b);
    }

    /**
     * subtrahiert eine andere Farbe
     *
     * @param c die andere Farbe
     * @return das Ergebnis
     */
    public Color sub(Color c) {
        if (c == null) {
            throw new IllegalArgumentException("c must not be null");
        }
        return new Color(r - c.r, g - c.g, b - c.b);
    }

    /**
     * Multipliziert eine andere Farbe
     *
     * @param c die andere Farbe
     * @return das Ergebnis
     */
    public Color mul(Color c) {
        if (c == null) {
            throw new IllegalArgumentException("c must not be null");
        }
        return new Color(r * c.r, g * c.g, b * c.b);
    }

    /**
     * Multipliziert mit einem Skalar
     *
     * @param v das Skalar
     * @return das Ergebnis
     */
    public Color mul(double v) {
        return new Color(v * r, v * g, v * b);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Color)) return false;

        Color color = (Color) o;

        return Double.compare(color.b, b) == 0 && Double.compare(color.g, g) == 0 && Double.compare(color.r, r) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(r);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(g);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(b);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Color{" +
                "r=" + r +
                ", g=" + g +
                ", b=" + b +
                '}';
    }
}
