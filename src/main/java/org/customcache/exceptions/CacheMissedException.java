package org.customcache.exceptions;

public class CacheMissedException extends RuntimeException {
    public CacheMissedException(String message) {
        super(message);
    }
}
