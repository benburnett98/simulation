package cellsociety.model;

import cellsociety.cells.Cell;
import cellsociety.configuration.Builder;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class PercolationTest {
    @Test
    void gameLogicOneStep() throws FileNotFoundException {
        Builder gettingMyCells = new Builder("PercPathAvailable");
        Percolation perc = new Percolation(gettingMyCells.getCells(), gettingMyCells.getXBound(), gettingMyCells.getYBound());
        perc.change();
        int[][] intGrid = new int[gettingMyCells.getXBound()][gettingMyCells.getYBound()];
        Collection<Cell> myCells = gettingMyCells.getCells();
        for (Cell thisCell : myCells) {
            intGrid[thisCell.getX()][thisCell.getY()] = thisCell.getCurrent();
        }
        assertEquals(1, intGrid[0][0]);
        assertEquals(0, intGrid[1][0]);
        assertEquals(1, intGrid[2][0]);
        assertEquals(1, intGrid[0][1]);
        assertEquals(1, intGrid[1][1]);
        assertEquals(0, intGrid[2][1]);
        assertEquals(0, intGrid[0][2]);
        assertEquals(2, intGrid[1][2]);
        assertEquals(2, intGrid[2][2]);
    }

    @Test
    void gameLogicTwoStep() throws FileNotFoundException {
        Builder gettingMyCells = new Builder("PercPathAvailable");
        Percolation perc = new Percolation(gettingMyCells.getCells(), gettingMyCells.getXBound(), gettingMyCells.getYBound());
        perc.change();
        perc.change();
        int[][] intGrid = new int[gettingMyCells.getXBound()][gettingMyCells.getYBound()];
        Collection<Cell> myCells = gettingMyCells.getCells();
        for (Cell thisCell : myCells) {
            intGrid[thisCell.getX()][thisCell.getY()] = thisCell.getCurrent();
        }
        assertEquals(1, intGrid[0][0]);
        assertEquals(0, intGrid[1][0]);
        assertEquals(1, intGrid[2][0]);
        assertEquals(1, intGrid[0][1]);
        assertEquals(2, intGrid[1][1]);
        assertEquals(0, intGrid[2][1]);
        assertEquals(0, intGrid[0][2]);
        assertEquals(2, intGrid[1][2]);
        assertEquals(2, intGrid[2][2]);
    }

    @Test
    void gameLogicCheckHorizontalAndDiagonal() throws FileNotFoundException {
        Builder gettingMyCells = new Builder("PercPathAvailable");
        Percolation perc = new Percolation(gettingMyCells.getCells(), gettingMyCells.getXBound(), gettingMyCells.getYBound());
        perc.change();
        perc.change();
        perc.change();
        int[][] intGrid = new int[gettingMyCells.getXBound()][gettingMyCells.getYBound()];
        Collection<Cell> myCells = gettingMyCells.getCells();
        for (Cell thisCell : myCells) {
            intGrid[thisCell.getX()][thisCell.getY()] = thisCell.getCurrent();
        }
        assertEquals(1, intGrid[0][0]);
        assertEquals(0, intGrid[1][0]);
        assertEquals(1, intGrid[2][0]);
        assertEquals(2, intGrid[0][1]);
        assertEquals(2, intGrid[1][1]);
        assertEquals(0, intGrid[2][1]);
        assertEquals(0, intGrid[0][2]);
        assertEquals(2, intGrid[1][2]);
        assertEquals(2, intGrid[2][2]);
    }

    @Test
    void checkClickedLogic() throws FileNotFoundException {
        Builder gettingMyCells = new Builder("PercPathAvailable");
        Percolation perc = new Percolation(gettingMyCells.getCells(), gettingMyCells.getXBound(), gettingMyCells.getYBound());
        perc.changeCell(2,2);
        perc.changeCell(1,0);
        perc.changeCell(0,0);
        assertEquals(1, perc.getValueAtLoc(2,2));
        assertEquals(1, perc.getValueAtLoc(1,0));
        assertEquals(2, perc.getValueAtLoc(0,0));

    }
}