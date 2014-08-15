package com.daleszynski.raytracer.java.material;

import com.daleszynski.raytracer.java.geometry.Hit;
import com.daleszynski.raytracer.java.image.Color;
import com.daleszynski.raytracer.java.light.Light;
import com.daleszynski.raytracer.java.math.Normal3;
import com.daleszynski.raytracer.java.math.Point3;
import com.daleszynski.raytracer.java.math.Ray;
import com.daleszynski.raytracer.java.math.Vector3;
import com.daleszynski.raytracer.java.raytracer.Tracer;
import com.daleszynski.raytracer.java.raytracer.World;
import com.daleszynski.raytracer.java.texture.Texture;

import java.util.List;

/**
 * Stellt ein perfekt diffus reflektierendes Material dar
 */
public class ReflectiveMaterial extends Material{

    /**
     * Textur des Körpers
     */
    public final Texture diffuse;

    /**
     * Textur des Glanzpunktes
     */
    public final Texture specular;

    /**
     * Textur der spiegelnden Relektion des Glanzpunktes
     */
    public final Texture reflection;

    /**
     * exponent der Reflektion
     */
    public final double exponent;


    public ReflectiveMaterial(final Texture diffuse, final Texture specular,final double exponent, final Texture reflection) {
        if (diffuse == null) {
            throw new IllegalArgumentException("diffuse must not be null");
        }
        if (specular == null) {
            throw new IllegalArgumentException("specular must not be null");
        }
        if (reflection == null) {
            throw new IllegalArgumentException("reflection must not be null");
        }

        this.specular = specular;
        this.diffuse = diffuse;
        this.reflection = reflection;
        this.exponent = exponent;

    }

    @Override
    public Color colorFor(final Hit hit, final World world, final Tracer tracer) {


        if (hit == null) {
            throw new IllegalArgumentException("hit must not be null");
        }
        if (world == null) {
            throw new IllegalArgumentException("world must not be null");
        }
        if (tracer == null) {
            throw new IllegalArgumentException("tracer must not be null");
        }
        final Color cd = this.diffuse.getColor(hit.texCoord2D);
        final Color cs = this.specular.getColor(hit.texCoord2D);
        final Color cr = this.reflection.getColor(hit.texCoord2D);
        final Color ca = world.ambientColor;
        final Normal3 n = hit.n;
        final double p = this.exponent;
        final Vector3 d = hit.ray.d.normalized();
        final Point3 pointHit = hit.ray.at(hit.t);
        final double phi = d.mul(-1).dot(n);

        Color sum = cd.mul(ca);

        for(Light light: world.lights) {
            final List<Boolean> illuminates = light.illuminates(pointHit, world);
            final List<Vector3> directionFrom = light.directionFrom(pointHit);
            final List<Double> intensity = light.intensity(pointHit);
            Color tmp = new Color(0,0,0);
            for (int i = 0; i < light.getSamplingPointsCount(); i++) {
                if (illuminates.get(i)) {
                    final Color cl = light.color;
                    final Vector3 l = directionFrom.get(i).normalized();
                    final Vector3 rl = l.reflectedOn(n);
                    final Vector3 e = hit.ray.d.mul(-1).normalized();
                    final double in = intensity.get(i);
                    Color part1 = cd.mul(cl).mul(Math.max(0, n.dot(l))).mul(in);
                    Color part2 = cs.mul(cl).mul(Math.pow(Math.max(0, e.dot(rl)), p)).mul(in);
                    tmp = tmp.add(part1.add(part2));
                }
            }
            sum = sum.add(tmp.div(light.getSamplingPointsCount()));
        }
        final Vector3 rd = d.add(n.mul(2*phi)).normalized();
        final Ray ray = new Ray(pointHit,  rd);
        final Color reflect = tracer.colorFor(ray, world);

        sum = sum.add(cr.mul(reflect));
        return sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReflectiveMaterial)) return false;

        ReflectiveMaterial that = (ReflectiveMaterial) o;

        return Double.compare(that.exponent, exponent) == 0 && diffuse.equals(that.diffuse) && reflection.equals(that.reflection) && specular.equals(that.specular);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = diffuse.hashCode();
        result = 31 * result + specular.hashCode();
        result = 31 * result + reflection.hashCode();
        temp = Double.doubleToLongBits(exponent);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "ReflectiveMaterial{" +
                "diffuse=" + diffuse +
                ", specular=" + specular +
                ", reflection=" + reflection +
                ", exponent=" + exponent +
                '}';
    }
}
