package com.daleszynski.raytracer.java.sampling;

import com.daleszynski.raytracer.java.math.Point2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *  A SamplingPattern with randomly aligned Sampling Points
 */
public class RandomSamplingPattern extends SamplingPattern{

    /**
     * Creates the Random Samling Pattern
     * @param numberOfSamplingPoints the number of Sampling Points
     */
    public RandomSamplingPattern(final int numberOfSamplingPoints) {
        super(generatePattern(numberOfSamplingPoints));
    }

    private static List<Point2> generatePattern(int numberOfSamplingPoints) {
        Random random = new Random();
        List<Point2> samplingPoints = new ArrayList<>();
        for (int i = 0; i < numberOfSamplingPoints; i++) {
            samplingPoints.add(new Point2(random.nextDouble() - 0.5, random.nextDouble() - 0.5));
        }
        return samplingPoints;

    }
}
