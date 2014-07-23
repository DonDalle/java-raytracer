package com.daleszynski.raytracer.java.material;

import com.daleszynski.raytracer.java.geometry.Hit;
import com.daleszynski.raytracer.java.image.Color;
import com.daleszynski.raytracer.java.light.Light;
import com.daleszynski.raytracer.java.math.Normal3;
import com.daleszynski.raytracer.java.math.Point3;
import com.daleszynski.raytracer.java.math.Vector3;
import com.daleszynski.raytracer.java.raytracer.Tracer;
import com.daleszynski.raytracer.java.raytracer.World;
import com.daleszynski.raytracer.java.texture.Texture;


/**
 * Blinnphong Material
 */
public class BlinnPhongMaterial extends Material {

    /**
     * diffuse komponente
     */
    public final Texture diffuse;

    /**
     * Exponent
     */
    public final int exponent;

    /**
     * specular komponente
     */
    public final Texture specular;

    /**
     * ein lokales Beleuchtungsmodell zur Lichtreflexion an Oberflächen
     */
    public BlinnPhongMaterial(final Texture diffuse, final Texture specular, int exponent) {
        if (diffuse == null) {
            throw new IllegalArgumentException("diffuse must not be null");
        }
        if (specular == null) {
            throw new IllegalArgumentException("specular must not be null");
        }
        this.diffuse = diffuse;
        this.specular = specular;
        this.exponent = exponent;
    }

    /**
     * gibt die Farbe für ein Hit-Objekt zurück
     *
     * @param hit   Hit-Objekt
     * @param world com.daleszynski.raytracer.java.raytracer.World-Objekt
     * @return Farbwert des Hit-Objekts
     */
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
        final Color ca = world.ambientColor;
        final int p = this.exponent;
        final Normal3 n = hit.n;
        final Point3 pointHit = hit.ray.at(hit.t);
        Color sum = cd.mul(ca);

        for (final Light light : world.lights) {
            if (light.illuminates(pointHit, world)) {
                final Color cl = light.color;
                final Vector3 l = light.directionFrom(pointHit).normalized(); //Vektor zur Lichtquelle
                final Vector3 rl = l.reflectedOn(n); //Vektor zum betrachter
                final Vector3 h = l.add(rl).normalized();
                final Color part1 = cd.mul(cl).mul(Math.max(0, h.dot(l)));
                final Color part2 = cs.mul(cl).mul(Math.pow(Math.max(0, (h.dot(rl))), p));
                sum = sum.add(part1.add(part2));
            }
        }
        return sum;
    }
  
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlinnPhongMaterial)) return false;

        BlinnPhongMaterial that = (BlinnPhongMaterial) o;

        return exponent == that.exponent && diffuse.equals(that.diffuse) && specular.equals(that.specular);

    }

    @Override
    public int hashCode() {
        int result = diffuse.hashCode();
        result = 31 * result + exponent;
        result = 31 * result + specular.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "BlinnPhongMaterial{" +
                "diffuse=" + diffuse +
                ", exponent=" + exponent +
                ", specular=" + specular +
                '}';
    }
}