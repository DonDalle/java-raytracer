package com.daleszynski.raytracer.java.raytracer;

import com.daleszynski.raytracer.java.geometry.Geometry;
import com.daleszynski.raytracer.java.geometry.Hit;
import com.daleszynski.raytracer.java.image.Color;
import com.daleszynski.raytracer.java.light.Light;
import com.daleszynski.raytracer.java.math.Ray;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Die darzustellende Welt
 */
public class World {

    /**
     * Hintergrundfarbe der Welt
     */
    public final Color backgroundColor;

    /**
     * Ambiente Farbe der Welt
     */
    public final Color ambientColor;

    /**
     * Lichtquellen der Welt
     */
    public final List<Light> lights;

    /**
     * Objekte der Welt
     */
    public final List<Geometry> geos;

    /**
     * Brechungsindex der Welt
     */
    public final double indexOfRefraction;

    /**
     * Erstellt die Welt
     * @param backgroundColor Hintergundfarbe
     * @param ambientColor Ambiente Beleuchtung
     * @param indexOfRefraction Brechungsindex
     */
    public World(final Color backgroundColor, final Color ambientColor, final double indexOfRefraction) {
        this.geos = new LinkedList<>();
        this.lights = new LinkedList<>();
        this.backgroundColor = backgroundColor;
        this.ambientColor = ambientColor;
        this.indexOfRefraction = indexOfRefraction;
    }


    /**
     * fügt eine Geometire hinzu
     * @param geo die Geometrie
     */
    public void addGeo(final Geometry geo) {
        if (geo == null) {
            throw new IllegalArgumentException("geo must not be null");
        }
        this.geos.add(geo);
    }

    public void addLight(final Light light) {
        if (light == null) {
            throw new IllegalArgumentException("com.daleszynski.raytracer.java.light must not be null");
        }
        this.lights.add(light);
    }

    /**
     * liefert ein Hitobjekt
     * @param r der Stargl
     * @return hitobjekt oder null wenn kein schnittpuhnkt für den strahl
     */
    public Hit hit(final Ray r) {
        if (r == null) {
            throw new IllegalArgumentException("r must not be null");
        }
        final Optional<Hit> min  = geos.stream()
             .map(geo -> geo.hit(r))
             .filter(hit -> hit != null)
             .filter(hit -> hit.t > 0)
             .min((h1, h2) -> Double.compare(h1.t, h2.t));

        if(min.isPresent()) {
            return min.get();
        }

        return null;

    }

    @Override
    public String toString() {
        return "com.daleszynski.raytracer.java.raytracer.World{" +
                "backgroundColor=" + backgroundColor +
                ", geos=" + geos +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof World)) return false;

        World world = (World) o;

        return backgroundColor.equals(world.backgroundColor) && geos.equals(world.geos);

    }

    @Override
    public int hashCode() {
        int result = backgroundColor.hashCode();
        result = 31 * result + geos.hashCode();
        return result;
    }
}
