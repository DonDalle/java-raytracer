package com.daleszynski.raytracer.java.light;

import com.daleszynski.raytracer.java.geometry.Hit;
import com.daleszynski.raytracer.java.image.Color;
import com.daleszynski.raytracer.java.math.Point3;
import com.daleszynski.raytracer.java.math.Ray;
import com.daleszynski.raytracer.java.math.Vector3;
import com.daleszynski.raytracer.java.raytracer.World;

/**
 * Repräsntiert ein Spotlight
 */
public class SpotLight extends Light {

    /**
     * Position
     */
    public final Point3 position;

    /**
     * Richtung
     */
    public final Vector3 direction;

    /**
     * Öffnungswinkel
     */
    public final double halfAngle;

    /**
     * ist eine Lichtquelle die von einem bestimmten Punkt aus in eine gegebene Richtung innerhalt eines festgelegten Winkels abstrahlt
     *
     * @param color Farbe de Lichtquelle
     * @param position  Position der Lichtquelle
     * @param direction Richtung der Lichtquelle
     * @param halfAngle Winkel der Lichtquelle
     */

    public SpotLight(final Color color, final Point3 position, final boolean castsShadows, final Vector3 direction, double halfAngle){
        super(color, castsShadows);
        if (position == null) {
            throw new IllegalArgumentException("position must not be null");
        }
        if (direction == null) {
            throw new IllegalArgumentException("direction must nor be null");
        }
        this.position = position;
        this.direction = direction;
        this.halfAngle = halfAngle;
    }


    /**
     * ermittelt ob der Punkt vom Licht angestrahlt wird
     *
     * @param point übergebener Punkt
     * @return boolean
     */
    @Override

    public boolean illuminates(Point3 point, World world) {
        if (world == null) {
            throw new IllegalArgumentException("world must not be null");
        }
        if (point == null) {
            throw new IllegalArgumentException("point must not be null");
        }
        final Vector3 v = point.sub(position);
        if(direction.normalized().x(v.normalized()).magnitude < Math.sin(halfAngle)) {
            if (castsShadows) {
                final Ray ray = new Ray(point, directionFrom(point));
                final Hit hit = world.hit(ray);
                if(hit == null) {
                    return true;
                }

                final double tl = ray.tOf(this.position);
                if(hit.t < tl) {
                    return false;
                }
            }
            return true;
        }
        return false;
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
        if (!(o instanceof SpotLight)) return false;
        if (!super.equals(o)) return false;

        SpotLight spotLight = (SpotLight) o;

        return Double.compare(spotLight.halfAngle, halfAngle) == 0 && direction.equals(spotLight.direction) && position.equals(spotLight.position);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + position.hashCode();
        result = 31 * result + direction.hashCode();
        temp = Double.doubleToLongBits(halfAngle);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "SpotLight{" +
                "position=" + position +
                ", direction=" + direction +
                ", halfAngle=" + halfAngle +
                '}';
    }
}
