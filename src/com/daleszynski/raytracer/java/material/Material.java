package com.daleszynski.raytracer.java.material;

import com.daleszynski.raytracer.java.geometry.Hit;
import com.daleszynski.raytracer.java.image.Color;
import com.daleszynski.raytracer.java.raytracer.Tracer;
import com.daleszynski.raytracer.java.raytracer.World;

/**
 * Abstrakte Basisklasse für das Meterial
 */
public abstract class Material {

    /**
     * gibt die Farbe für ein Hit-Objekt zurück
     * @param hit Hit-Objekt
     * @param world com.daleszynski.raytracer.java.raytracer.World-Objekt
     * @return Farbwert des Hit-Objekts
     */
    public abstract Color colorFor(Hit hit, World world, Tracer tracer);
}
