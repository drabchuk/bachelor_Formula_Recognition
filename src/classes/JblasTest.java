package classes;

import static nn.algebra.Alg.*;

import nn.algebra.Alg;
import org.jblas.DoubleMatrix;
import org.jblas.JavaBlas;

import java.util.Arrays;

/**
 * Created by new on 24.05.2017.
 */
public class JblasTest {

    public static void main(String[] args) {


        double[][] m = new double[][] {{2, 3},{3, 4}};

        DoubleMatrix dm = new DoubleMatrix(m);

        double[] ones = new double[2];
        Arrays.fill(ones, 1.0);

        DoubleMatrix r = DoubleMatrix.concatHorizontally(new DoubleMatrix(ones), dm);

        System.out.println(r);


    }

}
