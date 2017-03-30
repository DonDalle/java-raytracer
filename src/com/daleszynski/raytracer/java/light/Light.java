package com.daleszynski.raytracer.java.light;


import com.daleszynski.raytracer.java.image.Color;
import com.daleszynski.raytracer.java.math.Point3;
import com.daleszynski.raytracer.java.math.Vector3;
import com.daleszynski.raytracer.java.raytracer.World;

import java.util.List;



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
     * The Constant attenuation
     */
    public final double constantAttenuation;

    /**
     * The linear attenuation
     */
    public final double linearAttenuation;

    /**
     * the quadratic attenuation
     */
    public final double quadraticAttenuation;

    /**
     * Erstellt ein neues Licht
     * @param color Farbe des Lichts
     * @param castsShadows true wenn Licht Schatten wirft, sonst false
     */
    public Light(final Color color, final boolean castsShadows, final double constantAttenuation, final double linearAttenuation, final double quadraticAttenuation){
        if (color == null) {
            throw new IllegalArgumentException("diffuse must not be null");
        }
        this.castsShadows = castsShadows;
        this.color = color;
        this.constantAttenuation = constantAttenuation;
        this.linearAttenuation = linearAttenuation;
        this.quadraticAttenuation = quadraticAttenuation;
    }

    /**
     * ermittelt ob der Punkt vom Licht angestrahlt wird
     * @param point übergebener Punkt
     * @return boolean
     */
    public abstract List<Boolean> illuminates(Point3 point, World world);

    /**
     * gibt für den übergebenen Punkt den Vektor l zurück, der zur Lichtquelle zeigt
     * @param point übergebener Punkt
     * @return point als vector3
     */
    public abstract List<Vector3> directionFrom(Point3 point);

    /**
     * Returns the intensity on a given Point
     * @param point the Point
     * @return the Intensity
     */
    public abstract List<Double> intensity(final Point3 point);

    /**
     * Returns the number of sampling points used for this light
     * @return the Number of sampling points
     */
    public abstract int getSamplingPointsCount();

    @Override
    public String toString() {
        return "Light{" +
                "color=" + color +
                ", castsShadows=" + castsShadows +
                ", constantAttenuation=" + constantAttenuation +
                ", linearAttenuation=" + linearAttenuation +
                ", quadraticAttenuation=" + quadraticAttenuation +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Light light = (Light) o;

        return castsShadows == light.castsShadows && Double.compare(light.constantAttenuation, constantAttenuation) == 0 && Double.compare(light.linearAttenuation, linearAttenuation) == 0 && Double.compare(light.quadraticAttenuation, quadraticAttenuation) == 0 && color.equals(light.color);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = color.hashCode();
        result = 31 * result + (castsShadows ? 1 : 0);
        temp = Double.doubleToLongBits(constantAttenuation);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(linearAttenuation);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(quadraticAttenuation);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
