package com.project.oop.model;

public class State {

    private String name;
    private boolean startState;
    private boolean finalState;

    public State(String name, boolean startState, boolean finalState) {
        this.name = name;
        this.startState = startState;
        this.finalState = finalState;
    }

    public String getName() {
        return name;
    }

    public boolean isStartState() {
        return startState;
    }

    public boolean isFinalState() {
        return finalState;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartState(boolean startState) {
        this.startState = startState;
    }

    public void setFinalState(boolean finalState) {
        this.finalState = finalState;
    }
}