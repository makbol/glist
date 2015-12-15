package pl.edu.agh.util;

public class EnumIdOutOfBoundsException extends Exception {
    private String errorMessage;

    public EnumIdOutOfBoundsException(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
