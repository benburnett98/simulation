package cellsociety.model;

import cellsociety.cells.Cell;
import cellsociety.cells.Fish;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * @author Ben Burnett
 * Empty = 0
 * Fish = 1
 * Shark = 2
 */

public class WaTor extends SimModelRandom {

    int makeKids;
    public WaTor(Collection<Cell> cellList, int xBound, int yBound, int numTurns) {
        super(makeFish(cellList), xBound, yBound);
        makeKids = numTurns;
    }

    @Override
    protected void logicOnCell(Cell update) {

    }

    @Override
    protected void clickedOnLogic(Cell thisCell) {
        if (thisCell.getCurrent() == 0) {
            thisCell.setNext(1);
        }
        if (thisCell.getCurrent() == 1 || thisCell.getCurrent() == 2) {
            thisCell.setNext(Math.abs(1 - (thisCell.getCurrent() - 1)) + 1);
        }
    }

    private static Collection<Cell> makeFish(Collection<Cell> myCells) {
        Collection<Cell> myFish = new ArrayList<>();
        for (Cell cell : myCells) {
            Fish fish = (Fish) cell;
            myFish.add(fish);
        }
        return myFish;
    }
}
