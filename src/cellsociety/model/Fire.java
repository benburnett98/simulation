package cellsociety.model;

import cellsociety.cells.Cell;

import java.util.Collection;

/**
 * @author Ben Burnett
 * Empty = 0
 * Fire = 1
 * Tree = 2
 */

public class Fire extends SimModel {
    double probLight;
    public Fire(Collection<Cell> cellList, int xBound, int yBound, double probability) {
        super(cellList, xBound, yBound);
        probLight = probability;
    }

    @Override
    protected void logicOnCell(Cell thisCell) {
        Collection<Cell> neighbors = findCardinalPlane(thisCell.getX(), thisCell.getY());
        if (thisCell.getCurrent() == 0) return;
        if (thisCell.getCurrent() == 1) {
            thisCell.setNext(0);
        }
        for (Cell neighbor : neighbors) {
            if (neighbor.getCurrent() == 1 && Math.random() < probLight) {
                thisCell.setNext(2);
                return;
            }
        }
    }

    @Override
    protected void clickedOnLogic(Cell thisCell) {
        if (thisCell.getCurrent() == 0 || thisCell.getCurrent() == 1) {
            thisCell.setNext(2);
        }
        if (thisCell.getCurrent() == 2) {
            thisCell.setNext(1);
        }
    }
}

