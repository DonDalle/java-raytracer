package com.daleszynski.raytracer.java.sampling;

import com.daleszynski.raytracer.java.math.Point2;

import java.util.ArrayList;
import java.util.List;


/**
 * A regular sampling pattern, where all samplign points are aligned within a grid
 */
public class RegularSamplingPattern extends SamplingPattern {
    /**
     * Creates the regular sampling pattern, where all sampling points are ordered within a grid
     *
     * @param xCount number of sampling points among x direction
     * @param yCount number of sampling points among y direction
     */
    public RegularSamplingPattern(final int xCount, final int yCount) {
        super(generatePattern(xCount, yCount));
    }

    /**
     * Creates the default regular pattern, where all sampling points are ordered within a grid
     *
     * @param xCount number of sampling points among x direction
     * @param yCount number of sampling points among y direction
     * @return the SapmplingPattern
     */
    private static List<Point2> generatePattern(final int xCount, final int yCount) {
        final double xStart = xCount > 1 ? -0.5 : 0.0;
        final double yStart = yCount > 1 ? -0.5 : 0.0;
        final double xStep = xCount > 1 ? 1.0 / (xCount - 1.0) : 0.0;
        final double yStep = yCount > 1 ? 1.0 / (yCount - 1.0) : 0.0;


        final List<Point2> samplingPoints = new ArrayList<>();

        for (int x = 0; x < xCount; x++) {
            for (int y = 0; y < yCount; y++) {
                samplingPoints.add(new Point2((x * xStep) + xStart, (y * yStep) + yStart));
            }
        }
        return samplingPoints;
    }
}
