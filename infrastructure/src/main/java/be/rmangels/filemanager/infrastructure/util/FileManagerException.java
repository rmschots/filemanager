package be.rmangels.filemanager.infrastructure.util;

public class FileManagerException extends RuntimeException {
    public static FileManagerException of(String message, Throwable cause) {
        return new FileManagerException(message, cause);
    }

    private FileManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}
