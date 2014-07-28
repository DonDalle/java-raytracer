package com.daleszynski.raytracer.java.light;

import com.daleszynski.raytracer.java.geometry.Hit;
import com.daleszynski.raytracer.java.image.Color;
import com.daleszynski.raytracer.java.math.Point3;
import com.daleszynski.raytracer.java.math.Ray;
import com.daleszynski.raytracer.java.math.Vector3;
import com.daleszynski.raytracer.java.raytracer.World;

import java.util.Collections;
import java.util.List;

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
        this(color, position,castsShadows,direction, halfAngle,  1,0,0);

    }
    public SpotLight(final Color color, final Point3 position, final boolean castsShadows, final Vector3 direction, double halfAngle, final double constantAttenuation, final double linearAttenuation, final double quadraticAttenuation) {
        super(color, castsShadows, constantAttenuation, linearAttenuation, quadraticAttenuation);
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

    public List<Boolean> illuminates(Point3 point, World world) {
        if (world == null) {
            throw new IllegalArgumentException("world must not be null");
        }
        if (point == null) {
            throw new IllegalArgumentException("point must not be null");
        }
        final Vector3 v = point.sub(position);
        if(direction.normalized().x(v.normalized()).magnitude < Math.sin(halfAngle)) {
            if (castsShadows) {
                final Ray ray = new Ray(point, position.sub(point).normalized());
                final Hit hit = world.hit(ray);
                if(hit == null) {
                    return Collections.singletonList(true);
                }

                final double tl = ray.tOf(this.position);
                if(hit.t < tl) {
                    return Collections.singletonList(false);
                }
            }
            return Collections.singletonList(true);
        }
        return Collections.singletonList(false);
    }

    /**
     * gibt für den übergebenen Punkt den Vektor l zurück, der zur Lichtquelle zeigt
     *
     * @param point übergebener Punkt
     * @return point als vector3
     */
    @Override
    public List<Vector3> directionFrom(Point3 point) {
        if (point == null) {
            throw new IllegalArgumentException("point must not be null");
        }
        return Collections.singletonList(position.sub(point).normalized());
    }

    @Override
    public List<Double> intensity(final Point3 point) {
        if (point == null) {
            throw new IllegalArgumentException("point must not be null");
        }

        final double distance = point.sub(position).magnitude;
        return Collections.singletonList(1 / (constantAttenuation + linearAttenuation * distance + quadraticAttenuation * distance * distance));
    }

    @Override
    public int getSamplingPointsCount() {
        return 1;
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
