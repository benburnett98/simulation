package cellsociety.controller;

public class SimNotSupportedException extends RuntimeException {

    private static final String message = "%s is not currently supported";
    public SimNotSupportedException(String sim) {
        super(sim + message);
    }
}
