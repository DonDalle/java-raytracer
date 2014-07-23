package com.daleszynski.raytracer.java.light;


import com.daleszynski.raytracer.java.image.Color;
import com.daleszynski.raytracer.java.math.Point3;
import com.daleszynski.raytracer.java.math.Vector3;
import com.daleszynski.raytracer.java.raytracer.World;

/**
 * Abstrakte Basisklasse für das Licht
 */
public abstract class Light {
    /**
     * Farbe des Lichts
     */
    public final Color color;

    /**
     * true wenn Licht Schatten wirft, sonst false
     */
    public final boolean castsShadows;

    /**
     * Erstellt ein neues Licht
     * @param color Farbe des Lichts
     * @param castsShadows true wenn Licht Schatten wirft, sonst false
     */
    public Light(final Color color, final boolean castsShadows){
        if (color == null) {
            throw new IllegalArgumentException("diffuse must not be null");
        }
        this.castsShadows = castsShadows;
        this.color = color;
    }

    /**
     * ermittelt ob der Punkt vom Licht angestrahlt wird
     * @param point übergebener Punkt
     * @return boolean
     */
    public abstract boolean illuminates(Point3 point, World world);

    /**
     * gibt für den übergebenen Punkt den Vektor l zurück, der zur Lichtquelle zeigt
     * @param point übergebener Punkt
     * @return point als vector3
     */
    public abstract Vector3 directionFrom(Point3 point);

    @Override
    public String toString() {
        return "Light{" +
                "diffuse=" + color +
                '}';
    }

    public Color getColor() {
        return color;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
