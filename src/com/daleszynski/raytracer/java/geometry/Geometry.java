package com.daleszynski.raytracer.java.geometry;

import com.daleszynski.raytracer.java.material.Material;
import com.daleszynski.raytracer.java.math.Ray;

/**
 * Abstrakte Basisklasse für alle Geometrien
 */
public abstract class Geometry {

    /**
     * Die Farbe der Geometrie
     */
    public final Material m;

    /**
     * Erstellt eine neue Geometrie
     * @param m die Farbe
     */
    public Geometry(Material m) {
        if (m == null) {
            throw new IllegalArgumentException("m must not be null");
        }
        this.m = m;
    }

    /**
     * liefert ein neues hit Objekt bei einem Schnittpunkt
     * @param r der zu überprüfende Strahl
     * @return ein neues Hitobjekt, mit dem kleinsten Strahl bei mehreren schnittpunkten oder null bei keinem Schnittpunkt
     */
    public abstract Hit hit(Ray r);

}
