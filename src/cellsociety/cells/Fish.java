package cellsociety.cells;

/**
 * @author Ben Burnett
 */

public class Fish extends Cell {
    int timeAlive;

    public Fish(int current, int x, int y) {
        super(current, x, y);
        timeAlive = 0;
    }

    public Fish(int current, int timeAlreadyAlive, int x, int y) {
        super (current, x, y);
        timeAlive = 1;
    }

    public void change() {
        super.change();
        timeAlive++;
    }

    public int getTimeAlive() {
        return timeAlive;
    }

    public void resetTimeAlive() {
        timeAlive = 0;
    }
}
