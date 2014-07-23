//package com.daleszynski.raytracer.java.geometry;
//
//
//import com.daleszynski.raytracer.java.image.Color;
//import com.daleszynski.raytracer.java.material.Material;
//import com.daleszynski.raytracer.java.math.Normal3;
//import com.daleszynski.raytracer.java.math.Point3;
//import com.daleszynski.raytracer.java.math.Ray;
//import com.daleszynski.raytracer.java.math.Vector3;
//
//public class Rectangle extends Geometry {
//
//    /**
//     * a side od rectangle
//     */
//    public final Vector3 a;
//
//    /**
//     * b side of rectangle
//     */
//    public final Vector3 b;
//
//    /**
//     * Ursprung von a und b
//     */
//    public final Point3 p0;
//
//    /**
//     * Normale des Rechtecks
//     */
//    public final Normal3 normal;
//
//    /**
//     * erstellt ein Rechteck
//     * @param a a side
//     * @param b b side
//     * @param p0 ursprung von a und b
//     * @param normal normale ders rechtecks
//     * @param m farbe
//     */
//    public Rectangle(Vector3 a,Vector3 b, Point3 p0, Normal3 normal, Material m) {
//        super(m);
//        this.a = a;
//        this.b = b;
//        this.p0 = p0;
//        this.normal = normal;
//
//    }
//
//    @Override
//    public Hit hit(final Ray ray) {
//        // Nach dem ALgorithmus aus dem Buch "Ray Tracing from the ground up" Listing 19.8
//        if (ray == null) {
//            throw new IllegalArgumentException("ray must not be null");
//        }
//        final double t = p0.sub(ray.o).dot(normal) / ray.d.dot(normal);
//
//        if(t <= 0.0001) {
//            return null;
//        }
//
//        final Vector3 p = ray.d.mul(t).add(ray.o.asVector());
//        final Vector3 d = p.sub(p0.asVector());
//
//        final double ddota = d.dot(a);
//        if(ddota <= 0.0 || ddota > a.magnitude*a.magnitude ) {
//            return null;
//        }
//
//        final double ddotb = d.dot(b);
//
//        if(ddotb <= 0.0 || ddotb > a.magnitude*a.magnitude ) {
//            return null;
//        }
//
//        return new Hit(t, ray, this, this.normal);
//
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (!(o instanceof Rectangle)) {
//            return false;
//        }
//
//        Rectangle rectangle = (Rectangle) o;
//
//        return a.equals(rectangle.a) && b.equals(rectangle.b) && normal.equals(rectangle.normal) && p0.equals(rectangle.p0);
//
//    }
//
//    @Override
//    public int hashCode() {
//        int result = a.hashCode();
//        result = 31 * result + b.hashCode();
//        result = 31 * result + p0.hashCode();
//        result = 31 * result + normal.hashCode();
//        return result;
//    }
//
//    @Override
//    public String toString() {
//        return "Rectangle{" +
//                "a=" + a +
//                ", b=" + b +
//                ", p0=" + p0 +
//                ", normal=" + normal +
//                '}';
//    }
//}
