package cellsociety.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ResourceBundle;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main extends Application {
    private ResourceBundle myResource = ResourceBundle.getBundle("cellsociety.view.resources.View");
    private static final int GAME_SIZE = 900;
    private static final int FRAMES_PER_SECOND = 60;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private Stage myStage;

    /**
     * Starts the application, displaying a MainMenu of options to play, about, or exit.
     *
     * @author Nicole Lindbergh
     * @author Benjamin Burnet
     * @author Celine Murugi
     *
     * @see MainMenu
     */

    @Override
    public void start(Stage primaryStage) throws Exception {
        myStage = primaryStage;
        myStage.setTitle(myResource.getString("MainTitle"));


        //this is to help switch scenes faster for debugging


        //MainMenu myMenu = new MainMenu(myStage);
        //Scene startingPage = myMenu.buildScene(GAME_SIZE,GAME_SIZE);
        //myStage.setScene(startingPage);
        //SimView sv = new SimView(myStage, 5);
        //Scene startPaggee = sv.buildScene(GAME_SIZE, GAME_SIZE);
        CustomMenu cm = new CustomMenu(myStage);
        Scene startPaggee = cm.buildScene(GAME_SIZE, GAME_SIZE);
        myStage.setScene(startPaggee);
        myStage.show();
    }


}
