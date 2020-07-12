package cellsociety.view;

import cellsociety.controller.Controller;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.ResourceBundle;

public class LaunchButton extends Button {

    private ResourceBundle myResource = ResourceBundle.getBundle("cellsociety.view.resources.View");
    private boolean haveSim;
    private boolean haveStyle;
    private Stage stage;
    private Controller myControl;
    private HashMap<Integer, Color> myColors;

    public LaunchButton(String text, Stage myStage) {
        super(text);
        stage = myStage;
        this.setOnMouseClicked(mouseEvent -> {
            try {
                handleClick(mouseEvent);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        haveSim = false;
        haveStyle = false;
    }

    public void setSim(Controller control) {
        haveSim = true;
        myControl = control;
    }
    public void setStyle(HashMap<Integer, Color> hm) {
        haveStyle = true;
        myColors = hm;
    }

    public void handleClick(MouseEvent mouseEvent) throws FileNotFoundException {
        if (haveSim && haveStyle) {
            stage.setScene(new SimView(stage, myControl).buildScene(900,900));
        }
        else if (!haveStyle){
            Alert noStyle = new Alert(Alert.AlertType.INFORMATION);
            noStyle.setTitle(myResource.getString("MoreInfo"));
            noStyle.setHeaderText(myResource.getString("NoStyle"));
            noStyle.setContentText(myResource.getString("PreferStyle"));
            noStyle.showAndWait();
        }
        else if (!haveSim) {
            Alert noStyle = new Alert(Alert.AlertType.INFORMATION);
            noStyle.setTitle(myResource.getString("SelectSim"));
            noStyle.setHeaderText(myResource.getString("NoSim"));
            noStyle.setContentText(myResource.getString("PreferSim"));
            noStyle.showAndWait();
        }

    }
}
