package cellsociety.model;

import cellsociety.cells.Cell;

import java.util.Collection;

/**
 * @author - Ben Burnett
 */
public class GameOfLife extends SimModel{

    /**
     * Makes a call to the super class simmodel
     * @param cellList - the collection of cells read in from the csv file
     * @param xBound - the x boundary of the grid
     * @param yBound - the y boundary of the grid
     */
    public GameOfLife(Collection<Cell> cellList, int xBound, int yBound) {
        super(cellList, xBound, yBound);
    }

    /**
     * Overrides an abstract method from SimModel to give the logic for if a cell
     * has been clicked on
     * Flips from alive to dead and vice versa
     * @param update - the cell that's been clicked on
     */
    @Override
    protected void clickedOnLogic(Cell update) {
        update.setNext(Math.abs(1 - update.getCurrent()));
    }

    /**
     * Overrides an abstract method in SimModel to give the logic for a cell
     * generally in the context of the simulation
     * @param thisCell - the cell we're trying to determine the next state for
     */
    @Override
    protected void logicOnCell(Cell thisCell) {
        int alive = 0;
        for (Cell neighbor : findMoorePlane(thisCell.getX(), thisCell.getY())) {
            if (neighbor.getCurrent() != 0) alive++;
        }
        if (alive < 2 || alive >= 4) thisCell.setNext(0);
        if (alive == 3 ) thisCell.setNext(1);
    }
}
