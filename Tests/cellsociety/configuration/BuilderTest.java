package cellsociety.configuration;

import cellsociety.configuration.Builder;

import cellsociety.cells.Cell;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class BuilderTest {

    @Test
    void testIntBuilder() throws FileNotFoundException {
        Builder testBuild = new Builder("2Alive2");
        int[][] intGrid = new int[testBuild.getXBound()][testBuild.getYBound()];
        Collection<Cell> myCells = testBuild.getCells();
        for (Cell thisCell : myCells) {
            intGrid[thisCell.getX()][thisCell.getY()] = thisCell.getCurrent();
        }
        assertEquals(0, intGrid[0][0]);
        assertEquals(0, intGrid[1][0]);
        assertEquals(0, intGrid[2][0]);
        assertEquals(0, intGrid[0][1]);
        assertEquals(1, intGrid[1][1]);
        assertEquals(0, intGrid[2][1]);
        assertEquals(1, intGrid[0][2]);
        assertEquals(0, intGrid[1][2]);
        assertEquals(0, intGrid[2][2]);
    }

    @Test
    void testCellBuilder() throws FileNotFoundException {
        Builder testBuild = new Builder("2Alive2");
        int yB = testBuild.getYBound();
        int x = testBuild.getYBound();
        Cell[][] cellGrid = new Cell[x][yB];
        Collection<Cell> myCells = testBuild.getCells();
        for (Cell thisCell : myCells) {
            cellGrid[thisCell.getX()][thisCell.getY()] = thisCell;
        }
        assertEquals(0, cellGrid[0][0].getCurrent());
        assertEquals(0, cellGrid[1][0].getCurrent());
        assertEquals(0, cellGrid[2][0].getCurrent());
        assertEquals(0, cellGrid[0][1].getCurrent());
        assertEquals(1, cellGrid[1][1].getCurrent());
        assertEquals(0, cellGrid[2][1].getCurrent());
        assertEquals(1, cellGrid[0][2].getCurrent());
        assertEquals(0, cellGrid[1][2].getCurrent());
        assertEquals(0, cellGrid[2][2].getCurrent());
        assertEquals(0, cellGrid[0][0].getX());
        assertEquals(0, cellGrid[0][0].getY());
        assertEquals(1, cellGrid[1][0].getX());
        assertEquals(0, cellGrid[1][0].getY());
        assertEquals(2, cellGrid[2][0].getX());
        assertEquals(0, cellGrid[2][0].getY());
        assertEquals(0, cellGrid[0][1].getX());
        assertEquals(1, cellGrid[0][1].getY());
        assertEquals(1, cellGrid[1][1].getX());
        assertEquals(1, cellGrid[1][1].getY());
        assertEquals(2, cellGrid[2][1].getX());
        assertEquals(1, cellGrid[2][1].getY());
        assertEquals(0, cellGrid[0][2].getX());
        assertEquals(2, cellGrid[0][2].getY());
        assertEquals(1, cellGrid[1][2].getX());
        assertEquals(2, cellGrid[1][2].getY());
        assertEquals(2, cellGrid[2][2].getX());
        assertEquals(2, cellGrid[2][2].getY());
    }
}