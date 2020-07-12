package cellsociety.configuration;

public class InitialStateSizeException extends RuntimeException {

    private static final String message = "Layout dimensions of %s are different than %d on the %s-axis";
    public InitialStateSizeException(String fileName, int bound, String axis) {
        super(String.format(message, fileName, bound, axis));
    }
}
