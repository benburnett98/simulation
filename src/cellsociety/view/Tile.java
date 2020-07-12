package cellsociety.view;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

/**
 * @author Nicole Lindbergh
 */

public class Tile extends StackPane {

    private String[] myColors;
    private int x;
    private int y;
    private int Type;
    private int Size;

    private Rectangle myTile;

    /**
     * A Tile Constructor, to be animated in ViewCanvas
     *
     * @param x the x position of a Tile in the Scene a tile is made.
     * @param y the y position of a Tile in the Scene the tile is made.
     * @param Type the int value representing in the simulation what the Tile is supposed to be
     * @param size how big the Tile should be.
     *
     * @see Rectangle
     *
     * this constructor creates a StackPane that is colored depending on the Type input.
     * If the file is read properly, the Stack is NOT blue.
     *
     * @return Tile
     */

    public Tile(int x, int y, int Type, int size, String gameType) {
        setColors(gameType);
        this.x = x;
        this.y = y;
        this.Type = Type; //ex: 0, 1 , 2, 3, etc.
        this.Size = size;
        myTile = new Rectangle(size, size); //make a square baby!
        setTileColor();
        getChildren().add(myTile);
    }



    /*
     * REFACTOR INTO ENUM CLASS LATER
     */

    private void setColors(String gameType) {
        if (gameType.equals("GameOfLife")) {
            myColors =  new String[]{"GOLdefault-dead", "GOLdefault-alive", null};
        }
        if (gameType.equals("Percolation")) {
            myColors = new String[]{"Percdefault-closed", "Percdefault-open", "Percdefault-fill"};
        }
        if (gameType.equals("Segregation")) {
            myColors = new String[]{"SEGdefault-empty","SEGdefault-red", "SEGdefault-blue"};
        }
        if (gameType.equals("Fire")) {
            myColors = new String[]{"SOFdefault-unburnt", "SOFdefault-burning", "SOFdefault-burnt"};
        }
        if (gameType.equals("WaTor")) {
            myColors = new String[]{"PPdefault-water","PPdefault-shark","PPdefault-fish"};
        }
        if (gameType.equals("RockPaperScissors")) {
            myColors = new String[]{"RPSdefault-rock","RPSdefault-scissors","RPSdefault-paper"};
        }
    }

    /**
     * changes the tile's Type, so it can change colors accordingly.
     *
     * @return    void
     */

    public void changeTile(int newType) {
        Type = newType;
        setTileColor();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * set the Tile colors. It does this by filling the Rectangle
     * with a Color in an internal array of Colors set at the index
     * location of the Type.
     *
     * If for whatever reason the Resource bundle does not have a color associated with the
     * type of the Tile (the integer that correlates to a state), the Tile will turn black.
     * It will also turn black if the image associated with a Tile is not available. 
     *
     * @return    void
     */

    private void setTileColor() throws InsufficientColorInput {
        try {
            myTile.setId(myColors[Type]);
            if (myColors[Type].equals(null)) {
                throw new InsufficientColorInput();
            }
        } catch (InsufficientColorInput e) {
            myTile.setId("deadCell");
        }
    }
}
