package com.daleszynski.raytracer.java.geometry;

import com.daleszynski.raytracer.java.material.Material;
import com.daleszynski.raytracer.java.math.Point3;
import com.daleszynski.raytracer.java.math.Ray;
import com.daleszynski.raytracer.java.math.Transform;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Stellt eine Box dar
 */
@SuppressWarnings("all")
public class AxisAlignedBox extends Geometry{

    /**
     * nahe Ecke
     */
    public final Point3 lbf;

    /**
     * ferne Ecke
     */
    public final Point3 run;

    /**
     * Die Seiten Der Box
     */
    public Geometry left, right, top, bottom, front, back;

    /**
     * Erstellt die Box mit Kantenlänge 1 am koordinaten ursprung
     * @param m das Material der Box
     */
    public AxisAlignedBox(final Material m) {
        super(m);
        this.lbf = new Point3(-0.5,-0.5,-0.5);
        this.run = new Point3(0.5, 0.5, 0.5);

        this.top = new Node(new Transform().translation(run), new Plane(m), m);
        this.bottom = new Node(new Transform().translation(lbf).rotateX(Math.toRadians(180)), new Plane(m), m);
        this.back = new Node(new Transform().translation(lbf).rotateZ(Math.PI).rotateX(Math.toRadians(-90)), new Plane(m), m);
        this.front = new Node(new Transform().translation(run).rotateZ(Math.toRadians(180)).rotateX(Math.toRadians(90)),new Plane(m), m);
        this.right = new Node(new Transform().translation(run).rotateZ(Math.toRadians(-90)), new Plane(m), m);
        this.left = new Node(new Transform().translation(lbf).rotateZ(Math.toRadians(90)), new Plane(m), m);

    }

    @Override
    public Hit hit(final Ray r) {
        //TODO Optimieren
        if (r == null) {
            throw new IllegalArgumentException("r must not be null");
        }


        final List<Hit> hits = new LinkedList<>();
        final List<Hit> planeHits = new ArrayList<>();

        planeHits.add(top.hit(r));
        planeHits.add(bottom.hit(r));
        for(final Hit hit: planeHits) {
            if(hit != null) {
                Point3 p = r.at(hit.t);
                if (p.x >= lbf.x && p.x <= run.x && p.z >= lbf.z && p.z <= run.z  ) {
                    hits.add(hit);
                }
            }
        }
        planeHits.clear();

        planeHits.add(right.hit(r));
        planeHits.add(left.hit(r));
        for(final Hit hit: planeHits) {
            if(hit != null) {
                Point3 p = r.at(hit.t);
                if (p.y >= lbf.y && p.y <= run.y && p.z >= lbf.z && p.z <= run.z ) {
                    hits.add(hit);
                }
            }
        }
        planeHits.clear();


        planeHits.add(front.hit(r));
        planeHits.add(back.hit(r));

        for(final Hit hit: planeHits) {
            if(hit != null) {
                Point3 p = r.at(hit.t);
                if (p.x >= lbf.x && p.x <= run.x && p.y >= lbf.y && p.y <= run.y) {
                    hits.add(hit);
                }
            }
        }

        return hits.stream()
            .filter(h -> h != null)
            .filter(h -> h.t > 0.0001)
            .min((h1, h2) -> Double.compare(h1.t, h2.t))
            .map(hit -> new Hit(hit.t, r, this, hit.n, hit.texCoord2D))
            .orElse(null);
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

