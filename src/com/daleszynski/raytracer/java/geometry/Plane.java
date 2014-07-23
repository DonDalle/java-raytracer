package com.daleszynski.raytracer.java.geometry;

import com.daleszynski.raytracer.java.material.Material;
import com.daleszynski.raytracer.java.math.Normal3;
import com.daleszynski.raytracer.java.math.Point3;
import com.daleszynski.raytracer.java.math.Ray;
import com.daleszynski.raytracer.java.texture.TexCoord2D;

/**
 * Stellt eine Ebene dar
 */
public class Plane extends Geometry{

    /**
     * Bekannter Punkt auf der Ebene
     */
    public final Point3 a;

    /**
     * Normale der Ebene
     */
    public final Normal3 n;


    /**
     * Erstellt die Ebene
     * @param m das Material
     */
    public Plane(final Material m) {
        super(m);
        this.a = new Point3(0,0,0);
        this.n = new Normal3(0,1,0);
    }

    @Override
    public Hit hit(final Ray r) {
        final double h = r.d.dot(this.n); //d = richtung des strahls, n = normale der ebene, wenn h positiov = weg von der ebene , negativ zur ebene hin
        if(h==0.0){
            return null;
        }

        final double t = (a.sub(r.o)).dot(n) / h;
        if(t<0.001) {
            return null;
        }

        Point3 p = r.at(t);
        return new Hit(t, r, this, n, new TexCoord2D(p.x, -p.z));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Plane)) return false;

        Plane plane = (Plane) o;

        return a.equals(plane.a) && n.equals(plane.n);

    }

    @Override
    public int hashCode() {
        int result = a.hashCode();
        result = 31 * result + n.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "a=" + a +
                ", n=" + n +
                '}';
    }
}
