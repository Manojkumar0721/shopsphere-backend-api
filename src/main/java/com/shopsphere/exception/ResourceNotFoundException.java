package com.shopsphere.exception;

/**
 * Thrown when a requested resource (e.g., Product with a specific ID) is not found in the database.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
