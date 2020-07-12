package cellsociety.model;

import cellsociety.cells.Cell;

import java.util.ArrayList;
import java.util.Collection;


/**
 * @author Ben Burnett
 */
public abstract class SimModel {

    private Cell[][] myCells;

    /**
     * Creates an instance of the abstract class for all of the models in the simulation
     * @param cellList - a collection of cells that are the initials cells for the grid
     * @param xBound - the x boundary of the grid
     * @param yBound - the y boundary of the grid
     */
    public SimModel(Collection<Cell> cellList, int xBound, int yBound) {
        myCells = new Cell[yBound][xBound];
        for (Cell loadingCell : cellList) {
            myCells[loadingCell.getX()][loadingCell.getY()] = loadingCell;
        }
    }

    /**
     * Runs through all of the cells twice and updates them based on the logic specified in the subclass
     * and their neighbors
     * Use : called by the controller when the view wants to make another step in the animation
     */
    public void change() {
        for (int x = 0; x < myCells.length; x++) {
            for (int y = 0; y < myCells[x].length; y++) {
                logicOnCell(myCells[x][y]);
            }
        }
        for (int x = 0; x < myCells.length; x++) {
            for (int y = 0; y < myCells[x].length; y++) {
                myCells[x][y].change();
            }
        }
    }

    /**
     * The logic of each simulation specified in the respective subclass
     * Use: helper method for change()
     * Sets the next instance variable of the Cell to reflect that logic
     * @param update - the cell that will have its .next changed
     */
    protected abstract void logicOnCell(Cell update);

    /**
     * Updates a specific cell based on logic specified for the simulation - doesn't affect any of
     * the cells around it until the next time change is called
     * Use : called by Controller whenever the User clicks on a specific cell
     * @param x - the x coordinate of the cell
     * @param y - the y coordinate of the cell
     * @return the new value of the cell
     */
    public int changeCell(int x, int y) {
        Cell updated = myCells[x][y];
        clickedOnLogic(updated);
        updated.change();
        return updated.getCurrent();
    }

    /**
     * The logic for how a specific cell will act when clicked on
     * each simulation specified in the respective subclass
     * Sets the next instance variable of the Cell to reflect that logic
     * Use : helper method for changeCell()
     * @param current
     */
    protected abstract void clickedOnLogic(Cell current);

    /**
     * gets the value of a specific cell for the view to update the grid shown to the user
     * Use : called by view through the controller on every cell in the grid
     * @param x - the x coordinate of the cell called
     * @param y - the y coordinate of the cell called
     * @return the value of the cell at the specified point
     */
    public int getValueAtLoc(int x, int y) {
        return myCells[x][y].getCurrent();
    }

    /**
     * Finds all eight neighbors of the cell on a discrete plane
     * @param x - x coordinate of the cell you're finding the neighbors of
     * @param y - y coordinate of the cell you're finding the neighbors of
     * @return a collection of cells that are next to the cell in question
     */
    protected Collection<Cell> findMoorePlane(int x, int y) {
        return neighborsHelper(x, y, 8, 0);
    }

    /**
     * Finds all eight neighbors of the cell on a torus (wraps around)
     * @param x - x coordinate of the cell you're finding the neighbors of
     * @param y - y coordinate of the cell you're finding the neighbors of
     * @return a collection of cells that are next to the cell in question
     */
    protected Collection<Cell> findMooreTorus(int x, int y) {
        return neighborsHelper(x, y, 8, 1);
    }

    /**
     * Finds all four (on a cross) neighbors of the cell on a discrete plane
     * @param x - x coordinate of the cell you're finding the neighbors of
     * @param y - y coordinate of the cell you're finding the neighbors of
     * @return a collection of cells that are next to the cell in question
     */
    protected Collection<Cell> findCardinalPlane(int x, int y) {
        return neighborsHelper(x, y, 4, 0);
    }

    /**
     * Finds all 4 (on a cross) neighbors of the cell on a torus (wraps around)
     * @param x - x coordinate of the cell you're finding the neighbors of
     * @param y - y coordinate of the cell you're finding the neighbors of
     * @return a collection of cells that are next to the cell in question
     */
    protected Collection<Cell> findCardinalTorus(int x, int y) {
        return neighborsHelper(x, y, 4, 1);
    }

    /**
     * Helper class for finding neighbors to a cell for multiple different selection criteria and bounds cases
     * @param x - x coordinate of the cell you're finding the neighbors of
     * @param y - y coordinate of the cell you're finding the neighbors of
     * @param exclusion - what type of neighbors you're selecting for
     * @param boundsCase - how youre handling the bounds (torus, discrete plane etc)
     * @return a collection of cells representing the near
     */
    private Collection<Cell> neighborsHelper(int x, int y, int exclusion, int boundsCase) {
        Collection<Cell> neighbors = new ArrayList<>();
        for (int xMod = -1; xMod <= 1; xMod++) {
            int xLoc = bounds(x + xMod, myCells.length, boundsCase);
            if (xLoc >= myCells.length || xLoc < 0) continue;
            for (int yMod = -1; yMod <= 1; yMod++) {
                int yLoc = bounds(y + yMod, myCells[y].length, boundsCase);
                if (yLoc >= myCells[x].length || yLoc < 0) continue;
                if (whichToExlude(xMod, yMod, exclusion)) continue;
                Cell[] xGrid = myCells[xLoc];
                Cell neighbor = xGrid[yLoc];
                neighbors.add(neighbor);
            }
        }
        return neighbors;
    }

    /**
     * Helper method for determining how neighbors that go off the side of the 2d grid are handled,
     * depending on the type of grid we're working with (torus, discrete plane etc)
     * @param coordinate - the actual coodinate
     * @param bound - the bound for that coordinate
     * @param boundsCase - the type of grid we're working with (0 for discrete plane, 1 for torus)
     * @return the updated coordinate
     */
    private int bounds(int coordinate, int bound, int boundsCase) {
        if (boundsCase == 1) {
            if (coordinate < 0) return bound + coordinate;
            if (coordinate > bound) return coordinate - bound;
        }
        return coordinate;
    }

    /**
     * Helps determine which neighbors to include in the list of neighbors
     * @param xMod - the x coordinate relative to the cell we're trying to find neighbors of
     * @param yMod - the y coordinate relative to the cell we're trying to find neighbors of
     * @param exclusion - which cells we're trying to select for (8 for all neighbors, 4 the horizontal
     *                  and vertical neighbors
     * @return a boolean representing whether the neighbor should be excluded or not
     */
    private boolean whichToExlude(int xMod, int yMod, int exclusion) {
        if (exclusion == 8) return xMod == 0 && yMod == 0;
        if (exclusion == 4) return Math.abs(xMod) == Math.abs(yMod);
        return true;
    }

    /*
    /**
     * Gets all of the cells as a collection (since they know their own location) instead of needing
     * to make one call at a time to the grid
     * @return a collection of all the cells in the grid

    public Collection<Cell> getAllCells() {
        Collection<Cell> allCells = new ArrayList<>();
        for (int x = 0; x < myCells.length; x++) {
            for (int y = 0; y < myCells[x].length; y++) {
                allCells.add(myCells[x][y]);
            }
        }
        return allCells;
    }
    */
}
