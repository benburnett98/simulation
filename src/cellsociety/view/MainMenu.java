package cellsociety.view;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * @author Nicole Lindbergh
 */

public class MainMenu extends Page{

    private ResourceBundle myResource = ResourceBundle.getBundle("cellsociety.view.resources.View");
    private static final String STYLESHEET = "resources/main.css";

    private Stage myStage;

    /**
     * Constructs a basic Main Menu Page, extended from the abstract Page class.
     *
     * @param primaryStage Pages pass back and force the stage and animate them accordingly.
     * @return    MainMenu
     */

    public MainMenu(Stage primaryStage) {
        super(primaryStage);
        myStage = primaryStage;
    }

    /**
     * Builds a scene with a MenuBox of four items (PLAY, RULES, STORE, and EXIT) that the
     * user can click on. It also sets a title and generates a background.
     *
     * @param height height of the window.
     * @param width width of the window.
     *
     * @return    Page
     */

    @Override
    public Scene buildScene(int height, int width) {

        Pane myRoot = new Pane();
        myRoot.setPrefSize(width,height);

        Text title = new Text(myResource.getString("MainTitle"));
        title.setId("Welcome");
        MenuBox myMenuBox = new MenuBox(myResource.getString("Play"), myResource.getString("Custom"),
                myResource.getString("About"), myResource.getString("Exit"));

        myRoot.getChildren().addAll(title, myMenuBox);
        Scene myScene = new Scene(myRoot);
        myScene.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());

        return myScene;
    }

    /**
     * Gets Page Command.
     *
     * @return    String
     */

    @Override
    String getCommand(){return "MAIN MENU";}

    /**
     * gets type of Page, returns "MainMenu"
     *
     * @return    String
     */

    @Override
    public String getType() {
        return "MainMenu";
    }

    /**
     * switches scenes to whatever Scene the user has decided to pick using inherited methods
     * from Page.
     *
     * @param name name of the selected Page.
     * @return    Page
     */

    @Override
    Scene gotoScene(String name) throws IOException {
        return getScene(name);
    }

}
