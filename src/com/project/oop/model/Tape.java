package com.project.oop.model;

public class Tape {

    private char[] symbols;
    private int headPosition;

    public Tape(String input) {
        this.symbols = input.toCharArray();
        this.headPosition = 0;
    }

    public char readSymbol() {
        return symbols[headPosition];
    }

    public void writeSymbol(char symbol) {
        symbols[headPosition] = symbol;
    }

    public void moveLeft() {
        if (headPosition > 0) {
            headPosition--;
        }
    }

    public void moveRight() {
        if (headPosition < symbols.length - 1) {
            headPosition++;
        }
    }

    public int getHeadPosition() {
        return headPosition;
    }

    public String getTapeContent() {
        return new String(symbols);
    }
}