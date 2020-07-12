package cellsociety.view;


import cellsociety.controller.Controller;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class PagesTest extends DukeApplicationTest {

    private final static int GAME_SIZE = 600;
    // keep created scene to allow mouse and keyboard events
    private Scene myScene;
    private Stage myStage;

    @Override
    public void start(Stage stage) {
        myStage = stage;
        MainMenu mm = new MainMenu(myStage);
        myScene = mm.buildScene(GAME_SIZE, GAME_SIZE);
        stage.show();
    }

    @Test
    public void testPageType() throws FileNotFoundException {
        MainMenu mm = new MainMenu(myStage);
        PlayDirectory pd = new PlayDirectory(myStage);
        SimView sv = new SimView(myStage, new Controller("GameOfLife","3Alive"));
        LoadingScreen ls = new LoadingScreen(myStage,2 );

        assertEquals("MainMenu", mm.getType());
        assertEquals("PLAYDIRECTORY", pd.getType());
        assertEquals("SimView", sv.getType());
        assertEquals("LOADINGSCREEN", ls.getType());
    }

    @Test
    public void testGetScene() throws IOException {
        MainMenu mm = new MainMenu(myStage);
        LoadingScreen ls = new LoadingScreen(myStage,2 );
        Scene pd = mm.getScene("PLAY");
        Scene ll = ls.getScene("MAIN MENU");
        assertNotNull(pd);
        assertNotNull(ll);
    }

    @Test
    public void testProperMenuBoxes() throws FileNotFoundException {
        PlayDirectory pd = new PlayDirectory(myStage);
        Scene s1 = pd.buildScene(600,600);
        Page.MenuBox m1 = (Page.MenuBox) s1.lookup("#pdMenuBox");
        LoadingScreen ls = new LoadingScreen(myStage,2 );
        Scene s2 = ls.buildScene(600,600);
        Page.MenuBox m2 = (Page.MenuBox) s2.lookup("#lsMenuBox");

        assertNotNull(s1);
        assertNotEquals(m1, m2);

    }
}
