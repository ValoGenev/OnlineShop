package com.app.project.exceptions;

public class WrongInputException extends Exception {

    public WrongInputException() {
        super("Wrong input (Make sure that you type a integer value");
    }
}
