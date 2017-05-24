package sample;

import static classes.Const.*;

import classes.CustomImage;
import classes.JPGParser;
import classes.Parser;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import nn.NeuralNetwork;
import classes.NeuralReader;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import static nn.data.ImgBrightnessMapper.map;

public class Controller {

    @FXML
    private Button chooseButton, chooseXButton, chooseYButton, startTestButton, nextImgButton;

    @FXML
    private ImageView randomDidgitImg;

    @FXML
    private Label recognizedLabel;

    @FXML
    private javafx.scene.control.TextField resultError;

    private NeuralNetwork nn;
    private byte[][] imgs;
    private byte[] labels;


    @FXML
    public void initialize() {


        chooseButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open NN File");
                File file = fileChooser.showOpenDialog(new Stage());
                nn = NeuralReader.getReader().start(file);
            }
        });

        chooseXButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    imgs = JPGParser.parseXTest();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        chooseYButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    labels = JPGParser.parseYTest();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        startTestButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // CODE FOR TEST BUTTON
                double[] prediction = new double[LABELS_SIZE];
                double[] example = new double[IMG_BYTES];
                double sumError = 0.;
                for (int i = 0; i < imgs.length; i++) {
                    for (int j = 0; j < IMG_BYTES; j++) {
                        example[j] = map(imgs[i][j]);
                    }
                    prediction = nn.predict(example);
                    int maxId = 0;
                    double maxNum = 0.0;
                    for (int j = 0; j < LABELS_SIZE; j++) {
                        if (maxNum < prediction[j]) {
                            maxNum = prediction[j];
                            maxId = j;
                        }
                    }
                    if (maxId != labels[i]) sumError++;
                }
                sumError /= labels.length;
                resultError.setText(String.valueOf(sumError));
            }
        });

        nextImgButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                byte[][] pixels = new byte[IMG_WIDTH][IMG_WIDTH];
                for (int i = 0; i < IMG_WIDTH; i++) {
                    for (int j = 0; j < IMG_WIDTH; j++) {
                        pixels[i][j] = (byte) i;
                    }
                }
                int imgNum = (int) (Math.random() * (imgs.length - 1));
                CustomImage customImg = new CustomImage(imgs[imgNum]);
                randomDidgitImg.setImage(customImg.getWritableImage());

                double[] example = new double[IMG_BYTES];

                for (int j = 0; j < IMG_BYTES; j++) {
                    example[j] = map(imgs[imgNum][j]);
                }
                double[] prediction = nn.predict(example);
                int maxId = 0;
                double maxNum = 0.0;
                for (int j = 0; j < LABELS_SIZE; j++) {
                    if (maxNum < prediction[j]) {
                        maxNum = prediction[j];
                        maxId = j;
                    }
                }
                recognizedLabel.setText(PAC_NAME[maxId]);

            }
        });
    }
}
