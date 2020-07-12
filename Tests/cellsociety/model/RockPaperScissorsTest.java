package cellsociety.model;

import cellsociety.cells.Cell;
import cellsociety.configuration.Builder;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class RockPaperScissorsTest {
    @Test
    void gameLogicNoChange() throws FileNotFoundException {
        Builder gettingMyCells = new Builder("RPSMiddleNoChange");
        RockPaperScissors rps = new RockPaperScissors(gettingMyCells.getCells(),
                gettingMyCells.getXBound(), gettingMyCells.getYBound(), 4);
        rps.change();
        int[][] intGrid = new int[gettingMyCells.getXBound()][gettingMyCells.getYBound()];
        Collection<Cell> myCells = gettingMyCells.getCells();
        for (Cell thisCell : myCells) {
            intGrid[thisCell.getX()][thisCell.getY()] = thisCell.getCurrent();
        }
        assertEquals(1, intGrid[0][0]);
        assertEquals(1, intGrid[1][0]);
        assertEquals(1, intGrid[2][0]);
        assertEquals(1, intGrid[0][1]);
        assertEquals(2, intGrid[1][1]);
        assertEquals(1, intGrid[2][1]);
        assertEquals(1, intGrid[0][2]);
        assertEquals(1, intGrid[1][2]);
        assertEquals(1, intGrid[2][2]);
    }

    @Test
    void gameLogicChange() throws FileNotFoundException {
        Builder gettingMyCells = new Builder("RPSMiddleChange");
        RockPaperScissors rps = new RockPaperScissors(gettingMyCells.getCells(),
                gettingMyCells.getXBound(), gettingMyCells.getYBound(), 4);
        rps.change();
        int[][] intGrid = new int[gettingMyCells.getXBound()][gettingMyCells.getYBound()];
        Collection<Cell> myCells = gettingMyCells.getCells();
        for (Cell thisCell : myCells) {
            intGrid[thisCell.getX()][thisCell.getY()] = thisCell.getCurrent();
        }
        assertEquals(2, intGrid[0][0]);
        assertEquals(2, intGrid[1][0]);
        assertEquals(2, intGrid[2][0]);
        assertEquals(2, intGrid[0][1]);
        assertEquals(2, intGrid[1][1]);
        assertEquals(2, intGrid[2][1]);
        assertEquals(2, intGrid[0][2]);
        assertEquals(2, intGrid[1][2]);
        assertEquals(2, intGrid[2][2]);
    }

    @Test
    void clickedOnLogic() throws FileNotFoundException {
        Builder gettingMyCells = new Builder("RPSClick");
        RockPaperScissors rps = new RockPaperScissors(gettingMyCells.getCells(),
                gettingMyCells.getXBound(), gettingMyCells.getYBound(), 4);
        rps.changeCell(0,0);
        rps.changeCell(1,0);
        rps.changeCell(2,0);
    }
}