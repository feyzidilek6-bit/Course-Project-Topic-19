package com.project.oop.model;

import java.util.ArrayList;
import java.util.List;

public class Tape {

    private List<Character> symbols;
    private int headPosition;
    private char blankSymbol = '_';

    public Tape(String input) {
        symbols = new ArrayList<>();

        for (char c : input.toCharArray()) {
            symbols.add(c);
        }

        if (symbols.isEmpty()) {
            symbols.add(blankSymbol);
        }

        headPosition = 0;
    }

    public char readSymbol() {
        return symbols.get(headPosition);
    }

    public void writeSymbol(char symbol) {
        symbols.set(headPosition, symbol);
    }

    public void moveRight() {
        headPosition++;

        if (headPosition >= symbols.size()) {
            symbols.add(blankSymbol);
        }
    }

    public void moveLeft() {
        if (headPosition == 0) {
            symbols.add(0, blankSymbol);
        } else {
            headPosition--;
        }
    }

    public String getTapeContent() {
        StringBuilder builder = new StringBuilder();

        for (char c : symbols) {
            builder.append(c);
        }

        return builder.toString();
    }
}