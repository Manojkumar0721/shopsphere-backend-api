package com.shopsphere.exception;

/**
 * Thrown when an entity cannot be created because a unique property (e.g., SKU) already exists.
 */
public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }
}
