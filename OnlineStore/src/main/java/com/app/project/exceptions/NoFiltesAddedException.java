package com.app.project.exceptions;

public class NoFiltesAddedException extends Exception {

    public NoFiltesAddedException() {
        super("Zero filters added.");
    }
}
