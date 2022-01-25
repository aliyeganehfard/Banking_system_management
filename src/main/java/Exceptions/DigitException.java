package Exceptions;

public class DigitException extends RuntimeException{
    public DigitException() {
    }

    public DigitException(String message) {
        super(message);
    }

    public DigitException(String message, Throwable cause) {
        super(message, cause);
    }
}
