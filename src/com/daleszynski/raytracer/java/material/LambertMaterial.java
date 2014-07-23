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
 * Repäsentiert ein LambertMaterial
 */
public class LambertMaterial extends Material {

    /**
     * Farbe des Materials
     */
    private final Texture texture;

    /**
     * stellt das Material für einen perfekt diffus reflektierenden Körper dar
     *
     * @param texture die Textur
     */
    public LambertMaterial(final Texture texture) {
        this.texture = texture;
    }

    /**
     * gibt die Farbe für ein Hit-Objekt zurück
     *
     * @param hit   Hit-Objekt
     * @param world com.daleszynski.raytracer.java.raytracer.World-Objekt
     * @return Farbwert des Hit-Objekts
     */

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
        final Normal3 n = hit.n;
        final Color cd = this.texture.getColor(hit.texCoord2D);
        final Color ca = world.ambientColor;
        final Point3 pointHit = hit.ray.at(hit.t);
        Color sum = cd.mul(ca);
        for (final Light light : world.lights) {
            if (light.illuminates(pointHit, world)) {
                final Color cl = light.color;
                final Vector3 l = light.directionFrom(pointHit).normalized();
                sum = sum.add(
                        cd.mul(cd)
                                .mul(cl)
                                .mul(Math.max(0, n.dot(l)))
                );
            }
        }
        return sum;
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
