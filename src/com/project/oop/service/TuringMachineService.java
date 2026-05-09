package com.project.oop.service;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

import com.project.oop.model.State;
import com.project.oop.model.Transition;
import com.project.oop.model.Tape;
import com.project.oop.model.TuringMachine;

public class TuringMachineService {

    private Scanner scanner = new Scanner(System.in);
    private TuringMachine machine;

    public void start() {

        boolean running = true;

        while (running) {

            System.out.println("\n=== TURING MACHINE MENU ===");
            System.out.println("1. Create new Turing machine");
            System.out.println("2. Add state");
            System.out.println("3. Add transition");
            System.out.println("4. Show machine");
            System.out.println("5. Run machine");
            System.out.println("6. Save machine");
            System.out.println("7. Load machine");
            System.out.println("8. Help");
            System.out.println("9. Exit");

            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createMachine();
                    break;
                case 2:
                    addState();
                    break;
                case 3:
                    addTransition();
                    break;
                case 4:
                    showMachine();
                    break;
                case 5:
                    runMachine();
                    break;
                case 6:
                    saveMachine();
                    break;
                case 7:
                    loadMachine();
                    break;
                case 8:
                    showHelp();
                    break;
                case 9:
                    running = false;
                    System.out.println("System stopped.");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void createMachine() {
        System.out.print("Enter machine name: ");
        String name = scanner.nextLine();

        machine = new TuringMachine(name);
        System.out.println("Turing machine created successfully.");
    }

    private void addState() {
        if (machine == null) {
            System.out.println("Create a machine first.");
            return;
        }

        System.out.print("Enter state name: ");
        String name = scanner.nextLine();

        System.out.print("Is start state? (true/false): ");
        boolean isStart = scanner.nextBoolean();

        System.out.print("Is final state? (true/false): ");
        boolean isFinal = scanner.nextBoolean();
        scanner.nextLine();

        machine.addState(new State(name, isStart, isFinal));
        System.out.println("State added successfully.");
    }

    private void addTransition() {
        if (machine == null) {
            System.out.println("Create a machine first.");
            return;
        }

        System.out.print("From state: ");
        String fromState = scanner.nextLine();

        System.out.print("Read symbol: ");
        char readSymbol = scanner.nextLine().charAt(0);

        System.out.print("Write symbol: ");
        char writeSymbol = scanner.nextLine().charAt(0);

        System.out.print("Direction (L/R): ");
        char direction = scanner.nextLine().toUpperCase().charAt(0);

        System.out.print("To state: ");
        String toState = scanner.nextLine();

        machine.addTransition(new Transition(fromState, readSymbol, writeSymbol, direction, toState));
        System.out.println("Transition added successfully.");
    }

    private void showMachine() {
        if (machine == null) {
            System.out.println("No machine created.");
            return;
        }

        System.out.println("\nMachine name: " + machine.getName());

        System.out.println("States:");
        for (State state : machine.getStates()) {
            System.out.println(state.getName()
                    + " | start: " + state.isStartState()
                    + " | final: " + state.isFinalState());
        }

        System.out.println("Transitions:");
        for (Transition transition : machine.getTransitions()) {
            System.out.println(transition.getFromState()
                    + " --(" + transition.getReadSymbol()
                    + "/" + transition.getWriteSymbol()
                    + "," + transition.getDirection()
                    + ")--> " + transition.getToState());
        }
    }

    private void runMachine() {
        if (machine == null) {
            System.out.println("Create a machine first.");
            return;
        }

        State currentState = findStartState();

        if (currentState == null) {
            System.out.println("No start state defined.");
            return;
        }

        System.out.print("Enter input string: ");
        String input = scanner.nextLine();

        Tape tape = new Tape(input);

        System.out.println("Starting machine...");

        int step = 0;
        int maxSteps = 100;

        while (!currentState.isFinalState() && step < maxSteps) {

            char currentSymbol = tape.readSymbol();
            Transition transition = findTransition(currentState.getName(), currentSymbol);

            if (transition == null) {
                System.out.println("No transition found. Machine stopped.");
                break;
            }

            tape.writeSymbol(transition.getWriteSymbol());

            if (transition.getDirection() == 'R') {
                tape.moveRight();
            } else if (transition.getDirection() == 'L') {
                tape.moveLeft();
            }

            currentState = findState(transition.getToState());

            if (currentState == null) {
                System.out.println("Target state not found. Machine stopped.");
                break;
            }

            step++;
            System.out.println("Step " + step + ": " + tape.getTapeContent());
        }

        if (currentState != null && currentState.isFinalState()) {
            System.out.println("Machine reached final state: " + currentState.getName());
        }

        System.out.println("Final tape: " + tape.getTapeContent());
    }

    private State findStartState() {
        for (State state : machine.getStates()) {
            if (state.isStartState()) {
                return state;
            }
        }
        return null;
    }

    private State findState(String name) {
        for (State state : machine.getStates()) {
            if (state.getName().equals(name)) {
                return state;
            }
        }
        return null;
    }

    private Transition findTransition(String stateName, char symbol) {
        for (Transition transition : machine.getTransitions()) {
            if (transition.getFromState().equals(stateName)
                    && transition.getReadSymbol() == symbol) {
                return transition;
            }
        }
        return null;
    }

    private void saveMachine() {
        if (machine == null) {
            System.out.println("No machine to save.");
            return;
        }

        System.out.print("Enter file name: ");
        String fileName = scanner.nextLine();

        try {
            PrintWriter writer = new PrintWriter(fileName);

            writer.println("MACHINE:" + machine.getName());

            for (State state : machine.getStates()) {
                writer.println("STATE:" + state.getName() + "," + state.isStartState() + "," + state.isFinalState());
            }

            for (Transition transition : machine.getTransitions()) {
                writer.println("TRANSITION:"
                        + transition.getFromState() + ","
                        + transition.getReadSymbol() + ","
                        + transition.getWriteSymbol() + ","
                        + transition.getDirection() + ","
                        + transition.getToState());
            }

            writer.close();
            System.out.println("Machine saved successfully.");

        } catch (Exception e) {
            System.out.println("Error while saving machine.");
        }
    }

    private void loadMachine() {
        System.out.print("Enter file name: ");
        String fileName = scanner.nextLine();

        try {
            File file = new File(fileName);
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();

                if (line.startsWith("MACHINE:")) {
                    String name = line.substring(8);
                    machine = new TuringMachine(name);
                } else if (line.startsWith("STATE:")) {
                    String[] parts = line.substring(6).split(",");
                    machine.addState(new State(
                            parts[0],
                            Boolean.parseBoolean(parts[1]),
                            Boolean.parseBoolean(parts[2])
                    ));
                } else if (line.startsWith("TRANSITION:")) {
                    String[] parts = line.substring(11).split(",");
                    machine.addTransition(new Transition(
                            parts[0],
                            parts[1].charAt(0),
                            parts[2].charAt(0),
                            parts[3].charAt(0),
                            parts[4]
                    ));
                }
            }

            fileScanner.close();
            System.out.println("Machine loaded successfully.");

        } catch (Exception e) {
            System.out.println("Error while loading machine.");
        }
    }

    private void showHelp() {
        System.out.println("\nHelp:");
        System.out.println("1 - Create a new Turing machine");
        System.out.println("2 - Add a state");
        System.out.println("3 - Add a transition");
        System.out.println("4 - Show current machine");
        System.out.println("5 - Run machine with input string");
        System.out.println("6 - Save machine to file");
        System.out.println("7 - Load machine from file");
        System.out.println("9 - Exit");
    }
}