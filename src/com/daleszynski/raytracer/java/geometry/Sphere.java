package com.daleszynski.raytracer.java.geometry;

import com.daleszynski.raytracer.java.material.Material;
import com.daleszynski.raytracer.java.math.Point3;
import com.daleszynski.raytracer.java.math.Ray;
import com.daleszynski.raytracer.java.texture.TexCoord2D;


/**
 * Stellt eine Kugel dar
 */
public class Sphere extends Geometry{

    /**
     * Zentrumskoordinaten der Kugel
     */
    public final Point3 c;

    /**
     * Radius der Kugel
     */
    public final double r;



    /**
     * Erstellt eine Kugel an dem Punkt(0,0,0) mit dem Radius 1
     * @param m das Material der Kugel
     */
    public Sphere(final Material m) {
        super(m);
        this.c = new Point3(0,0,0);
        this.r = 1;
    }

    @Override
    public Hit hit(final Ray ray) {
        if (ray == null) {
            throw new IllegalArgumentException("ray must not be null");
        }
        final double a = ray.d.dot(ray.d);
        final double b = ray.d.dot(ray.o.sub(this.c).mul(2));

        final double c = ray.o.sub(this.c).dot(ray.o.sub(this.c)) - r*r;
        final double d = b*b - 4*a*c;

        final double epsilon = 0.0001;
        if(d<epsilon) {
            return null;
        }

        final double t1 = (-b - Math.sqrt(d)) / (2.0 * a);
        final double t2 = (-b + Math.sqrt(d)) / (2.0 * a);

        if (t1 > epsilon) {
            return new Hit(t1, ray, this, (ray.o.sub(this.c).add(ray.d.mul(t1)).mul(1.0 / r).asNormal()), calculateTexCoord(ray.at(t1)));
        }

        if (t2 > epsilon) {
            return new Hit(t2, ray, this, (ray.o.sub(this.c).add(ray.d.mul(t2)).mul(1.0 / r).asNormal()), calculateTexCoord(ray.at(t2)));
        }

        return null;



    }


    /**
     * Berechnet die Texturkoordinaten für einen Punkt
     * @param p der Punkt
     * @return die Koordinaten
     */
    private TexCoord2D calculateTexCoord(final Point3 p) {
        if (p == null) {
            throw new IllegalArgumentException("p must not be null");
        }

        final double theta = Math.acos(p.y);
        //noinspection SuspiciousNameCombination
        final double phi = Math.atan2(p.x, p.z);
        final double u = phi / (Math.PI * 2);
        final double v = -theta/Math.PI;
        return new TexCoord2D(u, v);


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sphere)) return false;

        Sphere sphere = (Sphere) o;

        return Double.compare(sphere.r, r) == 0 && c.equals(sphere.c);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = c.hashCode();
        temp = Double.doubleToLongBits(r);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "c=" + c +
                ", r=" + r +
                '}';
    }
}
