package cellsociety.view;

import cellsociety.controller.Controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * @author Nicole Lindbergh
 */

public class SimView extends Page {
    private ResourceBundle myResource = ResourceBundle.getBundle("cellsociety.view.resources.View");

    private Stage myStage;
    private Scene myScene;
    private Pane myRoot;
    private Slider slider;
    private int FRAMES_PER_SECOND;
    private double SECOND_DELAY;
    private Controller myControl;
    private Timeline myAnimation;
    private ViewCanvas myCanvas;
    private boolean isPlaying;
    private boolean colorDefault;
    private HashMap<Integer, Color> myColors;


    /**
     * Constructs a basic Page. All animated Pages are extended from this class.
     * @param primaryStage Pages pass back and force the stage and animate them accordingly
     * @return Page
     */

    public SimView(Stage primaryStage, Controller control) {
        super(primaryStage);
        myStage = primaryStage;
        myControl = control;
    }

    /**
     * Builds the SimView Screen. Creates a title, a MenuBar with two items in it,
     * a slider for changing animation speed, a Menu for choosing other layouts,
     * and a series of Tiles that change color based on internal ints, representing
     * "alive" or "dead".
     * @param height the height of the program window
     * @param width the width of the program window.
     * @return    double
     */

    @Override
    public Scene buildScene(int height, int width) throws FileNotFoundException {
        myRoot = new Pane();
        myRoot.setPrefSize(width,height);

        MenuBox myMenuBox = new MenuBox(myResource.getString("Menu"), myResource.getString("Exit"));
        myMenuBox.setId("MenuBox");

        myCanvas = new ViewCanvas(myControl);
        myCanvas.setId("MyCanvas");

        slider = new Slider(1, 5, 1);
        slider.setId("slider");
        Button Pause = buildPauseButton();
        Button save = buildSave();

        Rectangle rect = new Rectangle(500,150, Color.GAINSBORO);
        rect.setId("BackgroundPane");
        YouAreViewing view = new YouAreViewing(myControl);

        myRoot.getChildren().addAll(myMenuBox,
                Pause, myCanvas, view, rect, slider, save);

        setAnimation();

        myScene = new Scene(myRoot, width,height);
        myScene.getStylesheets().addAll(this.getClass().getResource("resources/main.css")
                .toExternalForm());
        return myScene;
    }

    /**
     * Steps through animation, performing logic on cells for each call and
     * changing Tiles accordingly.
     * @param elapsedTime how much time has passed, determined by Timeline.
     * @see Tile all tiles change each time.
     * @see Text stays static
     * @see Page change
     * @return    null
     */


    void step(double elapsedTime) {
        myCanvas.update();
    }

    private void setAnimation() {
        FRAMES_PER_SECOND = 1;
        SECOND_DELAY = 1.0/ FRAMES_PER_SECOND;
        isPlaying=true;

        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY));
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(frame);
        myAnimation.play();
        myAnimation.rateProperty().bind(slider.valueProperty());
        colorDefault = false;
    }

    private Button buildSave() {
        Button save = new Button(myResource.getString("Saved"));
        save.setId("SaveButton");
        save.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dialog<String[]> dialog = new Dialog<>();
                dialog.setTitle(myResource.getString("SaveTitle"));
                dialog.setHeaderText(myResource.getString("Enter"));

                ButtonType save = new ButtonType(myResource.getString("Save"), ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(save, ButtonType.CANCEL);

                TextField title = new TextField(myResource.getString("Simtitle"));
                title.setPromptText("Title");

                TextField saveloc = new TextField(myResource.getString("SaveLoc"));
                saveloc.setPromptText(myResource.getString("SaveLoc"));

                TextField author = new TextField(myResource.getString("Simauthor"));
                author.setPromptText("Author");

                TextField description = new TextField(myResource.getString("Simdetail"));
                description.setPromptText(myResource.getString("Describe"));

                VBox texts = new VBox(title, author, description, saveloc);
                dialog.getDialogPane().setContent(texts);

                dialog.setResultConverter(dialogButton-> {
                    if (dialogButton == save) {
                        return new String[]{ saveloc.getText(), title.getText(), author.getText(), description.getText()};
                    }
                    return null;
                });

                Optional<String[]> result = dialog.showAndWait();
                if (result.isPresent()) {
                    String[] g = result.get();
                    try {
                        myControl.save(g[0],g[1],g[2],g[3]);
                    } catch (IOException e) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle(myResource.getString("InvalidFile"));
                        alert.setHeaderText(myResource.getString("InvalidFile"));
                        alert.setContentText(myResource.getString("Try"));
                        //throw a "file already exists" exception
                    }
                }


            }
        });
        return save;
    }

    private Button buildPauseButton() {
        Button Pause = new Button("Pause");
        Pause.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (isPlaying) {
                    isPlaying = false;
                    myAnimation.stop();
                } else {
                    myAnimation.play();
                    isPlaying = true;
                }
            }
        });
        return Pause;
    }

    @Override
    public String getType() {
        return "SimView";
    }

    /**
     * Returns the command function of the page.
     * @return    String
     */

    @Override
    String getCommand() {
        return "SimView";
    }

    /**
     * goes to Scene, used in MenuBoxItems.
     * @return    Scene
     */

    @Override
    Scene gotoScene(String name) throws IOException {
        return getScene(name);
    }

}
