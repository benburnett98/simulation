package cellsociety.view;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class InsufficientColorInput extends RuntimeException {

    public InsufficientColorInput() {
        super();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Not Enough Colors");
        alert.setHeaderText("Simulation does not have enough colors to run");
        alert.setContentText("This simulation requires more colors than you have inputted");
        Button back = new Button("Back to start");
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage myStage = new Stage();
                MainMenu myMenu = new MainMenu(myStage);
                myStage.setScene( myMenu.buildScene(900,900));
            }
        });
    }

}
