package com.daleszynski.raytracer.java.math;

/**
 * Repräsentiert eine 3x3 Matrix
 */
public class Mat3x3 {
    /**
     * Element 1,1 der Matrix
     */
    public final double m11;

    /**
     * Element 1,2 der Matrix
     */
    public final double m12;

    /**
     * Element 1,3 der Matrix
     */
    public final double m13;

    /**
     * Element 2,1 der Matrix
     */
    public final double m21;

    /**
     * Element 2,2 der Matrix
     */
    public final double m22;

    /**
     * Element 1,3 der Matrix
     */
    public final double m23;

    /**
     * Element 3,1 der Matrix
     */
    public final double m31;
    /**
     * Element 3,2 der Matrix
     */
    public final double m32;
    /**
     * Element 3,3 der Matrix
     */
    public final double m33;

    /**
     * Die Determinante der Matrix
     */
    public final double determinant;


    /**
     * Initialisiert die Matrix
     *
     * @param m11 Element 1,1 der Matrix
     * @param m12 Element 1,2 der Matrix
     * @param m13 Element 1,3 der Matrix
     * @param m21 Element 2,1 der Matrix
     * @param m22 Element 2,2 der Matrix
     * @param m23 Element 2,3 der Matrix
     * @param m31 Element 3,1 der Matrix
     * @param m32 Element 3,2 der Matrix
     * @param m33 Element 3,3 der Matrix
     */
    public Mat3x3(final double m11,
                  final double m12,
                  final double m13,
                  final double m21,
                  final double m22,
                  final double m23,
                  final double m31,
                  final double m32,
                  final double m33) {
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;

        this.determinant = m11 * m22 * m33
                + m12 * m23 * m31
                + m13 * m21 * m32
                - m31 * m22 * m13
                - m32 * m23 * m11
                - m33 * m21 * m12;
    }

    /**
     * Multipliziert diese Matrix mit einer anderen Matrix
     *
     * @param m die zu Multiplizierende Matrix
     * @return das Ergebnis
     */
    public Mat3x3 mul(final Mat3x3 m) {
        if (m == null) {
            throw new IllegalArgumentException("Must not be null");
        }

        final double r11 = m11 * m.m11 + m12 * m.m21 + m13 * m.m31;
        final double r12 = m11 * m.m12 + m12 * m.m22 + m13 * m.m32;
        final double r13 = m11 * m.m13 + m12 * m.m23 + m13 * m.m33;

        final double r21 = m21 * m.m11 + m22 * m.m21 + m23 * m.m31;
        final double r22 = m21 * m.m12 + m22 * m.m22 + m23 * m.m32;
        final double r23 = m21 * m.m13 + m22 * m.m23 + m23 * m.m33;

        final double r31 = m31 * m.m11 + m32 * m.m21 + m33 * m.m31;
        final double r32 = m31 * m.m12 + m32 * m.m22 + m33 * m.m32;
        final double r33 = m31 * m.m13 + m32 * m.m23 + m33 * m.m33;


        return new Mat3x3(r11, r12, r13, r21, r22, r23, r31, r32, r33);
    }

    /**
     * Multipliziert die Matrix mit einem Vektor
     *
     * @param v der zu Multiplizierende Vektor
     * @return das Ergebnis
     */
    @SuppressWarnings("unused")
    public Vector3 mul(final Vector3 v) {
        if (v == null) {
            throw new IllegalArgumentException("Must not be null");
        }
        final double r1 = m11 * v.x + m12 * v.y + m13 * v.z;
        final double r2 = m21 * v.x + m22 * v.y + m23 * v.z;
        final double r3 = m31 * v.x + m32 * v.y + m33 * v.z;
        return new Vector3(r1, r2, r3);
    }

    /**
     * Multipliziert die Matrix mit einem Punkt
     *
     * @param p der Punkt
     * @return das Ergebnis
     */
    @SuppressWarnings("unused")
    public Point3 mul(final Point3 p) {
        if (p == null) {
            throw new IllegalArgumentException("Must not be null");
        }
        final double r1 = m11 * p.x + m12 * p.y + m13 * p.z;
        final double r2 = m21 * p.x + m22 * p.y + m23 * p.z;
        final double r3 = m31 * p.x + m32 * p.y + m33 * p.z;
        return new Point3(r1, r2, r3);
    }

    /**
     * Tauscht die erste Spalte der Matrix aus
     *
     * @param v die neue Spalte
     * @return das Ergebnis
     */
    public Mat3x3 changeCol1(final Vector3 v) {
        if (v == null) {
            throw new IllegalArgumentException("Must not be null");
        }
        return new Mat3x3(v.x, m12, m13, v.y, m22, m23, v.z, m32, m33);
    }


    /**
     * Tauscht die zweite Spalte der Matrix aus
     *
     * @param v die neue Spalte
     * @return das Ergebnis
     */
    public Mat3x3 changeCol2(final Vector3 v) {
        if (v == null) {
            throw new IllegalArgumentException("Must not be null");
        }
        return new Mat3x3(m11, v.x, m13, m21, v.y, m23, m31, v.z, m33);
    }


    /**
     * Tauscht die dritte Spalte der Matrix aus
     *
     * @param v die neue Spalte
     * @return das Ergebnis
     */
    public Mat3x3 changeCol3(Vector3 v) {
        if (v == null) {
            throw new IllegalArgumentException("Must not be null");
        }
        return new Mat3x3(m11,m12,v.x, m21, m22, v.y, m31, m32, v.z);
    }

    @SuppressWarnings("RedundantIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mat3x3 mat3x3 = (Mat3x3) o;

        if (Double.compare(mat3x3.determinant, determinant) != 0) return false;
        if (Double.compare(mat3x3.m11, m11) != 0) return false;
        if (Double.compare(mat3x3.m12, m12) != 0) return false;
        if (Double.compare(mat3x3.m13, m13) != 0) return false;
        if (Double.compare(mat3x3.m21, m21) != 0) return false;
        if (Double.compare(mat3x3.m22, m22) != 0) return false;
        if (Double.compare(mat3x3.m23, m23) != 0) return false;
        if (Double.compare(mat3x3.m31, m31) != 0) return false;
        if (Double.compare(mat3x3.m32, m32) != 0) return false;
        if (Double.compare(mat3x3.m33, m33) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(m11);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m12);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m13);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m21);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m22);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m23);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m31);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m32);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m33);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(determinant);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "com.daleszynski.raytracer.java.math.Mat3x3{" +
                "m11=" + m11 +
                ", m12=" + m12 +
                ", m13=" + m13 +
                ", m21=" + m21 +
                ", m22=" + m22 +
                ", m23=" + m23 +
                ", m31=" + m31 +
                ", m32=" + m32 +
                ", m33=" + m33 +
                ", determinant=" + determinant +
                '}';
    }
}
