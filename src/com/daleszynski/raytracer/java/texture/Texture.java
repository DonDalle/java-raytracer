package com.daleszynski.raytracer.java.texture;

import com.daleszynski.raytracer.java.image.Color;

/**
 * Wird von allen Texturen implementiert
 */
public interface Texture {

    /**
     * Liefert für die Texturkoordinaten u und v die Farbe der Textur
     * @param u u Koordinate
     * @param v v Koordinate
     * @return die Farbe
     */
    public Color getColor(double u, double v);

    /**
     * Liefert für ein TexCoord2D objekt die Farbe der Textur
     * @param texCoord die Texturcoordinaten
     * @return die Farbe
     */
    default public Color getColor(final TexCoord2D texCoord) {
        if (texCoord == null) {
            throw new IllegalArgumentException("texCoord must not be null");
        }
        return getColor(texCoord.u, texCoord.v);
    }
}
