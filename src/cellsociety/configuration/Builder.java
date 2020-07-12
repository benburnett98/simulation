package cellsociety.configuration;

import cellsociety.cells.Cell;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

/**
 * @author Ben Burnett
 */
public class Builder {

    private static final String CSV_EXT = ".csv";
    private static final String ROOT = "data/";
    private Collection<Cell> myIntCells;
    private int xBound;
    private int yBound;

    /**
     * Builder reads the CSV files and turns them into a more accessible format
     * Use: called by Controller to get the initial cell states from the csv into the model
     * @param fileName - tells the program what file to select from the data branch
     * @throws FileNotFoundException - if the fileName doesnt correspond to a real file in the data folder
     */
    public Builder(String fileName) throws FileNotFoundException {
        String fileLocation = ROOT + fileName + CSV_EXT;
        Scanner readState = new Scanner(new File(fileLocation));
        String[] dimensions = readState.nextLine().split(",");
        xBound = Integer.parseInt(dimensions[0]);
        yBound = Integer.parseInt(dimensions[1]);
        myIntCells = new ArrayList<>();
        for (int y = yBound - 1; y >= 0; y--) {
            String[] nextYLevel = readState.nextLine().split(",");
            if (nextYLevel.length != xBound) {
                throw new InitialStateSizeException(fileName, xBound, "x");
            }
            for (int x = 0; x < xBound; x++) {
                myIntCells.add(new Cell(Integer.parseInt(nextYLevel[x]), x, y));
            }
            if (y == yBound - 1 && !readState.hasNext()) {
                throw new InitialStateSizeException(fileName, yBound, "y");
            }
        }
        if (readState.hasNext()) if (!readState.nextLine().equals(""))
            throw new InitialStateSizeException(fileName, yBound, "y");
        readState.close();
    }

    /**
     * The call the controller makes to actually get the cells
     * Use : in controller
     * @return a collection of cells (which know their own location)
     */
    public Collection<Cell> getCells() {
        return myIntCells;
    }

    /**
     * Passed into the initialization of a model type so that the model can use the collection of cells without
     * going out of bounds
     * Use : in Controller, passed to model
     * @return the x boundary of the grid
     */
    public int getXBound() {
        return xBound;
    }

    /**
     * Passed into the initialization of a model type so that the model can use the collection of cells without
     * going out of bounds
     * Use : in Controller, passed to model
     * @return the x boundary of the grid
     */
    public int getYBound() {
        return yBound;
    }
}
