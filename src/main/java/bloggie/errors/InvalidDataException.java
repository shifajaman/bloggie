package bloggie.errors;

public class InvalidDataException extends RuntimeException {

    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
