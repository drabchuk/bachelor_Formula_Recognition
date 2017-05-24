package classes;

import static classes.Const.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class JPGReader {

    public static byte[][] readFromFolder(final File folder) {
        int count = 0;
        byte[][] data = new byte[IMG_AMOUNT][IMG_BYTES];
        for (final File fileEntry : folder.listFiles()) {
            System.out.println(fileEntry.getName());
            data[count] = read(fileEntry);
            if (++count >= IMG_AMOUNT) break;
        }
        return data;
    }

    public static byte[][] readTestFromFolder(final File folder) {
        int count = 0;
        int countTest = 0;
        byte[][] data = new byte[TEST_AMOUNT][IMG_BYTES];
        boolean test = false;
        for (final File fileEntry : folder.listFiles()) {
            System.out.println(fileEntry.getName());
            if (test) {
                data[countTest] = read(fileEntry);
                if (++countTest >= TEST_AMOUNT) break;
            } else {
                if (++count >= IMG_AMOUNT) test = true;
            }
        }
        return data;
    }

    public static byte[] read(File file) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] res = new byte[2025];
        int i = 0;
        for (int x = 0; x < 45; x++) {
            for (int y = 0; y < 45; y++) {
                int clr = image.getRGB(y,x);
                int  red   = (clr & 0x00ff0000) >> 16;
                int  green = (clr & 0x0000ff00) >> 8;
                int  blue  =  clr & 0x000000ff;
                int grayInt = (red + green + blue) / 3;
                //byte gray = (byte) (127 - grayInt);
                byte gray = (byte) (grayInt);
                res[i++] = gray;
            }
        }
        return res;
    }

}
