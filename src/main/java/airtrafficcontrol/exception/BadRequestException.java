package airtrafficcontrol.exception;

/**
 * A custom exception for our project.
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
