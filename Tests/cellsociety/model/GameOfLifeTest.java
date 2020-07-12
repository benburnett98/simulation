package cellsociety.model;

import cellsociety.cells.Cell;
import cellsociety.configuration.Builder;
import cellsociety.model.GameOfLife;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class GameOfLifeTest {

    @Test
    void noneAlive() throws IOException {
        testHelper(0,0,0,0,0,0,0,0,0, "0Alive");
    }
    @Test
    void OneAlive() throws IOException {
        testHelper(0,0,0,0,0,0,0,0,0, "1Alive2");
    }

    @Test
    void TwoAlive() throws IOException {
        testHelper(0,0,0,0,0,0,0,0,0, "2Alive2");
    }

    @Test
    void ThreeAlive() throws IOException {
        testHelper(0,0,0,1,1,0,1,1,0, "3Alive2");
    }

    @Test
    void FourAlive() throws IOException {
        testHelper(0,0,0,1,1,1,1,1,1, "4Alive2");
    }

    @Test
    void FiveAlive() throws IOException {
        testHelper(0,0,0,1,0,1,1,0,1, "5Alive2");
    }

    void testHelper(int val1, int val2, int val3, int val4, int val5, int val6, int val7, int val8, int val9, String fileName) throws FileNotFoundException {
        Builder gettingMyCells = new Builder(fileName);
        GameOfLife gol = new GameOfLife(gettingMyCells.getCells(), gettingMyCells.getXBound(), gettingMyCells.getYBound());
        gol.change();
        int[][] intGrid = new int[gettingMyCells.getXBound()][gettingMyCells.getYBound()];
        Collection<Cell> myCells = gettingMyCells.getCells();
        for (Cell thisCell : myCells) {
            intGrid[thisCell.getX()][thisCell.getY()] = thisCell.getCurrent();
        }
        assertEquals(val1, intGrid[0][0]);
        assertEquals(val2, intGrid[1][0]);
        assertEquals(val3, intGrid[2][0]);
        assertEquals(val4, intGrid[0][1]);
        assertEquals(val5, intGrid[1][1]);
        assertEquals(val6, intGrid[2][1]);
        assertEquals(val7, intGrid[0][2]);
        assertEquals(val8, intGrid[1][2]);
        assertEquals(val9, intGrid[2][2]);
    }

    @Test
    void testClicked() throws FileNotFoundException {
        Builder gettingMyCells = new Builder("3Alive2");
        GameOfLife gol = new GameOfLife(gettingMyCells.getCells(), gettingMyCells.getXBound(), gettingMyCells.getYBound());
        assertEquals(0, gol.changeCell(0,2));
        assertEquals(1, gol.changeCell(2,0));
    }
}