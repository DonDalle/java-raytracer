package com.daleszynski.raytracer.java.sampling;

import com.daleszynski.raytracer.java.math.Point2;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;


/**
 * Represents a Sampling Pattern
 */
public class SamplingPattern {

    /**
     * The Samplingpoints of this pattern
     */
    public final List<Point2> samplingPoints;

    /**
     * Creates a new Samplingpattern with a list of sampling points
     * @param samplingPoints the samplingpoints
     */
    public SamplingPattern(List<Point2> samplingPoints) {
        this.samplingPoints = samplingPoints;
    }
}
