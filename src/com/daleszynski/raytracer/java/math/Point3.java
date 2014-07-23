package com.daleszynski.raytracer.java.math;

/**
 * Repräsentiert einen Punkt
 */
public class Point3 {

    /**
     * Die X Coordinte
     */
    public final double x;

    /**
     * Die Y Coordinte
     */
    public final double y;

    /**
     * Die Z Coordinte
     */
    public final double z;

    /**
     * Erstellt einen neuen Punkt
     *
     * @param x X Coordiante
     * @param y Y Coordinate
     * @param z Z Coordinate
     */
    public Point3(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Subtrahiert einen einen Punkt und liefert einen Vektor
     *
     * @param p der andere Punkt
     * @return das Ergebnis
     */
    public Vector3 sub(final Point3 p) {
        if (p == null) {
            throw new IllegalArgumentException("Must not be null");
        }
        return new Vector3(x - p.x, y - p.y, z - p.z);
    }



    /**
     * Subtrahiert einen Vektor
     *
     * @param v der Vektor
     * @return das Ergebnis
     */
    public Point3 sub(final Vector3 v) {
        if (v == null) {
            throw new IllegalArgumentException("Must not be null");
        }
        return new Point3(x - v.x, y - v.y, z - v.z);
    }

    /**
     * Addiert einen Vektor
     *
     * @param v der Vektor
     * @return das Ergebnis
     */
    public Point3 add(final Vector3 v) {
        if (v == null) {
            throw new IllegalArgumentException("Must not be null");
        }
        return new Point3(x + v.x, y + v.y, z + v.z);
    }

    /**
     * Gibt punkt als Vektor
     * @return der Vektor
     */
    @SuppressWarnings("UnusedDeclaration")
    public Vector3 asVector() {

        return new Vector3(x,y,z);
    }

    @SuppressWarnings("RedundantIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point3 point3 = (Point3) o;

        if (Double.compare(point3.x, x) != 0) return false;
        if (Double.compare(point3.y, y) != 0) return false;
        if (Double.compare(point3.z, z) != 0) return false;

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
        return result;
    }

    @Override
    public String toString() {
        return "com.daleszynski.raytracer.java.math.Point3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }


}
