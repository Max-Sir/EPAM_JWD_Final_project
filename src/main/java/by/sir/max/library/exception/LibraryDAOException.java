package by.sir.max.library.exception;

public class LibraryDAOException extends Exception {
    public LibraryDAOException() {
    }

    public LibraryDAOException(String message) {
        super(message);
    }

    public LibraryDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public LibraryDAOException(Throwable cause) {
        super(cause);
    }

    public LibraryDAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
