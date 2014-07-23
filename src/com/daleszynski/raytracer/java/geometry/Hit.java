package com.daleszynski.raytracer.java.geometry;

import com.daleszynski.raytracer.java.math.Normal3;
import com.daleszynski.raytracer.java.math.Ray;
import com.daleszynski.raytracer.java.texture.TexCoord2D;

/**
 * Diese Klasse drückt einen Schnitt mit einer Geometrie aus.
 */
public class Hit {

    /**
     * das t an dem der Strahl die Geometrie geschnitten hat
     */
    public final double t;

    /**
     * der Strahl der die Geometrie geschnitten hat
     */
    public final Ray ray;

    /**
     * die Geometrie die geschnitten wurde
     */
    public final Geometry geo;

    /**
     * Normale des schnittpunktes
     */
    public final Normal3 n;

    /**
     * Die 2D Texturcoordinaten des Schnittpunktes
     */
    public final TexCoord2D texCoord2D;

    /**
     * Erstellt ein neues Hit Objekt
     * @param t das t an dem der Strahl die Geometrie geschnitten hat
     * @param ray der Strahl der die Geometrie geschnitten hat
     * @param geo die Geometrie die geschnitten wurde
     * @param n Normale des schnittpunkts
     */
    public Hit(final double t, final Ray ray, final Geometry geo, final Normal3 n, final TexCoord2D texCoord2D) {
        if (ray == null) {
            throw new IllegalArgumentException("ray must not be null");
        }
        if (geo == null) {
            throw new IllegalArgumentException("geo must be null");
        }
        if (n == null) {
            throw new IllegalArgumentException("n must not be null");
        }
        if (texCoord2D == null) {
            throw new IllegalArgumentException("texCoord2D must not be null");
        }

        this.t = t;
        this.ray = ray;
        this.geo = geo;
        this.n = n;
        this.texCoord2D = texCoord2D;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hit)) return false;

        Hit hit = (Hit) o;

        return Double.compare(hit.t, t) == 0 && geo.equals(hit.geo) && ray.equals(hit.ray);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(t);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + ray.hashCode();
        result = 31 * result + geo.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Hit{" +
                "t=" + t +
                ", ray=" + ray +
                ", geo=" + geo +
                '}';
    }
}
