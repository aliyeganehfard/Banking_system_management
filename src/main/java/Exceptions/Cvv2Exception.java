package Exceptions;

public class Cvv2Exception extends RuntimeException{
    public Cvv2Exception() {
    }

    public Cvv2Exception(String message) {
        super(message);
    }

    public Cvv2Exception(String message, Throwable cause) {
        super(message, cause);
    }
}
