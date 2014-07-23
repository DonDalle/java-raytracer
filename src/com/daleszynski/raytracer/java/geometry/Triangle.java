package com.daleszynski.raytracer.java.geometry;

import com.daleszynski.raytracer.java.material.Material;
import com.daleszynski.raytracer.java.math.*;
import com.daleszynski.raytracer.java.texture.TexCoord2D;

/**
 * stellt ein Dreieck dar
 */
public class Triangle extends Geometry {

    /**
     * A Punkt
     */
    public final Point3 a;

    /**
     * B Punkt
     */
    public final Point3 b;

    /**
     * C Punkt
     */
    public final Point3 c;

    /**
     * Normale von A
     */
    public final Normal3 na;

    /**
     * Normale von B
     */
    public final Normal3 nb;

    /**
     * Normale von A
     */
    public final Normal3 nc;

    /**
     * Texturkoordinate von A
     */
    public final TexCoord2D ta;

    /**
     * Texturkoordinate von B
     */
    public final TexCoord2D tb;

    /**
     * Texturkoordinate von C
     */
    public final TexCoord2D tc;

    /**
     * Erstellt das Dreieck
     * @param a der A Punkt
     * @param b der B Punkt
     * @param c der C Punkt
     * @param na Normale von A
     * @param nb Normale von B
     * @param nc Normale von C
     * @param m die Farbe
     */
    public Triangle(final Point3 a, final Point3 b, final Point3 c,
                    final Normal3 na, final Normal3 nb, final Normal3 nc,
                    final TexCoord2D ta, final TexCoord2D tb, final TexCoord2D tc,
                    final Material m) {
        super(m);
        if (a == null) {
            throw new IllegalArgumentException("a must not be null");
        }
        if (b == null) {
            throw new IllegalArgumentException("b must not be null");
        }
        if (m == null) {
            throw new IllegalArgumentException("m must not be null");
        }
        if (na == null) {
            throw new IllegalArgumentException("na must not be null");
        }
        if (nb == null) {
            throw new IllegalArgumentException("nb must not be null");
        }
        if (nc == null) {
            throw new IllegalArgumentException("nc must not be null");
        }
        if (ta == null) {
            throw new IllegalArgumentException("ta must not be null");
        }
        if (tb == null) {
            throw new IllegalArgumentException("tb must not be null");
        }
        if (tc == null) {
            throw new IllegalArgumentException("tc must not be null");
        }

        this.a = a;
        this.b = b;
        this.c = c;
        this.na = na;
        this.nb = nb;
        this.nc = nc;
        this.ta = ta;
        this.tb = tb;
        this.tc = tc;
    }

    @Override
    public Hit hit(Ray r) {
        if (r == null) {
            throw new IllegalArgumentException();
        }

        final Mat3x3 matrix = new Mat3x3(
                a.x - b.x,  a.x - c.x,  r.d.x,
                a.y - b.y,  a.y - c.y,  r.d.y,
                a.z - b.z,  a.z - c.z,  r.d.z
        );

        if(matrix.determinant < 0.000) {
            return null;
        }
        final double d = a.x - r.o.x;
        final double h = a.y - r.o.y;
        final double l = a.z - r.o.z;
        final Vector3 v = new Vector3(d,h,l);

        final double beta = matrix.changeCol1(v).determinant/matrix.determinant;
        final double gamma = matrix.changeCol2(v).determinant/matrix.determinant;
        final double alpha = 1.0 - beta - gamma;

        if(beta < 0) {
            return null;
        }
        if(beta + gamma > 1) {
            return null;
        }
        if(gamma < 0) {
            return null;
        }

        final double t = matrix.changeCol3(v).determinant/matrix.determinant;

        final Normal3 n = na.mul(alpha).add(nb.mul(beta)).add(nc.mul(gamma));
        //final Normal3  n = b.sub(a).x(a.sub(c)).normalized().asNormal();


        return new Hit(t, r, this, n, ta.mul(alpha).add(tb .mul(beta)).add(tc.mul(gamma)));


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Triangle)) return false;

        Triangle triangle = (Triangle) o;

        return a.equals(triangle.a) && b.equals(triangle.b) && c.equals(triangle.c);

    }

    @Override
    public int hashCode() {
        int result = a.hashCode();
        result = 31 * result + b.hashCode();
        result = 31 * result + c.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                '}';
    }
}
