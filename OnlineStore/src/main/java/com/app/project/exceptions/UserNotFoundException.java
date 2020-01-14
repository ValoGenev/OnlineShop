package com.app.project.exceptions;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String message) {

        super("User with email " + message + " was not found.");

    }
}
