package by.sir.max.library.exception;

public class LibraryServiceException extends Exception{
    public LibraryServiceException() {
    }

    public LibraryServiceException(String message) {
        super(message);
    }

    public LibraryServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public LibraryServiceException(Throwable cause) {
        super(cause);
    }

    public LibraryServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
