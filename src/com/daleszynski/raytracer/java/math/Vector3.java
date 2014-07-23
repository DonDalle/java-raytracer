package com.daleszynski.raytracer.java.math;

/**
 * Repräsentiert einen Vektor
 */
public class Vector3 {

    /**
     * der X Wert
     */
    public final double x;

    /**
     * der Y Wert
     */
    public final double y;

    /**
     * der Z Wert
     */
    public final double z;

    /**
     * Die Länge
     */
    public final double magnitude;

    /**
     * Erstellt einen neuen Vektor
     *
     * @param x der X wert
     * @param y der Y wert
     * @param z der Z wert
     */
    public Vector3(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.magnitude = Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * Adiert einen anderen Vektor
     *
     * @param v der andere Vektor
     * @return das Ergebnis
     */
    public Vector3 add(final Vector3 v) {
        if (v == null) {
            throw new IllegalArgumentException("Must not be null");
        }
        return new Vector3(x + v.x, y + v.y, z + v.z);
    }

    /**
     * addiert eine Normale
     *
     * @param n die Normale
     * @return das Ergebnis
     */
    @SuppressWarnings("unused")
    public Vector3 add(final Normal3 n) {
        if (n == null) {
            throw new IllegalArgumentException("Must not be null");
        }
        return new Vector3(x + n.x, y + n.y, z + n.z);
    }

    /**
     * Subtrahiert einen anderen Vektor
     *
     * @param v der Vektor
     * @return das Ergebnis
     */
    public Vector3 sub(final Vector3 v) {
        if (v == null) {
            throw new IllegalArgumentException("Must not be null");
        }
        return new Vector3(x - v.x, y - v.y, z - v.z);
    }

    /**
     * Subtrahiert eine normale
     *
     * @param v der Vektor
     * @return das Ergebnis
     */
    public Vector3 sub(final Normal3 v) {
        if (v == null) {
            throw new IllegalArgumentException("Must not be null");
        }
        return new Vector3(x - v.x, y - v.y, z - v.z);
    }

    /**
     * Multipliziert mit einem Skalar
     *
     * @param c das Skalar
     * @return das Ergebnis
     */
    public Vector3 mul(final double c) {
        return new Vector3(x * c, y * c, z * c);
    }

    /**
     * Skalarprodukt mit Vektor
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

    /**
     * Skalarprodukt mit Normale
     *
     * @param n Normale
     * @return Ergebnis
     */
    public double dot(final Normal3 n) {
        if (n == null) {
            throw new IllegalArgumentException("Must not be null");
        }
        return x * n.x + y * n.y + z * n.z;

    }

    /**
     * Normalisiert den Vektor
     *
     * @return das Ergebnis
     */
    @SuppressWarnings("unused")
    public Vector3 normalized() {
        return new Vector3(x / magnitude, y / magnitude, z / magnitude);
    }

    /**
     * Liefert Vektor als Normale
     *
     * @return das Ergebnis
     */
    @SuppressWarnings("unused")
    public Normal3 asNormal() {
        return new Normal3(x, y, z);
    }

    /**
     * Liefert Vektor als Punkt
     * @return der Punkt
     */
    @SuppressWarnings("UnusedDeclaration")
    public Point3 asPoint() {
        return new Point3(x,y,z);
    }

    /**
     * Reflektiert den Vektor an einer Normalen
     *
     * @param n die Normale
     * @return das Ergenis
     */
    public Vector3 reflectedOn(final Normal3 n) {
        if (n == null) {
            throw new IllegalArgumentException("must not be null");
        }

        final double dot = this.dot(n);
        final double rx = 2 * dot * n.x - this.x;
        final double ry = 2 * dot * n.y - this.y;
        final double rz = 2 * dot * n.z - this.z;
        return new Vector3(rx, ry, rz);


    }

    /**
     * Kreuzprodukt mit einem anderen Vektor
     *
     * @param v der andere Vektor
     * @return das Ergebnis
     */
    @SuppressWarnings("unused")
    public Vector3 x(final Vector3 v) {
        if (v == null) {
            throw new IllegalArgumentException("Must not be null");
        }
        return new Vector3(y * v.z - z * v.y,
                z * v.x - x * v.z,
                x * v.y - y * v.x
        );
    }

    @Override
    public String toString() {
        return "com.daleszynski.raytracer.java.math.Vector3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", magnitude=" + magnitude +
                '}';
    }

    @SuppressWarnings("RedundantIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector3 vector3 = (Vector3) o;

        if (Double.compare(vector3.magnitude, magnitude) != 0) return false;
        if (Double.compare(vector3.x, x) != 0) return false;
        if (Double.compare(vector3.y, y) != 0) return false;
        if (Double.compare(vector3.z, z) != 0) return false;

        return true;
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
        temp = Double.doubleToLongBits(magnitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }


}
