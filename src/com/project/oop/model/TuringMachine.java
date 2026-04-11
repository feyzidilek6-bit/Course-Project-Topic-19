package com.project.oop.model;

import java.util.ArrayList;
import java.util.List;

public class TuringMachine {

    private String name;
    private List<State> states;
    private List<Transition> transitions;

    public TuringMachine(String name) {
        this.name = name;
        this.states = new ArrayList<>();
        this.transitions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<State> getStates() {
        return states;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public void addState(State state) {
        states.add(state);
    }

    public void addTransition(Transition transition) {
        transitions.add(transition);
    }
}