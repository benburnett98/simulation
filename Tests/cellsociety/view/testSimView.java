package cellsociety.view;

import cellsociety.controller.Controller;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class testSimView extends DukeApplicationTest {
    private final static int GAME_SIZE = 600;
    // keep created scene to allow mouse and keyboard events
    private Scene myScene;
    private Stage myStage;
    private SimView mySV;

    @Override
    public void start(Stage primaryStage) throws Exception {
        myStage = primaryStage;
        mySV = new SimView(myStage, new Controller("GameOfLife","3Alive"));
        myScene = mySV.buildScene(GAME_SIZE, GAME_SIZE);
        myStage.show();
    }

    @Test
    public void testBuild() throws FileNotFoundException {
        assertEquals("SimView", mySV.getType());
    }

}
