package cellsociety.controller;

public class KeyNotFoundException extends Exception {

    private static final String message = " The key %s cannot be found in the %s properties file";

    public KeyNotFoundException(String key, String propFile) {
        super(String.format(message, key, propFile));
    }

}
