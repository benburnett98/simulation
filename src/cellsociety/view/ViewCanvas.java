package cellsociety.view;

import cellsociety.controller.Controller;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * @author Nicole Lindbergh
 */
public class ViewCanvas extends Pane {

    private Canvas canvas;
    private Affine myGridBorder;
    private Controller myControl;

    private final int CANVAS_WIDTH = 500;
    private final int CANVAS_HEIGHT = 500;
    private int TILE_WIDTH;
    private int TILE_HEIGHT;
    private ArrayList<Tile> myTiles;

    /**
     * This constructor makes a Pane that holds a JavaFx canvas that the user can interact with using mouse clicks.
     * The pane also holds an affine, which is a type of grid. This constructor applies CSS styling as well.
     *
     * @return    ViewCanvas
     */

    public ViewCanvas(Controller controller) throws FileNotFoundException {

        this.canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        this.canvas.setOnMousePressed(this::handledraw);
        this.canvas.setOnMouseDragged(this::handledraw);

        myControl = controller;

        TILE_WIDTH = CANVAS_WIDTH/myControl.getXBound();
        TILE_HEIGHT = CANVAS_HEIGHT/myControl.getYBound();

        myGridBorder = new Affine();
        myGridBorder.appendScale(TILE_WIDTH, TILE_HEIGHT);

        this.getStylesheets().addAll(this.getClass().getResource("resources/main.css").toExternalForm());
        this.draw();
        this.getChildren().addAll(this.canvas);
    }

    /**
     * This method, called once, creates an ArrayList of Tiles that are responsible for their x and y positions, their
     * type, and their gameType. If the gameType ever changes, the color of the tiles would automatically change as well.
     *
     * @return    void
     */

    public void draw() {
        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        myTiles = new ArrayList<>();
        for (int x = 0; x < myControl.getXBound(); x++) {
            for (int y = 0; y < myControl.getYBound(); y++) {

                int num = myControl.getValueAtLoc(x,y);

                Tile rect = new Tile(x,y,num, TILE_WIDTH, myControl.getGameType());
                rect.setTranslateX(x*TILE_WIDTH);
                rect.setTranslateY(y*TILE_HEIGHT);

                this.getChildren().add(rect);
                myTiles.add(rect);
            }
        }
    }

    /**
     * This method is called in the step function. It asks the Controller to apply the simulation rules once; then,
     * it changes the tiles, stored in the ArrayList made in .draw(), based on the new information.
     *
     * @see Tile changes
     */
    public void update() {
        myControl.change();
        for (Tile t : myTiles) {
            t.changeTile(myControl.getValueAtLoc(t.getX(),t.getY()));
        }
    }

    /**
     * This method is called whenever a user clicks on the canvas in the handledraw() method. It calls the Controller
     * to change the cell at X and Y positions of the _grid itself_--this integer does not correlate to any pixel on the
     * screen.
     *
     * @param changeX the X position of the Mouseclick event _relative to the grid itself_
     * @param changeY the Y position of the Mouseclick event _relative to the grid itself_
     *
     * @see Tile change based on user input
     */

    private void updateTile(int changeX, int changeY) {
        myControl.changeCell(changeX,changeY);
        for (Tile t : myTiles) {
            if (t.getX()==changeX && t.getY()==changeY) {
                t.changeTile(myControl.getValueAtLoc(t.getX(),t.getY()));
            }
        }
    }

    /**
     * This method takes the MouseEvent and attempts to change the type of the tile accordingly.
     *
     * First, it takes the X and Y position of the event. Then, it passes that information to the affine, which
     * gives back a Point2D that correlates to where in the affine the mouse clicked. For example, if a user clicked on a pixel
     * that correlated to the 1st tile in the 2nd row, the Point2D would translate the X position of the event to 1, and the Y
     * position to 2, because the affine has an internal grid it stores.
     *
     * The method then passes this new X and Y position on and updates the Tile at this location.
     *
     * @see Tile change.
     */

    private void handledraw(MouseEvent event) {
        double clickX = event.getX();
        double clickY = event.getY();
        try{
            Point2D Mouseclick = myGridBorder.inverseTransform(clickX, clickY);
            int changeX = (int) Mouseclick.getX();
            int changeY = (int) Mouseclick.getY();

            this.updateTile(changeX, changeY);

        } catch (NonInvertibleTransformException e) {
            e.printStackTrace(); //make custom exception here for outside of affine.
        }

    }

}



