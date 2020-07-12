package cellsociety.view;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * @author Nicole Lindbergh
 */

public class PlayDirectory extends Page {

    private ResourceBundle myResource = ResourceBundle.getBundle("cellsociety.view.resources.View");
    private static final String STYLESHEET = "resources/main.css";
    private Stage myStage;

    /**
     * Constructs a basic Page. All animated Pages are extended from this class.
     * @param primaryStage Pages pass back and force the stage and animate them accordingly.
     * @return Page
     */
    public PlayDirectory(Stage primaryStage) {
        super(primaryStage);
        myStage = primaryStage;
        myStage.getScene().getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
    }

    @Override
    public Scene buildScene(int height, int width) {
        Pane root = new Pane();

        root.setPrefSize(height, width);

        Text title = new Text(myResource.getString("SimTypeTitle"));
        title.setX(width/4);
        title.setY(height/6);
        title.setStyle(" -fx-font-size: 40pt; -fx-text-fill: greenyellow; -fx-font-family: 'Times New Roman';");
        title.setId("pdTitle");
        MenuBox myMenuBox = new MenuBox(myResource.getString("GOL"), myResource.getString("Perc"),
                myResource.getString("RPS"), myResource.getString("Spread"), myResource.getString("Seg"),
                myResource.getString("Pred-Prey"), myResource.getString("Menu"), myResource.getString("Exit"));
        myMenuBox.setTranslateX(width/4); //middle of everything
        myMenuBox.setTranslateY(height/3); //middle of everything, set to final static int when decided
        myMenuBox.setId("pdMenuBox");

        root.getChildren().addAll(title, myMenuBox);
        return new Scene(root);
    }

    @Override
    public String getType() {
        return "PLAYDIRECTORY";
    }

    @Override
    String getCommand() {
        return "PLAYDIRECTORY";
    }

    @Override
    Scene gotoScene(String name) throws IOException {
        return getScene(name);
    }
}
