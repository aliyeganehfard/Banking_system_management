package Exceptions;

public class DateException extends RuntimeException{
    public DateException() {
    }

    public DateException(String message) {
        super(message);
    }

    public DateException(String message, Throwable cause) {
        super(message, cause);
    }
}
