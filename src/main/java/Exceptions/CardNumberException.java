package Exceptions;

public class CardNumberException extends RuntimeException{
    public CardNumberException() {
    }

    public CardNumberException(String message) {
        super(message);
    }

    public CardNumberException(String message, Throwable cause) {
        super(message, cause);
    }
}
