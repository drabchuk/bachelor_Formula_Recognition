package nn;

/**
 * Created by Denis on 04.04.2017.
 */
public abstract class NNTrainer {

    protected double[][] tx;
    protected double[][] ty;

    public NNTrainer(double[][] tx, double[][] ty) {
        this.tx = tx;
        this.ty = ty;
    }

    public abstract void train(int maxSteps, int dichotomyAccuracy);
    public abstract void trainSimple(int maxSteps, int dichotomyAccuracy, int minutes);

    public void setTx(double[][] tx) {
        this.tx = tx;
    }

    public void setTy(double[][] ty) {
        this.ty = ty;
    }
}
