package com.project.oop.model;

public class Transition {

    private String fromState;
    private char readSymbol;
    private char writeSymbol;
    private char direction;
    private String toState;

    public Transition(String fromState, char readSymbol, char writeSymbol, char direction, String toState) {
        this.fromState = fromState;
        this.readSymbol = readSymbol;
        this.writeSymbol = writeSymbol;
        this.direction = direction;
        this.toState = toState;
    }

    public String getFromState() {
        return fromState;
    }

    public char getReadSymbol() {
        return readSymbol;
    }

    public char getWriteSymbol() {
        return writeSymbol;
    }

    public char getDirection() {
        return direction;
    }

    public String getToState() {
        return toState;
    }
}