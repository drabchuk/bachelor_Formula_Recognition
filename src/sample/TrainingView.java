package sample;

import javafx.scene.control.TextField;

import javax.xml.soap.Text;

public class TrainingView {

    private String name;
    private TextField value;


    public String getName() {
        return name;
    }

    public TextField getValue() {
        return value;
    }

    public TrainingView(String name, String value) {

        this.name = name;
        this.value = new TextField(value);
    }
}
