package com.daleszynski.raytracer.java.light;

import com.daleszynski.raytracer.java.geometry.Hit;
import com.daleszynski.raytracer.java.image.Color;
import com.daleszynski.raytracer.java.math.Point3;
import com.daleszynski.raytracer.java.math.Ray;
import com.daleszynski.raytracer.java.math.Vector3;
import com.daleszynski.raytracer.java.raytracer.World;

/**
 * Repräsentiert ein Directionales licht
 */
public class DirectionalLight extends Light{

    /**
     * direction ist die Richtung des Lichts
     */
    public final Vector3 direction;

    /**
     * Erstellt ein neues directionales, welches überall in der Szene aus der selben Richtung kommt
     *
     * @param color Farbe des Sonnenlichts
     * @param direction  ist die Richtung des Sonnenlichts
     * @param castsShadows Schattenwurf
     */
    public DirectionalLight(final Color color, final Vector3 direction, final boolean castsShadows) {
        super(color, castsShadows, 0,0,0);
        if (direction == null) {
            throw new IllegalArgumentException("direction must not be null");
        }

        this.direction = direction;
    }

    /**
     * ermittelt ob der Punkt vom Sonnenlicht angestrahlt wird
     *
     * @param point übergebener Punkt
     * @return boolean
     */
    @Override
    public boolean illuminates(final Point3 point, World world) {
        if (point == null) {
            throw new IllegalArgumentException("point must not be null");
        }
        if (world == null) {
            throw new IllegalArgumentException("world must not be null");
        }
        if(castsShadows) {
            final Ray ray = new Ray(point, directionFrom(point));
            final Hit hit = world.hit(ray);
            return hit == null;
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
    public Vector3 directionFrom(final Point3 point) {
        return direction.mul(-1);
    }

    @Override
    public double intensity(Point3 point) {
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DirectionalLight)) return false;
        if (!super.equals(o)) return false;

        DirectionalLight that = (DirectionalLight) o;

        return direction.equals(that.direction);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + direction.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "DirectionalLight{" +
                "direction=" + direction +
                '}';
    }
}
