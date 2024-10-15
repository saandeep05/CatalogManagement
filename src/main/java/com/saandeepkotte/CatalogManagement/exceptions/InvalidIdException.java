package com.saandeepkotte.CatalogManagement.exceptions;

public class InvalidIdException extends Exception {
    public InvalidIdException(Long id) {
        super("No item found with id: " + id);
    }
    public InvalidIdException(int id) {
        super("No item found with id: " + id);
    }
}
