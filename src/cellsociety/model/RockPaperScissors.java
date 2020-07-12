package cellsociety.model;

import cellsociety.cells.Cell;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Ben Burnett
 * Rock = 0
 * Paper = 1
 * Scissors = 2
 */

public class RockPaperScissors extends SimModel {

    private int numOptions = 3;
    private int threshold;

    public RockPaperScissors(Collection<Cell> cellList, int xBound, int yBound, int changeThreshold) {
        super(cellList, xBound, yBound);
        threshold = changeThreshold;
    }

    @Override
    protected void logicOnCell(Cell thisCell) {
        int[] optionsCount = new int[numOptions];
        for (Cell neighbor : findMoorePlane(thisCell.getX(), thisCell.getY())) {
            optionsCount[neighbor.getCurrent()]++;
        }
        //TODO: this will probably need some debugging
        for (int i = thisCell.getCurrent() + 1 % numOptions; i <= (thisCell.getCurrent() + numOptions / 2) % numOptions; i++) {
            if (optionsCount[i] >= threshold) {
                thisCell.setNext(i);
                return;
            }
        }
    }

    @Override
    protected void clickedOnLogic(Cell thisCell) {
        thisCell.setNext((thisCell.getCurrent() + 1) % numOptions);
    }

}
