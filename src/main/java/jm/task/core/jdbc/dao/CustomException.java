package jm.task.core.jdbc.dao;

public class CustomException extends RuntimeException {
    public CustomException() {
        super();
    }
    public CustomException(String message) {
        super(message);
    }
    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
    public CustomException(Throwable cause) {
        super(cause);
    }

}
