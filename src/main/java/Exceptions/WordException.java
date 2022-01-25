package Exceptions;

public class WordException extends RuntimeException{
    public WordException() {
    }

    public WordException(String message) {
        super(message);
    }

    public WordException(String message, Throwable cause) {
        super(message, cause);
    }
}
