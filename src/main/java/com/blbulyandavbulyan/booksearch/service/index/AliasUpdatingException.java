package com.blbulyandavbulyan.booksearch.service.index;

public class AliasUpdatingException extends RuntimeException {
    public AliasUpdatingException(String message) {
        super(message);
    }

    public AliasUpdatingException(String message, Throwable cause) {
        super(message, cause);
    }
}
