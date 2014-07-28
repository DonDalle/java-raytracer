package com.daleszynski.raytracer.java.light;

import com.daleszynski.raytracer.java.geometry.Hit;
import com.daleszynski.raytracer.java.image.Color;
import com.daleszynski.raytracer.java.math.Point2;
import com.daleszynski.raytracer.java.math.Point3;
import com.daleszynski.raytracer.java.math.Ray;
import com.daleszynski.raytracer.java.math.Vector3;
import com.daleszynski.raytracer.java.raytracer.World;
import com.daleszynski.raytracer.java.sampling.SamplingPattern;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Represents an Area light to support soft shadows
 */
public class AreaLight extends Light {

    /**
     * Position of the area light
     */
    public final Point3 e;

    /**
     * Viewing direction
     */
    public final Vector3 g;

    /**
     * UP-Vector
     */
    public final Vector3 t;

    /**
     * W-Axis of the orthonormal coordinate system
     */
    public final Vector3 w;

    /**
     * U-Axis of the orthonormal coordinate system
     */
    public final Vector3 u;

    /**
     * V-Axis of the orthonormal coordinate system
     */
    public final Vector3 v;

    /**
     * The SamplingPattern
     */
    public final SamplingPattern samplingPattern;

    /**
     * the size of the light
     */
    private final double size;

    public AreaLight(final Color color, final Point3 e, final Vector3 g, final Vector3 t, double size, final SamplingPattern samplingPattern, boolean castsShadows, double constantAttenuation, double linearAttenuation, double quadraticAttenuation) {
        super(color, castsShadows, constantAttenuation, linearAttenuation, quadraticAttenuation);
        this.e = e;
        this.g = g;
        this.t = t;
        this.samplingPattern = samplingPattern;
        this.size = size;

        this.w = g.normalized().mul(-1);
        this.u = t.x(this.w).normalized();
        this.v = this.w.x(this.u);

    }

    @Override
    public List<Boolean> illuminates(Point3 point, World world) {

        List<Boolean> illuminates = new ArrayList<>(samplingPattern.samplingPoints.size());
        for(final Point2 p: samplingPattern.samplingPoints) {
            final Point3 position = e.add(u.mul(p.x).mul(size)).add(v.mul(p.y).mul(size));
            final Ray ray =  new Ray(point, position.sub(point).normalized());
            final Hit hit = world.hit(ray);

            if (hit == null || hit.t < 0.0001) {
                illuminates.add(true);
            } else {
                final double tl = ray.tOf(position);
                if (hit.t < tl) {
                    illuminates.add(false);
                } else  {
                    illuminates.add(true);
                }
            }
        }
        return illuminates;
    }

    @Override
    public List<Vector3> directionFrom(Point3 point) {
        return samplingPattern.samplingPoints.stream()
                .map(p -> e.add(u.mul(p.x).mul(size)).add(v.mul(p.y).mul(size)))
                .map(position -> position.sub(point).normalized())
                .collect(Collectors.toCollection(ArrayList::new));

    }

    @Override
    public List<Double> intensity(Point3 point) {
        return samplingPattern.samplingPoints.stream()
                .map(p -> e.add(u.mul(p.x).mul(size)).add(v.mul(p.y).mul(size)))
                .map(position -> point.sub(position).magnitude)
                .map(distance -> 1 / (constantAttenuation + linearAttenuation * distance + quadraticAttenuation * distance * distance))
                .collect(Collectors.toCollection(ArrayList::new));

    }

    @Override
    public int getSamplingPointsCount() {
        return samplingPattern.samplingPoints.size();
    }
}
