package com.daleszynski.raytracer.java.geometry;


import com.daleszynski.raytracer.java.image.Color;
import com.daleszynski.raytracer.java.material.Material;
import com.daleszynski.raytracer.java.math.Ray;
import com.daleszynski.raytracer.java.math.Transform;
import com.daleszynski.raytracer.java.raytracer.Tracer;
import com.daleszynski.raytracer.java.raytracer.World;
import com.daleszynski.raytracer.java.utility.Constants;

import java.util.Collections;
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
     * will be used to determine weather we set a color for all geometries in this node.
     * Contains either no value or this object.
     */
    private final Optional<Geometry> self;

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
        this.self = Optional.of(this);
    }

    /**
     * Erstellt einen Node aus einer Geometry.
     * @param transformation die Transformation
     * @param geo die Geometry
     * @param material das Material
     */
    public Node(final Transform transformation, final Geometry geo, final Material material) {
        this(transformation, Collections.singletonList(geo), material);
    }

    /**
     * Use this constructor to create the Node and use the Materials of the geometries
     * @param transformation the Transfrom object
     * @param geos the List of Geometries
     */
    public Node(final Transform transformation, final List<Geometry> geos) {
        super(new Material() {  //TODO Create a placeholder material in an own file?
            @Override
            public Color colorFor(Hit hit, World world, Tracer tracer) {
                return new Color(-1, -1, -1);
            }
        });

        if (transformation == null) {
            throw new IllegalArgumentException("transformation must not be null");
        }
        if (geos == null) {
            throw new IllegalArgumentException("geos must not be null");
        }
        this.geos = geos;
        this.transformation = transformation;
        this.self = Optional.empty();
    }

    /**
     * Use this constructor to create the Node and use a single geometry and the Material of the Geometry
     * @param transformation the Transform objekt
     * @param geo the Geometry
     */
    public Node(final Transform transformation, final Geometry geo) {
        this(transformation, Collections.singletonList(geo));
    }

    @Override
    public Hit hit(final Ray r) {
        if (r == null) {
            throw new IllegalArgumentException("r must not be null");
        }

        final Ray transformedRay = transformation.mul(r);
        return geos.stream()
                .map(geo -> geo.hit(transformedRay))
                .filter(hit -> hit != null)
                .filter(hit -> hit.t > Constants.epsilon)
                .min((h1, h2) -> Double.compare(h1.t, h2.t))
                .map(hit -> new Hit(hit.t, r, self.orElse(hit.geo), transformation.mul(hit.n), hit.texCoord2D))
                .orElse(null);
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
