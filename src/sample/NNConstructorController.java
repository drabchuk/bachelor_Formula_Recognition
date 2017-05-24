package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import nn.perceptrons.DeepFeedForwardNN;

import java.io.File;
import java.io.IOException;


public class NNConstructorController {

    @FXML
    private TableColumn<TrainingView, String> nameColumn;

    @FXML
    private TableColumn<TrainingView, TextField> valueColumn;

    @FXML
    private TableView trainingTable;

    @FXML
    private TextField depthField;

    @FXML
    private Button saveNN;

    private ObservableList<TrainingView> trainingProperties = FXCollections.observableArrayList();

    private DeepFeedForwardNN nn;
    private int depth;


    @FXML
    public void initialize() {

        trainingTable.setEditable(true);

        nameColumn.setCellValueFactory(new PropertyValueFactory<TrainingView, String>("name"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<TrainingView, TextField>("value"));

        depthField.textProperty().addListener((observable, oldValue, newValue) -> {
            depth = Integer.parseInt(newValue);
            if (newValue.equals(""))
                newValue = "0";
            trainingProperties.removeAll();
            trainingProperties.clear();
            trainingProperties.add(new TrainingView("Input", ""));
            for (int i = 0; i < depth - 2; i++) {
                trainingProperties.add(new TrainingView("Size " + i, ""));
            }
            trainingProperties.add(new TrainingView("Output", ""));
            trainingTable.setItems(trainingProperties);

        });
        saveNN.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int[] sizes = new int[depth];

                for (int i = 0; i < depth; i++) {
                    sizes[i] = Integer.parseInt(valueColumn.getCellObservableValue(i).getValue().getText());
                }
                nn = new DeepFeedForwardNN(depth, sizes);
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
