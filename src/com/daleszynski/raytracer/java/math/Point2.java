package com.daleszynski.raytracer.java.math;

/**
 * A Point with two coordinates, used for Sampling
 */
public class Point2 {

    /**
     * X coordiante
     */
    public final double x;

    /**
     * Y Coordinate
     */
    public final double y;

    public Point2(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
