package Exceptions;

public class PhoneNumberException extends RuntimeException{
    public PhoneNumberException() {
    }

    public PhoneNumberException(String message) {
        super(message);
    }

    public PhoneNumberException(String message, Throwable cause) {
        super(message, cause);
    }
}
