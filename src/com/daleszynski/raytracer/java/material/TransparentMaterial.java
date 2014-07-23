package com.daleszynski.raytracer.java.material;

import com.daleszynski.raytracer.java.geometry.Hit;
import com.daleszynski.raytracer.java.image.Color;
import com.daleszynski.raytracer.java.math.Normal3;
import com.daleszynski.raytracer.java.math.Point3;
import com.daleszynski.raytracer.java.math.Ray;
import com.daleszynski.raytracer.java.math.Vector3;
import com.daleszynski.raytracer.java.raytracer.Tracer;
import com.daleszynski.raytracer.java.raytracer.World;

/**
 * Stellt einen transparenten Körper dar
 */
public class TransparentMaterial extends Material {


    /**
     * Brechungsindex
     */
    public double indexOfRefraction;

    public TransparentMaterial(final double indexOfRefraction) {
        this.indexOfRefraction = indexOfRefraction;
    }

    @Override
    public Color colorFor(final Hit hit, final World world, final Tracer tracer) {
        if (tracer == null) {
            throw new IllegalArgumentException("tracer must not be null");
        }
        if (world == null) {
            throw new IllegalArgumentException("world must not be null");
        }
        if (hit == null) {
            throw new IllegalArgumentException("hit must not be null");
        }
        final Vector3 d = hit.ray.d;
        Normal3 n = hit.n;
        double cosPhi1 = d.mul(-1).dot(n);
        double eta1 = world.indexOfRefraction;
        double eta2 = indexOfRefraction;

        if (cosPhi1 < 0) {
            n = hit.n.mul(-1);
            cosPhi1 = d.mul(-1).dot(n);
            eta1 = indexOfRefraction;
            eta2 = world.indexOfRefraction;
        }

        final Point3 pr = hit.ray.at(hit.t);
        final double eta = eta1 / eta2;
        final double sing = eta * eta * (1 - cosPhi1 * cosPhi1);
        final double cosPhi2 = Math.sqrt(1 - sing);
        final Vector3 rd = d.add(n.mul(cosPhi1 * 2));
        final Ray ray1 = new Ray(pr, rd);

        final boolean isTotalInnerReflection = (eta1 > eta2) && (sing > 1);

        final double R0 = Math.pow(((eta1 - eta2) / (eta1 + eta2)), 2);
        double R = R0 + (1 - R0);
        if (eta1 <= eta2) {
            R = R * Math.pow(1 - cosPhi1, 5);
        } else if (eta1 > eta2 && !isTotalInnerReflection) {
            R = R * Math.pow(1 - cosPhi2, 5);
        } else if (eta1 > eta2 && isTotalInnerReflection) {
            R = 1;
        }

        if (R != 1) {
            final Vector3 t = d.mul(eta).add(n.mul(cosPhi1 * eta - cosPhi2));
            final Ray ray2 = new Ray(pr, t);
            final double T = 1 - R;
            return (tracer.colorFor(ray1, world).mul(R)).add(tracer.colorFor(ray2, world).mul(T));
        }

        return tracer.colorFor(ray1, world);


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransparentMaterial)) return false;

        TransparentMaterial that = (TransparentMaterial) o;

        return Double.compare(that.indexOfRefraction, indexOfRefraction) == 0;

    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(indexOfRefraction);
        return (int) (temp ^ (temp >>> 32));
    }

    @Override
    public String toString() {
        return "TransparentMaterial{" +
                "indexOfRefraction=" + indexOfRefraction +
                '}';
    }
}
