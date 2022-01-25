package Exceptions;

public class MoneyException extends RuntimeException{
    public MoneyException() {
    }

    public MoneyException(String message) {
        super(message);
    }

    public MoneyException(String message, Throwable cause) {
        super(message, cause);
    }
}
