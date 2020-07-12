package cellsociety.model;

import cellsociety.cells.Cell;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * @author Ben Burnett
 */
public abstract class SimModelRandom extends SimModel {

    private List<Cell> empty = new ArrayList<>();
    private Random random = new Random(200);

    public SimModelRandom(Collection<Cell> cellList, int xBound, int yBound) {
        super(cellList, xBound, yBound);
        for (Cell cell : cellList) {
            if (cell.getCurrent() == 0) {
                empty.add(cell);
            }
        }
    }

    protected void getRandom(Cell thisCell) {
        int randomIndex = random.nextInt(empty.size() - 1);
        Cell randomEmpty = empty.get(randomIndex);
        randomEmpty.setNext(thisCell.getCurrent());
        thisCell.setNext(0);
        empty.remove(randomIndex);
        empty.add(thisCell);
    }

    public List<Cell> getEmpty() {
        return empty;
    }
}
