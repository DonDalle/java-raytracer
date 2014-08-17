package com.daleszynski.raytracer.java.camera;

import com.daleszynski.raytracer.java.math.Point3;
import com.daleszynski.raytracer.java.math.Ray;
import com.daleszynski.raytracer.java.math.Vector3;

/**
 * Abstrakte Basisklasse für die Kameras.
 */
public abstract class Camera {
    /**
     * Position
     */
    public final Point3 e;

    /**
     * Blickrichtung
     */
    public final Vector3 g;

    /**
     * Up-Vektor
     */
    public final Vector3 t;

    /**
     * U-Achse des orthomaormalen Koordinatensystems
     */
    public final Vector3 u;

    /**
     * V-Achse des orthomaormalen Koordinatensystems
     */
    public final Vector3 v;

    /**
     * W-Achse des orthomaormalen Koordinatensystems
     */
    public final Vector3 w;

    /**
     * Erstellt die Kamera und konstruiert ihr orthonormale Koordinatensystem
     * @param e Position der Kamera
     * @param g Blickrichtung
     * @param t Up-Vektor
     */
    public Camera(final Point3 e, final Vector3 g, final Vector3 t) {
        if (e == null) {
            throw new IllegalArgumentException("e must not be null");
        }

        if (g == null) {
            throw new IllegalArgumentException("g must not be null");
        }
        if (t == null) {
            throw new IllegalArgumentException("t must not be null");
        }
        this.e = e;
        this.g = g;
        this.t = t;

        this.w = g.normalized().mul(-1);
        this.u = t.x(this.w).normalized();
        this.v = this.w.x(this.u);
    }

    /**
     * Erstellt einen Srahl (Ray) für einen Pixel
     * @param w Breite des Bildes
     * @param h Höhe des Bildes
     * @param x x-Koordinate des Pixels
     * @param y y-Koordinate des Pixels
     * @return the Ray for the values
     */
    public abstract java.util.List<Ray> raysFor(final int w, final int h, final int x, final int y);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Camera camera = (Camera) o;

        return e.equals(camera.e) && g.equals(camera.g) && t.equals(camera.t) && u.equals(camera.u) && v.equals(camera.v) && w.equals(camera.w);

    }

    @Override
    public int hashCode() {
        int result = e.hashCode();
        result = 31 * result + g.hashCode();
        result = 31 * result + t.hashCode();
        result = 31 * result + u.hashCode();
        result = 31 * result + v.hashCode();
        result = 31 * result + w.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Camera{" +
                "e=" + e +
                ", g=" + g +
                ", t=" + t +
                ", u=" + u +
                ", v=" + v +
                ", w=" + w +
                '}';
    }
}
