package cellsociety.view;

import cellsociety.controller.Controller;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ResourceBundle;

/**
 * @author Nicole Lindbergh
 */

public class LoadingScreen extends Page {
    private ResourceBundle myResource = ResourceBundle.getBundle("cellsociety.view.resources.View");
    private static final String STYLESHEET = "main.css";
    private Stage myStage;
    private int LAYOUT_NO;
    private static final int GAME_SIZE = 600;
    private String BACKGROUND_ADDRESS = "sunnyday.png";
    private ImageView image;

    /**
     * Constructs a basic Page. All animated Pages are extended from this class.
     * @param primaryStage Pages pass back and force the stage and animate them accordingly.
     * @return Page
     */
    public LoadingScreen(Stage primaryStage, int chooseFile) {
        super(primaryStage);
        myStage = primaryStage;
        LAYOUT_NO = chooseFile;
        myStage.getScene().getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());
    }


    @Override
    public Scene buildScene(int height, int width) throws FileNotFoundException {
        Pane root = new Pane();

        root.setPrefSize(GAME_SIZE, GAME_SIZE);

        Text title = new Text(myResource.getString("LoadingTitle"));
        title.setX(GAME_SIZE/5);
        title.setY(GAME_SIZE/2+GAME_SIZE/8);
        title.setStyle("-fx-font-size: 50pt; -fx-font-family: 'Times New Roman'; -fx-font-style: italic; -fx-text-fill: whitesmoke");
        title.setId("lsTitle");
        MenuBox myBox = new MenuBox(myResource.getString("NewSim"));
        myBox.setId("lsMenuBox");
        root.getChildren().addAll(title, myBox);
        return new Scene(root);
    }

    /**
     * Returns the type of the page.
     * @return    String
     */


    @Override
    public String getType() {
        return "LOADINGSCREEN";
    }

    /**
     * Returns the command function of the page.
     * @return    String
     */

    @Override
    String getCommand() {
        return "LOADINGSCREEN";
    }

    /**
     * goes to Scene, used in MenuBoxItems.
     * @return    Scene
     */

    @Override
    Scene gotoScene(String name) throws FileNotFoundException {
        SimView ss = new SimView(myStage, new Controller("GameOfLife","GOLLayout1"));
        Scene myScene = ss.buildScene(GAME_SIZE, GAME_SIZE);
        return myScene;
    }
}
