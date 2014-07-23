package com.daleszynski.raytracer.java.raytracer;

import com.daleszynski.raytracer.java.geometry.Hit;
import com.daleszynski.raytracer.java.image.Color;
import com.daleszynski.raytracer.java.material.Material;
import com.daleszynski.raytracer.java.math.Ray;


/**
 * Tracer Klasse
 */
public class Tracer {

    public int currentRecursionDepth = 0 ;
    public static int maximumRecursionDepth = 5;

    /**
     * Führt Raytracing aus und liefert eine Farbe für einen Strahl
     * @param ray der Strahl
     * @param world die Welt
     * @return die Farbe
     */
    public Color colorFor(final Ray ray, final World world) {
        currentRecursionDepth++; //Eintritt in Funtion -> Tiefe eerhöhen
        if( currentRecursionDepth < maximumRecursionDepth ) {
            final Hit hit = world.hit(ray);
            if (hit != null) {
                final Material m = hit.geo.m;
                final Color c = m.colorFor(hit, world, this); //Rekursiver aufrif
                currentRecursionDepth--; //Austritt aus der Funktion -> Tiefe verringern
                return c;
            }
        }
        currentRecursionDepth--; //Austritt aus der Funktion -> Tiefe verringern
        return world.backgroundColor;

    }
}