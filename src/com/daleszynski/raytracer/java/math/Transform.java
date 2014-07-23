package com.daleszynski.raytracer.java.math;

/**
 * Dient der Transformation von Objekten
 */
@SuppressWarnings("unused") //TOOD
public class Transform {

    /**
     * Die Transformationsmatrix
     */
    public final Mat4x4 m;

    /**
     * Die Inverse der Transformationsmatrix
     */
    public final Mat4x4 i;

    /**
     * Initialisiert die Transformation mit der Einheitsmatrix
     */
    public Transform() {
        this.m = new Mat4x4(1,0,0,0
                            ,0,1,0,0
                            ,0,0,1,0
                            ,0,0,0,1);

        this.i = new Mat4x4(1,0,0,0
                            ,0,1,0,0
                            ,0,0,1,0
                            ,0,0,0,1);
    }

    /**
     * Privater Kontruktor nimmt Matrix und Inverse
     * @param m Matrix
     * @param i Inverse von m
     */
    private  Transform(final Mat4x4 m, final Mat4x4 i) {
        if (m == null) {
            throw new IllegalArgumentException("m must not be null");
        }
        if (i == null) {
            throw new IllegalArgumentException("i must not be null");
        }
        this.i = i;
        this.m = m;
    }


    /**
     * Translation (Verschiebung)
     * @param x  Translation in X Richtung
     * @param y  Translation in Y Richtungt
     * @param z  Translation in z Richtungt
     * @return Ergebnis
     */
    public Transform translation(final double x, final double y, final double z) {
        final Mat4x4 translationMatrix = new Mat4x4(  1, 0, 0, x,
                                                      0, 1, 0, y,
                                                      0, 0, 1, z,
                                                      0, 0, 0, 1);

        final Mat4x4 inverseTranslationMatrix = new Mat4x4(     1, 0, 0, -x,
                                                                0, 1, 0, -y,
                                                                0, 0, 1, -z,
                                                                0, 0, 0, 1);
        return new Transform(m.mul(translationMatrix), inverseTranslationMatrix.mul(i));

    }

    /**
     * Translation an einen Punkt
     * @param p der Punkt
     * @return das Ergebnis
     */
    public Transform translation(final Point3 p) {
        if (p == null) {
            throw new IllegalArgumentException("p must not be null");
        }
        return this.translation(p.x, p.y, p.z);
    }


    /**
     * Skalierung
     * @param x Skalierung in X Richtung
     * @param y Skalierung in Y RIchtung
     * @param z Skalierung in Z Richtung
     * @return das Ergebnis
     */
    public Transform scaling(final double x, final double y, final double z) {
        final  Mat4x4 scalingMatrix = new Mat4x4(x, 0, 0, 0,
                                                0, y, 0, 0,
                                                0, 0, z, 0,
                                                0, 0, 0, 1);
        final Mat4x4 inverseScalingMatrix = new Mat4x4(1/x, 0, 0, 0,
                                                        0, 1/y, 0, 0,
                                                        0, 0, 1/z, 0,
                                                        0, 0, 0, 1);
        return new Transform(m.mul(scalingMatrix), inverseScalingMatrix.mul(i));

    }

    /**
     * Sklaierung mit einem Faktor für alle Seiten
     * @param s der Faktor
     * @return das Ergebnis
     */
    public Transform scaling(final double s) {
        return this.scaling(s,s,s);
    }



    /**
     * Rotation um X Achse gegen den Uhrzeigersinn
     * @param angle der Rotations Winkel
     * @return das Ergebnis
     */
    public Transform rotateX(final double angle) {
        final Mat4x4 rotationMatrix = new Mat4x4(1, 0, 0, 0,
                                                0, Math.cos(angle), -Math.sin(angle), 0,
                                                0, Math.sin(angle), Math.cos(angle), 0,
                                                0, 0, 0, 1);
        final Mat4x4 inverseRotationMatrix = new Mat4x4(  1, 0, 0, 0,
                                                    0, Math.cos(angle), Math.sin(angle), 0,
                                                    0, -Math.sin(angle), Math.cos(angle), 0,
                                                    0, 0, 0, 1);
        return new Transform(m.mul(rotationMatrix), inverseRotationMatrix.mul(i));

    }

    /**
     * Rotation um Y Achse gegen den Uhrzeigersinn
     * @param angle der Rotations Winkel
     * @return das Ergebnis
     */
    public Transform rotateY(final double angle) {
        final Mat4x4 rotationMatrix = new Mat4x4( Math.cos(angle), 0, Math.sin(angle), 0,
                                                    0, 1, 0, 0,
                                                    -Math.sin(angle), 0, Math.cos(angle), 0,
                                                    0,0,0,1);
        final Mat4x4 inverseRotationMatrix = new Mat4x4( Math.cos(angle), 0, -Math.sin(angle), 0,
                                                    0, 1, 0, 0,
                                                    Math.sin(angle), 0, Math.cos(angle), 0,
                                                    0,0,0,1);
        return new Transform(m.mul(rotationMatrix), inverseRotationMatrix.mul(i));

    }

    /**
     * Rotation um Z Achse gegen den Uhrzeigersinn
     * @param angle der Rotations Winkel
     * @return das Ergebnis
     */
    public Transform rotateZ(final double angle) {
        final Mat4x4 rotationMatrix = new Mat4x4( Math.cos(angle), -Math.sin(angle), 0, 0,
                                            Math.sin(angle), Math.cos(angle), 0, 0,
                                            0,0,1,0,
                                            0,0,0,1);
        final Mat4x4 inverseRotationMatrix = new Mat4x4( Math.cos(angle), Math.sin(angle), 0, 0,
                                            -Math.sin(angle), Math.cos(angle), 0, 0,
                                            0,0,1,0,
                                            0,0,0,1);
        return new Transform(m.mul(rotationMatrix), inverseRotationMatrix.mul(i));

    }

    /**
     * Transformiert einen Strahl
     * @param ray der zu transformierende Strahl
     * @return der transformierte Strahl
     */
    public Ray mul(final Ray ray) {
        if (ray == null) {
            throw new IllegalArgumentException("ray must not be null");
        }
        return new Ray(i.mul(ray.o), i.mul(ray.d));
    }


    /**
     * Transformiert eine Normale
     * @param n die Normale
     * @return das Ergebnis
     */
    public Normal3 mul(final Normal3 n) {
        if (n == null) {
            throw new IllegalArgumentException("n must not be null");
        }
        return i.transpose().mul(n.asVector()).normalized().asNormal();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transform transform = (Transform) o;

        return i.equals(transform.i) && m.equals(transform.m);

    }

    @Override
    public int hashCode() {
        int result = m.hashCode();
        result = 31 * result + i.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Transform{" +
                "m=" + m +
                ", i=" + i +
                '}';
    }

}
