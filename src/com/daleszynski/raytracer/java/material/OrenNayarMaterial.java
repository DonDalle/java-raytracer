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
 * Stellt ein OrenNayar Material dar
 */
public class OrenNayarMaterial extends Material{

    public final Texture diffuse;
    public final double roughness;

    public OrenNayarMaterial(final Texture diffuse, final double roghness) {
        super();
        this.roughness = roghness;
        this.diffuse = diffuse;
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

        //Algorithmus nach http://ruh.li/GraphicsOrenNayar.html
        final Normal3 n = hit.n;
        final Vector3 eyeDir =hit.ray.d.normalized();
        final Point3 pointHit = hit.ray.at(hit.t);
        final Color cd = diffuse.getColor(hit.texCoord2D);

        Color sum = cd.mul(world.ambientColor);
        for (Light light: world.lights) {
            if(light.illuminates(pointHit, world)) {

                final Vector3 l = light.directionFrom(pointHit).normalized();
                final double NdotL = n.dot(l);
                final double NdotV = n.dot(eyeDir);

                final double angleVN = Math.acos(NdotV);
                final double angleLN = Math.acos(NdotL);
                final double alpha = Math.max(angleVN, angleLN);
                final double beta = Math.min(angleVN, angleLN);
                final Vector3 gamma1 = eyeDir.sub(n).mul(eyeDir.dot(n));
                final Vector3 gamma2 = l.sub(n).mul(l.dot(n));
                final double gamma = gamma1.dot(gamma2);

                final double roughnessSquared = roughness * roughness;

                final double A = 1.0 - 0.5 * (roughnessSquared / (roughnessSquared + 0.57));
                final double B = 0.45 * (roughnessSquared / (roughnessSquared + 0.09));
                final double C = Math.sin(alpha) * Math.tan(beta);

                final double L1 = Math.max(0.0, NdotL) * (A + B * Math.max(0.0, gamma) * C);
                //sum = sum.add(com.daleszynski.raytracer.java.light.com.daleszynski.raytracer.java.texture.mul(L1));
                sum = sum.add(cd.mul(L1));
            }

        }

        return sum;
    }
}
