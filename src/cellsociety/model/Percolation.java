package cellsociety.model;

import cellsociety.cells.Cell;

import java.util.Collection;

/**
 * @author Ben Burnett
 * closed = 0
 * open = 1
 * water = 2
 */

public class Percolation extends SimModel {
    int myYBound;
    public Percolation(Collection<Cell> cellList, int xBound, int yBound) {
        super(cellList, xBound, yBound);
        myYBound = yBound;
    }

    @Override
    protected void logicOnCell(Cell thisCell) {
        if (thisCell.getCurrent() == 0 || thisCell.getCurrent() == 2) return;
        if (thisCell.getCurrent() == 1 & thisCell.getY() == myYBound - 1) {
            thisCell.setNext(2);
            return;
        }
        Collection<Cell> neighbors = findCardinalPlane(thisCell.getX(), thisCell.getY());
        for (Cell neighbor : neighbors) {
            if (neighbor.getCurrent() == 2) {
                thisCell.setNext(2);
                return;
            }
        }
    }


    @Override
    protected void clickedOnLogic(Cell thisCell) {
        if (thisCell.getCurrent() == 0 || thisCell.getCurrent() == 1) {
            thisCell.setNext(thisCell.getCurrent() + 1);
        }
        if (thisCell.getCurrent() == 2) {
            thisCell.setNext(1);
        }
    }
}
