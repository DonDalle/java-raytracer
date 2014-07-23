package com.daleszynski.raytracer.java.texture;

/**
 * Eine Texturkoordinate
 */
public class TexCoord2D {

    /**
     * die u Koordinate
     */
    public final double u;

    /**
     * die v Koordiante
     */
    public final double v;

    /**
     * Erstellt die Texturkoordinate
     * @param u die u Koordiante
     * @param v die v Koordinate
     */
    public TexCoord2D(final double u, final double v) {
        this.u = u;
        this.v = v;
    }

    public TexCoord2D mul(final double a) {
        return new TexCoord2D(u*a, a*a);
    }
    public TexCoord2D add(final TexCoord2D t) {
        if (t == null) {
            throw new IllegalArgumentException("t must not be null");
        }

        return new TexCoord2D(u + t.u, v + t.v);
    }
}
