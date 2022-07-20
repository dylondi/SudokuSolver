package exceptions;

public class InvalidParameterValueException extends RuntimeException{
    public InvalidParameterValueException(String message) {
        super(message);
    }
}
