package com.daleszynski.raytracer.java.light;

import com.daleszynski.raytracer.java.geometry.Hit;
import com.daleszynski.raytracer.java.image.Color;
import com.daleszynski.raytracer.java.math.Point3;
import com.daleszynski.raytracer.java.math.Ray;
import com.daleszynski.raytracer.java.math.Vector3;
import com.daleszynski.raytracer.java.raytracer.World;

/**
 * stellt eine Punktlichtquelle dar
 */
public class PointLight extends Light {

    private final Point3 position;

    /**
     * Erstellt ein neues Punktlicht, dass in alle Richtungen strahlt
     *
     * @param color Farbe des Punktlichts
     * @param position Position des Punkts
     * @param castsShadows Schattenwurf
     */
    public PointLight(final Color color, final Point3 position, final boolean castsShadows) {
        super(color, castsShadows);
        if (position == null) {
            throw new IllegalArgumentException("position must not be null");
        }
        this.position = position;
    }

    /**
     * ermittelt ob der Punkt vom Punktlicht angestrahlt wird
     *
     * @param point übergebener Punkt
     * @return boolean
     */
    @Override
    public boolean illuminates(final Point3 point, final World world) {
        if (point == null) {
            throw new IllegalArgumentException("point must not be null");
        }
        if (world == null) {
            throw new IllegalArgumentException("world must not be null");
        }
        if (castsShadows) {
            final Ray ray = new Ray(point, directionFrom(point));
            final Hit hit = world.hit(ray);
            if(hit == null || hit.t < 0.0001) {
                return true;
            }

            final double tl = ray.tOf(this.position);
            if(hit.t < tl) {
                return false;
            }
        }
        return true;
    }

    /**
     * gibt für den übergebenen Punkt den Vektor l zurück, der zur Lichtquelle zeigt
     *
     * @param point übergebener Punkt
     * @return point als vector3
     */
    @Override
    public Vector3 directionFrom(Point3 point) {
        if (point == null) {
            throw new IllegalArgumentException("point must not be null");
        }
        return position.sub(point).normalized();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PointLight)) return false;
        if (!super.equals(o)) return false;

        PointLight that = (PointLight) o;

        return position.equals(that.position);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + position.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PointLight{" +
                "position=" + position +
                '}';
    }
}
