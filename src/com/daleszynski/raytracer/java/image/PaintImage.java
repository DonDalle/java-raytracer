package com.daleszynski.raytracer.java.image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;


/**
 * nimmt ein BufferedImage und zeichnet es mittels Paint
 */
public class PaintImage extends Canvas {

    /**
     * Das Bild
     */
    private BufferedImage image;

    /**
     * Konstruktor
     * @param image das Bild
     */
    public PaintImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    public void paint( final Graphics g ) {
        g.drawImage(this.image,0,0,null );
    }

    /**
     * liefert das Bild
     * @return das Bild
     */
    public RenderedImage getImage() {
        return this.image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaintImage)) return false;

        PaintImage that = (PaintImage) o;

        return image.equals(that.image);

    }

    @Override
    public int hashCode() {
        return image.hashCode();
    }

    @Override
    public String toString() {
        return "PaintImage{" +
                "com.daleszynski.raytracer.java.image=" + image +
                '}';
    }
}
