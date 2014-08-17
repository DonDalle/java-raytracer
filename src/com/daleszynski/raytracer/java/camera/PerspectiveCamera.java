package com.daleszynski.raytracer.java.camera;

import com.daleszynski.raytracer.java.math.Point3;
import com.daleszynski.raytracer.java.math.Ray;
import com.daleszynski.raytracer.java.math.Vector3;
import com.daleszynski.raytracer.java.sampling.RegularSamplingPattern;
import com.daleszynski.raytracer.java.sampling.SamplingPattern;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Stellt eine Perspektivische Kamera dar
 */
public class PerspectiveCamera extends Camera {

    /**
     * Öffnungwinkel
     */
    public final double angle;

    public final SamplingPattern samplingPattern;

    /**
     * Erstellt eine Orhographische Kamera
     *
     * @param e     Position der Kamera
     * @param g     Blickrichtung
     * @param t     Up-Vektor
     * @param angle Öffnungswinkel
     */
    public PerspectiveCamera(Point3 e, Vector3 g, Vector3 t, double angle) {
        super(e, g, t);
        if (angle < 0) {
            throw new IllegalArgumentException("angle may not be less than 0");
        }
        this.angle = angle;
        this.samplingPattern = new RegularSamplingPattern(1,1);
    }

    //TODO Javadoc
    public PerspectiveCamera(Point3 e, Vector3 g, Vector3 t, double angle, SamplingPattern samplingPattern) {
        super(e,g,t);
        this.angle = angle;
        this.samplingPattern = samplingPattern;

    }

    @Override
    public List<Ray> raysFor(int width, int height, int xPixel, int yPixel) {
        final double x = (double)xPixel;
        final double y = (double)yPixel;
        final double h = (double)height;
        final double w = (double)width;
        if (w < 0) {
            throw new IllegalArgumentException("w must not be smaller than 1");
        }
        if (h < 0) {
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

        return samplingPattern.samplingPoints.stream()
                .map(p -> (this.w.mul(-1).mul((h / 2) / Math.tan(angle / 2)))
                                .add(u.mul(x - ((w - 1) / 2)))
                                .add(v.mul(y - ((h - 1) / 2)))
                                .add(u.mul(p.x))
                                .add(v.mul(p.y))
                )
                .map(r -> new Ray(e, r.normalized()))
                .collect(Collectors.toCollection(ArrayList::new));



        /*
        final Vector3 part1 = this.w.mul(-1).mul((h / 2) / Math.tan(angle/2));
        final Vector3 part2 = this.u.mul(x - ((w - 1) / 2));
        final Vector3 part3 = this.v.mul(y - ((h - 1) / 2));
        */
        //final Vector3 r = part1.add(part2).add(part3);
        //return Collections.singletonList(new Ray(e, r.normalized()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PerspectiveCamera)) return false;
        if (!super.equals(o)) return false;

        PerspectiveCamera camera = (PerspectiveCamera) o;

        return Double.compare(camera.angle, angle) == 0;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(angle);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "PerspectiveCamera{" +
                "angle=" + angle +
                '}';
    }
}
