package sample;

import static classes.Const.*;

import classes.JPGParser;
import classes.NeuralReader;
import classes.Parser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import nn.perceptrons.DFFNNTrainer;
import nn.perceptrons.DeepFeedForwardNN;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import static nn.data.ImgBrightnessMapper.map;

public class TrainingWindowController {

    @FXML
    private Button chooseButton;

    @FXML
    private Button chooseXButton;

    @FXML
    private Button chooseYButton;

    @FXML
    private Button loadTrainButton;

    @FXML
    private Button saveNN;

    @FXML
    private TextField criteriaField;

    @FXML
    private TextField stepLengthField;

    @FXML
    private TextField maxStepsField;

    @FXML
    private TextField maxTimeField;

    @FXML
    private Label loadXStatus;

    @FXML
    private Label loadYStatus;

    @FXML
    private Label learningStatus;

    private ObservableList<TrainingView> trainingProperties = FXCollections.observableArrayList();

    private DeepFeedForwardNN nn;
    private byte[][] imgs;
    private byte[] labels;
    private int depth;
    private int[] sizes;
    private double criteria;
    private double stepLength;
    private int maxSteps;
    private int maxTime;


    @FXML
    public void initialize() {

        chooseButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open NN File");
                File file = fileChooser.showOpenDialog(new Stage());
                nn = (DeepFeedForwardNN) NeuralReader.getReader().start(file);
            }
        });

        criteriaField.textProperty().addListener((observable, oldValue, newValue) -> {
            criteria = Double.parseDouble(newValue);
            if (newValue.equals(""))
                newValue = "0";

        });

        stepLengthField.textProperty().addListener((observable, oldValue, newValue) -> {
            stepLength = Double.parseDouble(newValue);
            if (newValue.equals(""))
                newValue = "0";

        });

        maxStepsField.textProperty().addListener((observable, oldValue, newValue) -> {
            maxSteps = Integer.parseInt(newValue);
            if (newValue.equals(""))
                newValue = "0";

        });

        maxTimeField.textProperty().addListener((observable, oldValue, newValue) -> {
            maxTime = Integer.parseInt(newValue);
        });

        chooseXButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    imgs = JPGParser.parseX();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        chooseYButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    labels = JPGParser.parseY();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        loadTrainButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                learningStatus.setText("Learning started");

                double[][] lbls = new double[labels.length][LABELS_SIZE];
                for (int i = 0; i < labels.length; i++) {
                    lbls[i][labels[i]] = 1.;
                }
                double[][] images = new double[imgs.length][IMG_BYTES];
                for (int i = 0; i < imgs.length; i++) {
                    for (int j = 0; j < IMG_BYTES; j++) {
                        images[i][j] = map(imgs[i][j]);
                    }
                }
                DFFNNTrainer trainer = new DFFNNTrainer(nn, images, lbls);
                //trainer.trainTimeBound(maxSteps, stepLength, maxTime);
                trainer.trainSimple(maxSteps, (int) stepLength, maxTime);
                System.out.println("Learning completed.");
                learningStatus.setText("Learning completed.");

            }
        });
        saveNN.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save File");
                File file =  fileChooser.showOpenDialog(new Stage());
                try {
                    nn.saveWeights(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
