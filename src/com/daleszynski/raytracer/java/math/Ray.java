package com.daleszynski.raytracer.java.math;


/**
 * Repräsnetiert einen Strahl
 */
@SuppressWarnings("UnusedDeclaration")
public class Ray {

    /**
     * Ursprung des Strahls
     */
    public final Point3 o;

    /**
     * Richtung des strahls
     */
    public final Vector3 d;

    /**
     * Erstellt einen neuen Strahl
     * @param o der Ortsvektor
     * @param d der Richtungsvektor
     */
    public Ray(final Point3 o, final Vector3 d) {
        if (o == null) {
            throw new IllegalArgumentException("o must not be null");
        }
        if (d == null) {
            throw new IllegalArgumentException("d must not be null");
        }
        this.o = o;
        this.d = d;
    }

    /**
     * Liefert die Position vom Strahl anhand von dem Streckfaktor t
     * @param t der Streckfaktor
     * @return der Punkt
     */
    public Point3 at(final double t) {
        //p = o + td
        return  o.add(d.mul(t));
    }

    /**
     * nimmt einen Punkt und liefert das entsprechende t
     * @param p der Punkt
     * @return das t
     */
    public double tOf(final Point3 p) {
        if (p == null) {
            throw new IllegalArgumentException("p must not be null");
        }

        return p.sub(o).magnitude/d.magnitude;
    }

}
