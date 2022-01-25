package Exceptions;

public class NationalCodeLengthException extends RuntimeException{
    public NationalCodeLengthException() {
    }

    public NationalCodeLengthException(String message) {
        super(message);
    }

    public NationalCodeLengthException(String message, Throwable cause) {
        super(message, cause);
    }
}
