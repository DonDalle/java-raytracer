package com.daleszynski.raytracer.java.math;

/**
 * Repräsentiert einen Normalenvektor auf einer Oberfläche
 */
public class Normal3 {
    /**
     * die X Rchtung
     */
    public final double x;

    /**
     * die Y Richtung
     */
    public final double y;

    /**
     * die Z Richtung
     */
    public final double z;

    /**
     * Initialisiert den Vektor
     *
     * @param x die X Rixhtung
     * @param y die Y Richtung
     * @param z die Z Richtung
     */
    public Normal3(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Multipliziert den Vektor mit einem Skalar
     *
     * @param n das Skalar
     * @return das Ergebnis
     */
    public Normal3 mul(double n) {
        return new Normal3(x * n, y * n, z * n);
    }

    /**
     * Addiert den Vektor mit einem anderen Normalen Vektor
     *
     * @param n der Andere Vektor
     * @return das Ergebnis
     */
    public Normal3 add(final Normal3 n) {
        if (n == null) {
            throw new IllegalArgumentException("Must not be null");
        }
        return new Normal3(x + n.x, y + n.y, z + n.z);
    }

    /**
     * liefert die Normale als Vektor
     * @return der Vektor
     */
    public Vector3 asVector() {
        return new Vector3(x,y,z);
    }

    /**
     * Skalarprodukt mit einem Vektor
     *
     * @param v der Vektor
     * @return das Ergebnis
     */
    public double dot(final Vector3 v) {
        if (v == null) {
            throw new IllegalArgumentException("Must not be null");
        }
        return x * v.x + y * v.y + z * v.z;

    }


    @Override
    public String toString() {
        return "com.daleszynski.raytracer.java.math.Normal3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Normal3 normal3 = (Normal3) o;

        return Double.compare(normal3.x, x) == 0 && Double.compare(normal3.y, y) == 0 && Double.compare(normal3.z, z) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
