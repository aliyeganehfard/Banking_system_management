package Exceptions;

public class NationalCodeException extends RuntimeException{
    public NationalCodeException() {
    }

    public NationalCodeException(String message) {
        super(message);
    }

    public NationalCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
