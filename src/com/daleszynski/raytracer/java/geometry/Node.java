package com.daleszynski.raytracer.java.geometry;


import com.daleszynski.raytracer.java.material.Material;
import com.daleszynski.raytracer.java.math.Ray;
import com.daleszynski.raytracer.java.math.Transform;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


/**
 * Der Szenengraph
 */
public class Node extends Geometry {

    /**
     * Die transformation für die Objekte
     */
    public final Transform transformation;

    /**
     * Die Objekte des Graphen
     */
    public final List<Geometry> geos;

    /**
     * Erstellt einen Szenengraph
     * @param transformation die Transformation für den Graphen
     * @param geos die Geometrien
     * @param material das Material
     */
    public Node(final Transform transformation, final List<Geometry> geos, final Material material) {
        super(material);
        if (geos == null) {
            throw new IllegalArgumentException("geos must not be null");
        }
        if (transformation == null) {
            throw new IllegalArgumentException("transformation must not be null");
        }
        this.geos = geos;
        this.transformation = transformation;
    }

    /**
     * Erstellt einen Node aus einer Geometry.
     * @param transformation die Transformation
     * @param geo die Geometry
     * @param material das Material
     */
    public Node(final Transform transformation, final Geometry geo, final Material material) {
        super(material);
        this.transformation = transformation;
        final List<Geometry> list = new LinkedList<>();
        list.add(geo);
        this.geos = list;
    }

    @Override
    public Hit hit(final Ray r) {
        if (r == null) {
            throw new IllegalArgumentException("r must not be null");
        }

        final Ray transformedRay = transformation.mul(r);
        final Optional<Hit> min  = geos.stream()
                .map(geo -> geo.hit(transformedRay))
                .filter(hit -> hit != null)
                .filter(hit -> hit.t > 0)
                .min((h1, h2) -> Double.compare(h1.t, h2.t));

        if(min.isPresent()) {
            Hit hit = min.get();
            return new Hit(hit.t, r, hit.geo , transformation.mul(hit.n), hit.texCoord2D);
        }

        return null;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        return geos.equals(node.geos) && transformation.equals(node.transformation);

    }

    @Override
    public int hashCode() {
        int result = transformation.hashCode();
        result = 31 * result + geos.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Node{" +
                "transformation=" + transformation +
                ", geos=" + geos +
                '}';
    }
}
