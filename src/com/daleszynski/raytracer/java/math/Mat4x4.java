package com.daleszynski.raytracer.java.math;


/**
 * Repeäsentiert eine 4x4 Matrix für die Transformationen
 */
public class Mat4x4 {
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
     * Element 1,4 der Matrix
     */
    public final double m14;

    /**
     * Element 2,1 der Matrix
     */
    public final double m21;

    /**
     * Element 2,2 der Matrix
     */
    public final double m22;

    /**
     * Element 2,3 der Matrix
     */
    public final double m23;
    /**
     * Element 2,4 der Matrix
     */
    public final double m24;

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
     * Element 3,4 der Matrix
     */
    public final double m34;
    /**
     * Element 4,1 der Matrix
     */
    public final double m41;
    /**
     * Element 4,2 der Matrix
     */
    public final double m42;
    /**
     * Element 4,3 der Matrix
     */
    public final double m43;
    /**
     * Element 4,2 der Matrix
     */
    public final double m44;

    public Mat4x4(final double m11, final double m12, final double m13, final double m14,
                  final double m21, final double m22, final double m23, final double m24,
                  final double m31, final double m32, final double m33, final double m34,
                  final double m41, final double m42, final double m43, final double m44) {

        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m14 = m14;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m24 = m24;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
        this.m34 = m34;
        this.m41 = m41;
        this.m42 = m42;
        this.m43 = m43;
        this.m44 = m44;
    }

    public Mat4x4 transpose() {
        return new Mat4x4(  m11, m21, m31, m41,
                            m12, m22, m32, m42,
                            m13, m23, m33, m43,
                            m14, m24, m34, m44);
    }

    public Mat4x4 mul(final Mat4x4 m) {
        if (m == null) {
            throw new IllegalArgumentException("m must not be null");
        }
        final double r11 = m11 * m.m11 + m12 * m.m21 + m13 * m.m31 + m14 * m.m41;
        final double r12 = m11 * m.m12 + m12 * m.m22 + m13 * m.m32 + m14 * m.m42;
        final double r13 = m11 * m.m13 + m12 * m.m23 + m13 * m.m33 + m14 * m.m43;
        final double r14 = m11 * m.m14 + m12 * m.m24 + m13 * m.m34 + m14 * m.m44;
        final double r21 = m21 * m.m11 + m22 * m.m21 + m23 * m.m31 + m24 * m.m41;
        final double r22 = m21 * m.m12 + m22 * m.m22 + m23 * m.m32 + m24 * m.m42;
        final double r23 = m21 * m.m13 + m22 * m.m23 + m23 * m.m33 + m24 * m.m43;
        final double r24 = m21 * m.m14 + m22 * m.m24 + m23 * m.m34 + m24 * m.m44;
        final double r31 = m31 * m.m11 + m32 * m.m21 + m33 * m.m31 + m34 * m.m41;
        final double r32 = m31 * m.m12 + m32 * m.m22 + m33 * m.m32 + m34 * m.m42;
        final double r33 = m31 * m.m13 + m32 * m.m23 + m33 * m.m33 + m34 * m.m43;
        final double r34 = m31 * m.m14 + m32 * m.m24 + m33 * m.m34 + m34 * m.m44;
        final double r41 = m41 * m.m11 + m42 * m.m21 + m43 * m.m31 + m44 * m.m41;
        final double r42 = m41 * m.m12 + m42 * m.m22 + m43 * m.m32 + m44 * m.m42;
        final double r43 = m41 * m.m13 + m42 * m.m23 + m43 * m.m33 + m44 * m.m43;
        final double r44 = m41 * m.m14 + m42 * m.m24 + m43 * m.m34 + m44 * m.m44;
        return new Mat4x4(  r11, r12, r13, r14,
                            r21, r22, r23, r24,
                            r31, r32, r33, r34,
                            r41, r42, r43, r44);
    }

    /**
     * Multiplikation mit einem Punkt, es wird angenommen, die letzte Komponente vom Punkt 1 ist
     * @param p der Punkt
     * @return das Ergebnis
     */
    public Point3 mul(final Point3 p) {
        if (p == null) {
            throw new IllegalArgumentException("p must not be null");
        }
        final double r1 = m11 * p.x + m12 * p.y + m13 * p.z + m14 * 1;
        final double r2 = m21 * p.x + m22 * p.y + m23 * p.z + m24 * 1;
        final double r3 = m31 * p.x + m32 * p.y + m33 * p.z + m34 * 1;
        return new Point3(r1, r2, r3);
    }

    /**
     * Multiplikation mit einem Vektor, es wird angenommen, die letzte Komponente vom Vektor 0 ist
     * @param v der Vektor
     * @return das Ergebnis
     */
    public Vector3 mul(final Vector3 v) {
        if (v == null) {
            throw new IllegalArgumentException("v must not be null");
        }
        final double r1 = m11 * v.x + m12 * v.y + m13 * v.z;
        final double r2 = m21 * v.x + m22 * v.y + m23 * v.z;
        final double r3 = m31 * v.x + m32 * v.y + m33 * v.z ;
        return new Vector3(r1, r2, r3);
    }

    /**
     * Multiplikation mit einer Normalen, es wird angenommen, die letzte Komponente der Normalen 0 ist
     * @param n die Normale
     * @return das Ergebnis
     */
    public Normal3 mul(final Normal3 n) {
        if (n == null) {
            throw new IllegalArgumentException("v must not be null");
        }
        final double r1 = m11 * n.x + m12 * n.y + m13 * n.z;
        final double r2 = m21 * n.x + m22 * n.y + m23 * n.z;
        final double r3 = m31 * n.x + m32 * n.y + m33 * n.z ;
        return new Normal3(r1, r2, r3);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mat4x4 mat4x4 = (Mat4x4) o;

        return Double.compare(mat4x4.m11, m11) == 0 && Double.compare(mat4x4.m12, m12) == 0 && Double.compare(mat4x4.m13, m13) == 0 && Double.compare(mat4x4.m14, m14) == 0 && Double.compare(mat4x4.m21, m21) == 0 && Double.compare(mat4x4.m22, m22) == 0 && Double.compare(mat4x4.m23, m23) == 0 && Double.compare(mat4x4.m24, m24) == 0 && Double.compare(mat4x4.m31, m31) == 0 && Double.compare(mat4x4.m32, m32) == 0 && Double.compare(mat4x4.m33, m33) == 0 && Double.compare(mat4x4.m34, m34) == 0 && Double.compare(mat4x4.m41, m41) == 0 && Double.compare(mat4x4.m42, m42) == 0 && Double.compare(mat4x4.m43, m43) == 0 && Double.compare(mat4x4.m44, m44) == 0;

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
        temp = Double.doubleToLongBits(m14);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m21);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m22);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m23);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m24);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m31);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m32);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m33);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m34);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m41);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m42);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m43);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(m44);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
