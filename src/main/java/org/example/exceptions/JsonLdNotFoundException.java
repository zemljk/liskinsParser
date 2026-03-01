package org.example.exceptions;

public class JsonLdNotFoundException extends RuntimeException {
    public JsonLdNotFoundException(String message) {
        super(message);
    }
}
