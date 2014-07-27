package com.daleszynski.raytracer.java.sampling;

import com.daleszynski.raytracer.java.math.Point2;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class SamplingPattern {

    public final List<Point2> samplingPoints;

    public SamplingPattern(List<Point2> samplingPoints) {
        this.samplingPoints = samplingPoints;
    }


    /**
     * Returns a SamplingPattern with n random Points
     * @param n the Number of SamplingPoints
     * @return the SamplingPattern
     */
    public static SamplingPattern randomPattern(int n) {
        Random random = new Random();
        List<Point2> samplingPoints = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            samplingPoints.add(new Point2(random.nextDouble() - 0.5, random.nextDouble() - 0.5));
        }
        return new SamplingPattern(samplingPoints);
    }
}
