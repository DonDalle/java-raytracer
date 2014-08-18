package com.daleszynski.raytracer.java.camera;

import com.daleszynski.raytracer.java.math.Point3;
import com.daleszynski.raytracer.java.math.Ray;
import com.daleszynski.raytracer.java.math.Vector3;
import com.daleszynski.raytracer.java.sampling.RegularSamplingPattern;
import com.daleszynski.raytracer.java.sampling.SamplingPattern;

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
     * The Samplingpattern to be used for the Camera
     */
    public final SamplingPattern samplingPattern;

    /**
     * Creates the Camera and uses the given Samplingpattern
     * @param e Position of Camera
     * @param g viewing direction
     * @param t up-vector
     */
    public Camera(final Point3 e, final Vector3 g, final Vector3 t) {
        this(e,g,t, new RegularSamplingPattern(1,1));
    }

    /**
     * Creates the Camera and uses the given Samplingpattern
     * @param e Position of Camera
     * @param g viewing direction
     * @param t up-vector
     * @param samplingPattern the samplingpattern to use
     */
    public Camera(Point3 e, Vector3 g, Vector3 t, SamplingPattern samplingPattern) {
        if (e == null) {
            throw new IllegalArgumentException("e must not be null");
        }

        if (g == null) {
            throw new IllegalArgumentException("g must not be null");
        }
        if (t == null) {
            throw new IllegalArgumentException("t must not be null");
        }
        if (samplingPattern == null) {
            throw new IllegalArgumentException("samplingPattern must not be null");
        }
        this.e = e;
        this.g = g;
        this.t = t;
        this.samplingPattern = samplingPattern;

        this.w = g.normalized().mul(-1);
        this.u = t.x(this.w).normalized();
        this.v = this.w.x(this.u);

    }

    /**
     * Returns a list of rays for x,y koordinates and width/height
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
