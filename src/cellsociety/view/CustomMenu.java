package cellsociety.view;

import cellsociety.configuration.SavePullHelper;
import cellsociety.controller.Controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Nicole Lindbergh
 */

public class CustomMenu extends Page {

    private ResourceBundle myResource = ResourceBundle.getBundle("cellsociety.view.resources.View");
    private Stage myStage;
    private Scene myScene;
    private Pane myRoot;
    private VBox gameButtons;
    private String layoutSelectedByUser;
    private String gameSelectedByUser;
    private Slider slider;
    private int FRAMES_PER_SECOND;
    private double SECOND_DELAY;
    private Controller myControl;
    private Controller myNextPage;
    private LaunchButton launchButton;
    private Timeline myAnimation;
    private ViewCanvas myCanvas;
    private boolean isPlaying;
    private boolean colorDefault;
    private HashMap<Integer, Color> myColors;

    /**
     * Constructs a basic Page. All animated Pages are extended from this class.
     *
     * @param primaryStage Pages pass back and force the stage and animate them accordingly.
     * @return Page
     */
    public CustomMenu(Stage primaryStage) {
        super(primaryStage);
        myStage = primaryStage;
    }

    @Override
    public Scene buildScene(int height, int width) throws FileNotFoundException {
        myRoot = new Pane();
        myRoot.setPrefSize(width,height);

        myControl = new Controller("GameOfLife", "GOLLayout1");

        launchButton = new LaunchButton("L A U N C H", myStage);
        launchButton.setId("LaunchButton");
        launchButton.setDisable(true);

        Text vBoxTitle = new Text(myResource.getString("SimTypeTitle"));
        vBoxTitle.setId("MainTitle");
        MenuBox myMenuBox = new MenuBox(myResource.getString("Menu"), myResource.getString("Exit"));
        makeGameButtons(myMenuBox);

        HBox ColorConfigs = getCustomColorButtons();
        ColorConfigs.setId("CS-ColorConfigs");

        myCanvas = new ViewCanvas(myControl);
        myCanvas.setId("MyCanvas");

        VBox myLayouts = getFileOptionsButtons();
        myLayouts.setId("CS-Layouts");

        slider = new Slider(1, 5, 1);
        slider.setId("slider");
        Button Pause = buildPauseButton();
        Button save = buildSave();

        Rectangle rect = new Rectangle(500,150, Color.GAINSBORO);
        rect.setId("BackgroundPane");
        YouAreViewing view = new YouAreViewing(myControl);

        myRoot.getChildren().addAll(myLayouts,launchButton, myMenuBox,
                ColorConfigs, Pause, myCanvas, view, rect, slider, vBoxTitle, save);

        setAnimation();

        myScene = new Scene(myRoot, width,height);
        myScene.getStylesheets().addAll(this.getClass().getResource("resources/main.css")
                .toExternalForm());
        return myScene;
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

    //implemented
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

    //implemented
    private HBox getCustomColorButtons() {
        myColors = new HashMap();
        ColorPicker PickCustomColor1 = new ColorPicker();
        PickCustomColor1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!colorDefault) {
                    myColors.put(0,PickCustomColor1.getValue());
                    launchButton.setDisable(false);
                    launchButton.setStyle(myColors);
                }
            }
        });
        ColorPicker CustomColor2 = new ColorPicker();
        CustomColor2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!colorDefault) {
                    myColors.put(1,CustomColor2.getValue());
                    launchButton.setDisable(false);
                    launchButton.setStyle(myColors);
                }
            }
        });
        ColorPicker Picker3 = new ColorPicker();
        Picker3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!colorDefault) {
                    myColors.put(2,Picker3.getValue());
                    launchButton.setDisable(false);
                    launchButton.setStyle(myColors);
                }
            }
        });
        return new HBox(PickCustomColor1, CustomColor2, Picker3);
    }

    //TODO: implement with Ben
    private VBox getFileOptionsButtons() {
        FileChooser choosefile = new FileChooser();
        Button chooseCustomFile = new Button(myResource.getString("Package"));
        chooseCustomFile.setOnAction(e-> {
            File myFile = choosefile.showOpenDialog(myStage);
            /*try {
                //myControl = new Controller(myFile);
                String s = myControl.getText();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } */
            ResourceBundle myResource = ResourceBundle.getBundle(myFile.getName());
        });
        ToggleButton DefaultButton = new ToggleButton(myResource.getString("ToggleDefault"));
        DefaultButton.setId("ToggleButton");
        DefaultButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (DefaultButton.isSelected()) {
                    DefaultButton.setId("selected");
                    colorDefault = true;
                    launchButton.setDisable(false);
                    launchButton.setStyle(myColors);
                }
                else {
                    DefaultButton.setId("ToggleButton");
                    colorDefault = false;
                    launchButton.setDisable(false);
                    launchButton.setStyle(myColors);
                }
            }
        });
        return new VBox(chooseCustomFile, DefaultButton);
    }

    //implemented
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

    //PERFECT! BEAUTIFUL! SEXY!!
    void step(double elapsedTime) {
        myCanvas.update();
    }

    //TODO
    private void makeGameButtons(MenuBox myBox){
        launchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    launchButton.setSim(new Controller(gameSelectedByUser, layoutSelectedByUser));
                    System.out.print("click");
                } catch (FileNotFoundException e) {
                    System.out.println("hell no");
                }
                finally {
                    //throw customerror
                }
            }
        });

        Button GOL = new Button (myResource.getString("GOL"));
        GOL.setId("button");
        GOL.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    handleUserGameChoice("GameOfLife");
                    gameSelectedByUser = "GameOfLife";
                } catch (IOException e) {
                    System.out.println("Select a Valid Game Type");
                }
            }
        });
        Button percolation = new Button (myResource.getString("Perc"));
        percolation.setId("button");
        percolation.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gameSelectedByUser = "Percolation";
                try {
                    handleUserGameChoice("Percolation");
                } catch (IOException e) {
                    System.out.println("Select a Valid Game Type");
                }
            }
        });
        Button RPS = new Button (myResource.getString("RPS"));
        RPS.setId("button");
        RPS.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gameSelectedByUser = "RockPaperScissors";
                try {
                    handleUserGameChoice("RockPaperScissors");
                } catch (IOException e) {
                    System.out.println("Select a Valid Game Type");
                }
            }
        });
        Button spread = new Button (myResource.getString("Spread"));
        spread.setId("button");
        spread.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gameSelectedByUser = "Fire";
                try {
                    handleUserGameChoice("Fire");
                } catch (IOException e) {
                    System.out.println("Select a Valid Game Type");
                }
            }
        });
        Button seg = new Button (myResource.getString("Seg"));
        seg.setId("button");
        seg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gameSelectedByUser = "Segregation";
                try {
                    handleUserGameChoice("Segregation");
                } catch (IOException e) {
                    System.out.println("Select a Valid Game Type");
                }
            }
        });
        Button predPrey = new Button (myResource.getString("Pred-Prey"));
        predPrey.setId("button");
        predPrey.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gameSelectedByUser = "PredatorPrey";
                try {
                    handleUserGameChoice("PredatorPrey");
                } catch (IOException e) {
                    System.out.println("Select a Valid Game Type");
                }
            }
        });
        myBox.addButtons(GOL, percolation, RPS, spread, seg, predPrey);
        myBox.setId("MenuBox");
    }

    //TODO: @nicol refactor this
    //would prevent game crashing in case user forgot to select one of the files
    private void setChoiceBoxInitialValue (ChoiceBox<String> choiceBox, String gameType){
        if (gameType.equals("GameOfLife")){
            choiceBox.setValue("GOLLayout1");
        }
        if (gameType.equals("Percolation")){
            choiceBox.setValue("PercLayout1");
        }
        if (gameType.equals("RockPaperScissors")){
            choiceBox.setValue("RPSLayout1");
        }
        if (gameType.equals("Fire")){
            choiceBox.setValue("SpreadLayout1");
        }
        if (gameType.equals("Segregation")){
            choiceBox.setValue("SegLayout1");
        }
        if (gameType.equals("PredatorPrey")){
            choiceBox.setValue("PredPreyLayout1");
        }
    }


    //code by Celine
    private void handleUserGameChoice(String gameType) throws IOException {
        SavePullHelper loadProperties = new SavePullHelper(gameType);
        ChoiceBox<String> gameSpecifiedLayouts = new ChoiceBox<>();
        gameSpecifiedLayouts.setId("choiceBox");
        gameSpecifiedLayouts.getItems().addAll((loadProperties.getAllProperties()));
        setChoiceBoxInitialValue(gameSpecifiedLayouts, gameType);
        myRoot.getChildren().add(gameSpecifiedLayouts);
        layoutSelectedByUser = gameSpecifiedLayouts.getSelectionModel().getSelectedItem();

        if (!gameType.equals("GameOfLife")) {
            changeCanvas(gameType);
        }

    }

    private void changeCanvas(String gameType) throws FileNotFoundException {
        myRoot.getChildren().remove(myScene.lookup("MyCanvas"));
        if (gameType.equals("Percolation")) {
            myControl = new Controller(gameType, "PercLayout1");
        }
        if (gameType.equals("RockPaperScissors")) {
            myControl = new Controller(gameType, "RPSLayout1");
        }
        if (gameType.equals("Fire")) {
            myControl = new Controller(gameType, "SpreadLayout1");
        }
        myCanvas = new ViewCanvas(myControl);
        myCanvas.setId("MyCanvas");
        myRoot.getChildren().add(myCanvas);
    }

    @Override
    String getType() {
        return "CustomMenu";
    }
    @Override
    String getCommand() {
        return "CustomMenu";
    }
    @Override
    Scene gotoScene(String name) throws IOException {
        return getScene(name);
    }
}
