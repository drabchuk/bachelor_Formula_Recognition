package nn.optimazers;

import static nn.algebra.Alg.*;

public class Partan {

    private Function f;
    private int dim;
    private double[] x;
    private double[] grad;
    private double[] tmp;
    private double svenLambdaFrom;
    private double svenLambdaTo;

    public Partan(Function f) {
        System.out.println("partan constructor");
        this.f = f;
        this.x = f.getCurrentArgument();
        this.dim = x.length;
        System.out.println("partan constructor initialized");
    }

    public double[] optimizeSimple(int steps, int dichotomyAccuracy) {
        double[] node1 = x.clone();
        double[] point;
        System.out.println("optimization using simple gradient descent with dichotomy started");
        point = node1.clone();
        System.out.println("cost : " + f.cost(point));
        for (int d = 0; d < steps; d++) {
            System.out.println(d + "/" + steps);
            System.out.println("gradient descent");
            grad = f.grad(point);
            System.out.println("gradient descent completed");
            sven(1.0, grad, point);
            System.out.println("sven completed");
            double directionStep = dichotomy(dichotomyAccuracy, grad, point);
            System.out.println("dichotomy completed");
            point = sum(point, dotMult(directionStep, grad));
            System.out.println("cost : " + f.cost(point));
        }
        return point;
    }

    public double[] optimize(int maxIterations, int dichotomyAccuracy) {
        double[] node1 = x.clone();
        double[] node2;
        double[] dir;
        double[] point;
        System.out.println("optimization started");
        System.out.println("max iterations : " + maxIterations);
        for (int iteration = 0; iteration < maxIterations; iteration++) {
            System.out.println("iteration : " + iteration);
            point = node1.clone();
            System.out.println("cost : " + f.cost(point));
            for (int d = 0; d < dim; d++) {
                System.out.println(d + "/" + dim);
                System.out.println("gradient descent");
                grad = f.grad(point);
                sven(1.0, grad, point);
                System.out.println("sven completed");
                double directionStep = dichotomy(dichotomyAccuracy, grad, point);
                System.out.println("dichotomy completed");
                point = sum(point, dotMult(directionStep, grad));
                System.out.println("cost : " + f.cost(point));
            }
            node2 = point.clone();
            dir = sub(node2, node1);
            sven(1.0, dir, node1);
            double mainStep = dichotomy(20, dir, node1);
            node1 = sum(node1, dotMult(mainStep, dir));
        }
        return node1;
    }

    private double dichotomy(int accuracy, double[] dir, double[] from) {
        double ax = svenLambdaFrom;
        double bx = svenLambdaTo;
        double cx;
        double a = fLambda(ax, dir, from);
        double b = fLambda(bx, dir, from);
        double c;
        for (int i = 0; i < accuracy; i++) {
            cx = (ax + bx) / 2.0;
            c = fLambda(cx, dir, from);
            if (a > b) {
                a = c;
                ax = cx;
            } else {
                b = c;
                bx = cx;
            }
        }
        return (ax + bx) / 2.0;
    }

    private void sven(double d, double[] dir, double[] from) {
        double x1, x2, x3, tmp;

        x2 = 0;
        x3 = x2 + d;

        if (fLambda(x3, dir, from) > fLambda(x2, dir, from)) {
            d = -d;
            x1 = x3;
        } else {
            x1 = x2 - d;
        }

        while (true) {
            x3 = x2 + d;
            if (fLambda(x3, dir, from) >= fLambda(x2, dir, from))
                break;
            x1 = x2;
            x2 = x3;
            d *= 2;
        }

        tmp = (x3 + x2) / 2;
        if (fLambda(tmp, dir, from) >= fLambda(x2, dir, from)) {
            x3 = tmp;
        } else {
            x1 = x2;
            x2 = tmp;
        }

        if (x1 > x3) {
            tmp = x3;
            x3 = x1;
            x1 = tmp;
        }
        svenLambdaFrom = x1;
        svenLambdaTo = x3;
    }

    private double fLambda(double lambda, double[] dir, double[] from) {
        tmp = sum(from, dotMult(lambda, dir));
        return f.cost(tmp);
    }

}
