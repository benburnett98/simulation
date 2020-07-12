package cellsociety.model;

import cellsociety.cells.Cell;

import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * @author Ben Burnett
 * Empty = 0
 * Type1 = 1
 * Type2 = 2
 */

public class Segregation extends SimModelRandom {

    double contentThreshold;

    public Segregation(Collection<Cell> cellList, int xBound, int yBound, double threshold) {
        super(cellList, xBound, yBound);
        contentThreshold = threshold;
    }

    @Override
    protected void logicOnCell(Cell thisCell) {
        int sameType = 0;
        Collection<Cell> mooreNeighbors = findMoorePlane(thisCell.getX(), thisCell.getY());
        for (Cell neighbor : mooreNeighbors) {
            if (neighbor.getCurrent() == thisCell.getCurrent()) {
                sameType++;
            }
        }
        double contentRatio = (double) (sameType / mooreNeighbors.size());
        if (contentRatio < contentThreshold) {
            getRandom(thisCell);
            return;
        }
        thisCell.setNext(thisCell.getCurrent());
    }


    @Override
    protected void clickedOnLogic(Cell thisCell) {
        Random random = new Random();
        if (thisCell.getCurrent() == 0) {
            thisCell.setNext(random.nextInt(2) + 1);
        }

        if (thisCell.getCurrent() == 1 || thisCell.getCurrent() == 2) {
            thisCell.setNext(Math.abs(1 - (thisCell.getCurrent() - 1)) + 1);
        }
    }
}