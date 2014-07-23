package com.daleszynski.raytracer.java.material;

import com.daleszynski.raytracer.java.geometry.Hit;
import com.daleszynski.raytracer.java.image.Color;
import com.daleszynski.raytracer.java.raytracer.Tracer;
import com.daleszynski.raytracer.java.raytracer.World;
import com.daleszynski.raytracer.java.texture.Texture;

/**
 * EIn com.daleszynski.raytracer.java.material mit einer einfachen Farbe
 */
public class SingleColorMaterial extends Material {
    /**
     * Farbe des Materials
     */
    public final Texture texture;

    public SingleColorMaterial(final Texture texture){
        this.texture = texture;
    }

    /**
     * gibt die Farbe unabhängig von der Lichtquelle für ein Hit-Objekt zurück
     *
     * @param hit   Hit-Objekt
     * @param world com.daleszynski.raytracer.java.raytracer.World-Objekt
     * @return Farbwert des Hit-Objekts
     */
    @Override
    public Color colorFor(Hit hit, World world, Tracer tracer) {
        if (world == null) {
            throw new IllegalArgumentException("world must not be null");
        }
        return this.texture.getColor(hit.texCoord2D);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SingleColorMaterial)) return false;

        SingleColorMaterial that = (SingleColorMaterial) o;

        return texture.equals(that.texture);

    }

    @Override
    public int hashCode() {
        return texture.hashCode();
    }


    @Override
    public String toString() {
        return "SingleColorMaterial{" +
                "cd=" + texture +
                '}';
    }
}
