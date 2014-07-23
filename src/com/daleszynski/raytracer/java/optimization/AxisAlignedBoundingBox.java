package com.daleszynski.raytracer.java.optimization;

import com.daleszynski.raytracer.java.geometry.AxisAlignedBox;
import com.daleszynski.raytracer.java.geometry.Geometry;
import com.daleszynski.raytracer.java.geometry.Hit;
import com.daleszynski.raytracer.java.material.Material;
import com.daleszynski.raytracer.java.math.Normal3;
import com.daleszynski.raytracer.java.math.Point3;
import com.daleszynski.raytracer.java.math.Ray;
import com.daleszynski.raytracer.java.texture.TexCoord2D;

/**
 * Bounding Box um die Schnittberechnung von von Meshes zu beschleunigen. Der Strahl wird zuerst gegen die Box geprüft und dann gegen die Meshes
 */
public class AxisAlignedBoundingBox extends Geometry {

    /**
     * nahe Ecke
     */
    public final Point3 lbf;

    /**
     * ferne Ecke
     */
    public final Point3 run;


    public AxisAlignedBoundingBox(final Point3 lbf, final Point3 run, Material m) {
        super(m);
        if (lbf == null) {
            throw new IllegalArgumentException("lbf must not be null");
        }
        if (run == null) {
            throw new IllegalArgumentException("run must not be null");
        }
        this.lbf = lbf;
        this.run = run;
    }



    @Override
    public Hit hit(final Ray r) {
        if (r == null) {
            throw new IllegalArgumentException("r must not be null");
        }

        //Algorithmus nach dem Buch "Ray Tracing from the Ground up"
        //Listing 19.1
        final double x0 = lbf.x;
        final double y0 = lbf.y;
        final double z0 = lbf.z;

        final double x1 = run.x;
        final double y1 = run.y;
        final double z1 = run.z;

        final double ox = r.o.x;
        final double oy = r.o.y;
        final double oz = r.o.z;
        final double dx = r.d.x;
        final double dy = r.d.y;
        final double dz = r.d.z;

        final double tx_min;
        final double ty_min;
        final double tz_min;
        final double tx_max;
        final double ty_max;
        final double tz_max;

        final double epsilon = 0.0001;

        final double a = 1.0 / dx;
        if(a >= 0) {
            tx_min = (x0 - ox) * a;
            tx_max = (x1 - ox) * a;
        } else {
            tx_min = (x1 - ox) * a;
            tx_max = (x0 - ox) * a;
        }

        final double b = 1.0 / dy;
        if( b >= 0) {
            ty_min = (y0 - oy) * b;
            ty_max = (y1 - oy) * b;
        } else {
            ty_min = (y1 - oy) * b;
            ty_max = (y0 - oy) * b;
        }

        final double c = 1.0 / dz;
        if(c >= 0) {
            tz_min = (z0 - oz ) * c;
            tz_max = (z1 - oz)  * c;
        } else {
            tz_min = (z1 - oz) * c;
            tz_max = (z0 - oz) * c;
        }

        double t0, t1;

        //Buch Listing 19.3, Seite 360

        //find largest entering t value

        if(tx_min > ty_min) {
            t0 = tx_min;

        } else {
            t0 = ty_min;
        }

        if(tz_min > t0) {
            t0 = tz_min;
        }

        //find smallest existing value
        if(tx_max < ty_max) {
            t1 = tx_max;
        } else {
            t1 = ty_max;
        }
        if(tz_max < t1) {
            t1 = tz_max;
        }


        final double tmin;
        if(t0 < t1 && t1 >= epsilon) {
            final Normal3 n=new Normal3(1,0,0);
            if(t0 > epsilon) {
                tmin = t0;

            } else {
                tmin = t1;

            }
            return new Hit(tmin, r, this, n, new TexCoord2D(0,0)); //TODO
        }
        return null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AxisAlignedBox)) return false;

        AxisAlignedBox that = (AxisAlignedBox) o;

        return lbf.equals(that.lbf) && run.equals(that.run);

    }

    @Override
    public int hashCode() {
        int result = lbf.hashCode();
        result = 31 * result + run.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AxisAlignedBox{" +
                "lbf=" + lbf +
                ", run=" + run +
                '}';
    }
}
