package classes;

import static classes.Const.*;

import java.io.File;
import java.io.IOException;

/**
 * Created by new on 24.05.2017.
 */
public abstract class JPGParser {

    public static byte[][] parseX() throws IOException {
        byte[][] res = new byte[TOTAL_DATASET][IMG_BYTES];
        byte[][][] res3 = new byte[PAC_NAME.length][IMG_AMOUNT][IMG_BYTES];
        for (int i = 0; i < PAC_NAME.length; i++) {
            res3[i] = JPGReader.readFromFolder(new File(DATASET_FOLDER + PAC_NAME[i]));
        }
        int c = 0;
        for (int i = 0; i < PAC_NAME.length; i++) {
            for (int j = 0; j < IMG_AMOUNT; j++) {
                System.arraycopy(res3[i][j], 0, res[c++], 0, IMG_BYTES);
            }
        }
        return res;
    }

    public static byte[] parseY() throws IOException{
        byte[] res = new byte[TOTAL_DATASET];
        int c = 0;
        for (int i = 0; i < PAC_NAME.length; i++) {
            for (int j = 0; j < IMG_AMOUNT; j++) {
                res[c++] = (byte) i;
            }
        }
        return res;
    }

    public static byte[][] parseXTest() throws IOException {
        byte[][] res = new byte[TOTAL_TEST_DATASET][IMG_BYTES];
        byte[][][] res3 = new byte[PAC_NAME.length][TEST_AMOUNT][IMG_BYTES];
        for (int i = 0; i < PAC_NAME.length; i++) {
            res3[i] = JPGReader.readTestFromFolder(new File(DATASET_FOLDER + PAC_NAME[i]));
        }
        int c = 0;
        for (int i = 0; i < PAC_NAME.length; i++) {
            for (int j = 0; j < TEST_AMOUNT; j++) {
                System.arraycopy(res3[i][j], 0, res[c++], 0, IMG_BYTES);
            }
        }
        return res;
    }

    public static byte[] parseYTest() throws IOException{
        byte[] res = new byte[TOTAL_TEST_DATASET];
        int c = 0;
        for (int i = 0; i < PAC_NAME.length; i++) {
            for (int j = 0; j < TEST_AMOUNT; j++) {
                res[c++] = (byte) i;
            }
        }
        return res;
    }

}
