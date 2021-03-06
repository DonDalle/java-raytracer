package com.daleszynski.raytracer.java.material;


import com.daleszynski.raytracer.java.geometry.Hit;
import com.daleszynski.raytracer.java.image.Color;
import com.daleszynski.raytracer.java.light.Light;
import com.daleszynski.raytracer.java.math.Point3;
import com.daleszynski.raytracer.java.math.Vector3;
import com.daleszynski.raytracer.java.raytracer.Tracer;
import com.daleszynski.raytracer.java.raytracer.World;

import java.util.List;

/**
 * Repräsentiert das Material für das Cel Shading
 */
@SuppressWarnings("UnusedDeclaration")
public class CelShadingMaterial extends Material {

    /**
     * Die Farbe des Materials
     */
    public final Color color;

    public CelShadingMaterial(Color color) {
        this.color = color;
    }

    @Override
    public Color colorFor(Hit hit, World world, Tracer tracer) {


        if (hit == null) {
            throw new IllegalArgumentException("hit must not be null");
        }
        if (world == null) {
            throw new IllegalArgumentException("world must not be null");
        }
        if (tracer == null) {
            throw new IllegalArgumentException("tracer must not be null");
        }
        final Color cd = this.color;
        final Color ca = world.ambientColor;
        final Point3 pointHit = hit.ray.at(hit.t);
        Color sum = cd.mul(ca);


        for (Light light : world.lights) {
            final List<Boolean> illuminates = light.illuminates(pointHit, world);
            final List<Vector3> directionFrom = light.directionFrom(pointHit);
            final List<Double> intensity = light.intensity(pointHit);
            Color tmp = new Color(0,0,0);
            for (int i = 0; i < light.getSamplingPointsCount(); i++) {
                if (illuminates.get(i)) {

                    final Color cl = light.color;
                    final Vector3 l = directionFrom.get(i).normalized();

                    double gray = (hit.n.dot(l));
                    gray = Math.max(gray, 0);
                    gray = grayScale(gray, 7);

                    tmp = tmp.add(cd.mul(gray).mul(cl).mul(intensity.get(i)));

                }
            }
            sum = sum.add(tmp.div(light.getSamplingPointsCount()));
        }
        return sum;
    }

    /**
     * Führst stufenbildung des Grauwertes Durch
     *
     * @param c             Grauwert
     * @param numberofsteps anzahl der STufen
     * @return Die Stufe
     */
    private double grayScale(final double c, final int numberofsteps) {
        double step = 1 / (double) numberofsteps;
        for (int i = 0; i < numberofsteps; i++) {
            if (c < step * i) {
                return i * step;
            }

        }
        return 1;

    }

    @Override
    public String toString() {
        return "CelShadingMaterial{" +
                "diffuse=" + color +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CelShadingMaterial)) return false;

        CelShadingMaterial that = (CelShadingMaterial) o;

        return color.equals(that.color);

    }

    @Override
    public int hashCode() {
        return color.hashCode();
    }
}
