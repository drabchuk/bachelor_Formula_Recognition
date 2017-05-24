package classes;

import java.io.File;
import java.io.IOException;

/**
 * Created by new on 24.05.2017.
 */
public class ImgTestRunner {

    public static void main(String[] args) {
        //"resources\\networks\\test1.txt"
        //File img = new File("resources\\DATASET\\0\\0_49.jpg");
        //byte[] bytes = JPGReader.read(img);

        /*File img = new File("resources\\DATASET\\0");
        byte[][] bytes = JPGReader.readFromFolder(img);
        System.out.println();*/

        try {
            JPGParser.parseY();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
