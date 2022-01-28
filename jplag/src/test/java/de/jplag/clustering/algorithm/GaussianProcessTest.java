package de.jplag.clustering.algorithm;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.junit.Test;

public class GaussianProcessTest {
    
    @Test
    public void noisyLinearFunction() {
        List<RealVector> X = new ArrayList<>();
        double[] Y = new double[20];
        int idx = 0;
        for (int i = 0; i < 10; i++) {
            RealVector x = new ArrayRealVector(1);
            x.setEntry(0, i);
            X.add(x);
            X.add(x);
            Y[idx++] = i + Math.random() - 0.5;
            Y[idx++] = i + Math.random() - 0.5;
        }
        GaussianProcess gp = GaussianProcess.fit(X, Y, 1 / 12.0, true);
        for (int i = 0; i < 19; i++) {
            RealVector vx = new ArrayRealVector(1);
            double x = i / 2.0;
            vx.setEntry(0, x);
            double[] prediction = gp.predictWidthStd(vx);
            assertTrue("The prediction error can't be greater that the random value added", Math.abs(prediction[0] - x) < 0.5);
            assertTrue("The standard deviation must be greater than 0", prediction[1] > 0);
        }
    }
}