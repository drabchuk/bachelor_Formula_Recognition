package classes;

/**
 * Created by new on 24.05.2017.
 */
public interface Const {

    int IMG_AMOUNT = 300;
    int TEST_AMOUNT = 50;
    int IMG_WIDTH = 45;
    int IMG_BYTES = IMG_WIDTH * IMG_WIDTH;

    String[] PAC_NAME = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "-", "(", ")", "+", "div", "e", "log", "pi", "sin", "sqrt",
            "tan", "times", "u", "v", "x", "y", "z"};

    int LABELS_SIZE = PAC_NAME.length;

    int TOTAL_DATASET = IMG_AMOUNT * PAC_NAME.length;
    int TOTAL_TEST_DATASET = TEST_AMOUNT * PAC_NAME.length;

    String DATASET_FOLDER = "resources\\DATASET\\";

}
