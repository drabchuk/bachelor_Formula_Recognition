package nn.perceptrons;

import static nn.algebra.Alg.*;

import nn.NeuralNetwork;
import classes.NeuralWriter;
import org.jblas.DoubleMatrix;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Denis on 30.03.2017.
 */
public class DeepFeedForwardNN extends NeuralNetwork {

    private int depth;
    private int[] layerSizes;
    private double[][][] theta;

    public DeepFeedForwardNN(int depth, int[] layerSizes) {
        this.depth = depth;
        this.layerSizes = layerSizes;
        this.theta = new double[depth - 1][][];
        for (int i = 0; i < depth - 1; i++) {
            this.theta[i] = randInitForWeights(layerSizes[i + 1], layerSizes[i] + 1);
        }
    }

    public DeepFeedForwardNN(int depth, int[] layerSizes, double[][][] theta) {
        this.depth = depth;
        this.layerSizes = layerSizes;
        this.theta = theta;
    }

    /**
     * return z [0] and a [1]
     */
    public double[][][] forwardPropagation(double[] features) {
        double[][][] za = new double[2][depth][];
        double[] a = features;
        za[0][0] = features;
        for (int i = 0; i < depth - 1; i++) {
            a = addForwardNum(1.0, a);
            za[1][i] = a;
            double[] z = mult(theta[i], a);
            za[0][i + 1] = z;
            a = sigmoid(z);
        }
        za[1][depth - 1] = a;
        return za;
    }

    @Override
    public double[] predict(double[] features) {
        double[] a = features;
        for (int i = 0; i < depth - 1; i++) {
            a = addForwardNum(1.0, a);
            double[] z = mult(theta[i], a);
            a = sigmoid(z);
        }
        return a;
    }
    @Override
    public double[][] predict(double[][] features) {
        DoubleMatrix a = new DoubleMatrix(features);
        double[] ones;
        DoubleMatrix onesDM;


        //DoubleMatrix r = DoubleMatrix.concatHorizontally(new DoubleMatrix(ones), dm);
        for (int i = 0; i < depth - 1; i++) {
            ones = new double[a.rows];
            Arrays.fill(ones, 1.0);
            onesDM = new DoubleMatrix(ones);
            a = DoubleMatrix.concatHorizontally(onesDM, a);
            DoubleMatrix thet = new DoubleMatrix(theta[i]);
            DoubleMatrix z = a.mmul(thet.transpose());
            a = new DoubleMatrix(sigmoid(z.toArray2()));
        }

        return a.toArray2();
    }

    /*@Override
    public double[][] predict(double[][] features) {
        double[][] res = new double[features.length][];
        for (int j = 0; j < features.length; j++) {
            double[] a = features[j];
            for (int i = 0; i < depth - 1; i++) {
                a = addForwardNum(1.0, a);
                double[] z = mult(theta[i], a);
                a = sigmoid(z);
            }
            res[j] = a;
        }

        return res;
    }*/

    @Override
    public void saveWeights(File file) throws IOException {
        NeuralWriter.getWriter().start(file, depth, layerSizes, theta);
    }

    public int getDepth() {
        return depth;
    }

    public int[] getLayerSizes() {
        return layerSizes;
    }

    public double[][][] getTheta() {
        return theta;
    }

    public void setTheta(double[][][] theta) {
        this.theta = theta;
    }
}
