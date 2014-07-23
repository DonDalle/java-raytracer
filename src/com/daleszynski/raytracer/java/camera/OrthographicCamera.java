package com.daleszynski.raytracer.java.camera;

import com.daleszynski.raytracer.java.math.Point3;
import com.daleszynski.raytracer.java.math.Vector3;
import com.daleszynski.raytracer.java.math.Ray;

/**
 * Stellt eine Orthographische Kamera dar
 */
public class OrthographicCamera extends Camera {
    /**
     * Skalierungsfaktor der Bildebene
     */
    public final double s;

    /**
     * Erstellt eine Orhographische Kamera
     *
     * @param e Position der Kamera
     * @param g Blickrichtung
     * @param t Up-Vektor
     * @param s Skalierungfaktor der Bildebene
     */
    public OrthographicCamera(final Point3 e, final Vector3 g, final Vector3 t, double s) {
        super(e, g, t);
        this.s = s;
    }

    @Override
    public Ray rayFor(int width, int height, int xPixel, int yPixel) {
        final double x = (double)xPixel;
        final double y = (double)yPixel;
        final double h = (double)height;
        final double w = (double)width;
        if (w < 1) {
            throw new IllegalArgumentException("w must not be smaller than 1");
        }
        if (h < 1) {
            throw new IllegalArgumentException("h must not be smaller than 1");
        }
        if (x > w) {
            throw new IllegalArgumentException("x must not be greater than width");
        }
        if (y > h) {
            throw new IllegalArgumentException("y must not be greater than height");
        }
        if (y < 0) {
            throw new IllegalArgumentException("y must not be smaller than 0");
        }
        if (x < 0) {
            throw new IllegalArgumentException("x must not be smaller than 0");
        }
        final double a = w / h;
        final Point3 o = e.add(u.mul(a * s * ((x - (w - 1) / 2) / (w - 1))))
                    .add(v.mul(s * ((y - (h - 1) / 2) / (h - 1))));

        return new Ray(o, this.w.mul(-1));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrthographicCamera)) return false;
        if (!super.equals(o)) return false;

        OrthographicCamera that = (OrthographicCamera) o;

        return Double.compare(that.s, s) == 0;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(s);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "OrthographicCamera{" +
                "s=" + s +
                '}';
    }
}
