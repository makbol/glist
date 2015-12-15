package pl.edu.agh.util;

public class BoardSizeException extends Exception {
    private String errorMessage;

    public BoardSizeException(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
