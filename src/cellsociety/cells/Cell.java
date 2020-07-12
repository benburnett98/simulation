package cellsociety.cells;

/**
 * @author Ben Burnett
 */
public class Cell {
    private int myCurrent;
    private int myNext;
    private int myX;
    private int myY;

    private boolean changed = false;

    public Cell(int current, int x, int y) {
        myCurrent = current;
        myX = x;
        myY = y;
    }

    public void change() {
        if (changed) {
            myCurrent = myNext;
            changed = false;
        }
    }

    public int getCurrent() {
        return myCurrent;
    }

    public void setNext(int next) {
        myNext = next;
        changed = true;
    }

    public int getX() {
        return myX;
    }

    public int getY() {
        return myY;
    }
}