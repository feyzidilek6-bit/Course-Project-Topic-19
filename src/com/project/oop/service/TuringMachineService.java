package com.project.oop.service;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.project.oop.model.State;
import com.project.oop.model.Transition;
import com.project.oop.model.Tape;
import com.project.oop.model.TuringMachine;

public class TuringMachineService {

    private Scanner scanner = new Scanner(System.in);

    private List<TuringMachine> machines = new ArrayList<>();

    private int nextId = 1;

    private TuringMachine machine;
    private Tape currentTape;
    private State currentState;

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
            System.out.println("10. Set start state");
            System.out.println("11. Add accept state");
            System.out.println("12. Add reject state");
            System.out.println("13. List machines");
            System.out.println("14. Show tape");
            System.out.println("15. Reset machine");
            System.out.println("16. Remove transition");

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
                case 10:
                    setStartState();
                    break;
                case 11:
                    addAcceptState();
                    break;
                case 12:
                    addRejectState();
                    break;
                case 13:
                    listMachines();
                    break;
                case 14:
                    showTape();
                    break;
                case 15:
                    resetMachine();
                    break;
                case 16:
                    removeTransition();
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private void createMachine() {
        System.out.print("Enter machine name: ");
        String name = scanner.nextLine();

        machine = new TuringMachine(nextId, name);
        machines.add(machine);

        currentTape = null;
        currentState = null;

        System.out.println("Turing machine created successfully.");
        System.out.println("Machine ID: " + nextId);

        nextId++;
    }

    private void addState() {
        if (machine == null) {
            System.out.println("Create a machine first.");
            return;
        }

        System.out.print("Enter state name: ");
        String name = scanner.nextLine();

        machine.addState(new State(name, false, false));
        System.out.println("State added successfully.");
    }

    private void setStartState() {
        if (machine == null) {
            System.out.println("Create a machine first.");
            return;
        }

        System.out.print("Enter state name: ");
        String stateName = scanner.nextLine();

        State state = findState(stateName);

        if (state == null) {
            System.out.println("State not found.");
            return;
        }

        for (State s : machine.getStates()) {
            s.setStartState(false);
        }

        state.setStartState(true);
        System.out.println("Start state set successfully.");
    }

    private void addAcceptState() {
        if (machine == null) {
            System.out.println("Create a machine first.");
            return;
        }

        System.out.print("Enter state name: ");
        String stateName = scanner.nextLine();

        State state = findState(stateName);

        if (state == null) {
            System.out.println("State not found.");
            return;
        }

        state.setFinalState(true);
        System.out.println("Accept state added successfully.");
    }

    private void addRejectState() {
        if (machine == null) {
            System.out.println("Create a machine first.");
            return;
        }

        System.out.print("Enter state name: ");
        String stateName = scanner.nextLine();

        State state = findState(stateName);

        if (state == null) {
            System.out.println("State not found.");
            return;
        }

        state.setRejectState(true);
        System.out.println("Reject state added successfully.");
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

        System.out.println("\nMachine ID: " + machine.getId());
        System.out.println("Machine name: " + machine.getName());

        System.out.println("States:");
        for (State state : machine.getStates()) {
            System.out.println(state.getName()
                    + " | start: " + state.isStartState()
                    + " | accept: " + state.isFinalState()
                    + " | reject: " + state.isRejectState());
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

        currentState = findStartState();

        if (currentState == null) {
            System.out.println("No start state defined.");
            return;
        }

        System.out.print("Enter input string: ");
        String input = scanner.nextLine();

        currentTape = new Tape(input);

        System.out.println("Starting machine...");

        int step = 0;
        int maxSteps = 100;

        while (!currentState.isFinalState()
                && !currentState.isRejectState()
                && step < maxSteps) {

            char currentSymbol = currentTape.readSymbol();
            Transition transition = findTransition(currentState.getName(), currentSymbol);

            if (transition == null) {
                System.out.println("No transition found. Machine stopped.");
                break;
            }

            currentTape.writeSymbol(transition.getWriteSymbol());

            if (transition.getDirection() == 'R') {
                currentTape.moveRight();
            } else if (transition.getDirection() == 'L') {
                currentTape.moveLeft();
            }

            currentState = findState(transition.getToState());

            if (currentState == null) {
                System.out.println("Target state not found. Machine stopped.");
                break;
            }

            step++;
            System.out.println("Step " + step + ": " + currentTape.getTapeContent());
        }

        if (currentState != null && currentState.isFinalState()) {
            System.out.println("Machine reached accept state: " + currentState.getName());
        }

        if (currentState != null && currentState.isRejectState()) {
            System.out.println("Machine reached reject state: " + currentState.getName());
        }

        System.out.println("Final tape: " + currentTape.getTapeContent());
    }

    private void showTape() {
        if (currentTape == null) {
            System.out.println("No tape available.");
            return;
        }

        System.out.println("Current tape: " + currentTape.getTapeContent());
    }

    private void resetMachine() {
        currentTape = null;
        currentState = null;

        System.out.println("Machine reset successfully.");
    }

    private void removeTransition() {
        if (machine == null) {
            System.out.println("Create a machine first.");
            return;
        }

        System.out.print("From state: ");
        String fromState = scanner.nextLine();

        System.out.print("Read symbol: ");
        char readSymbol = scanner.nextLine().charAt(0);

        Transition transitionToRemove = null;

        for (Transition transition : machine.getTransitions()) {
            if (transition.getFromState().equals(fromState)
                    && transition.getReadSymbol() == readSymbol) {
                transitionToRemove = transition;
                break;
            }
        }

        if (transitionToRemove != null) {
            machine.getTransitions().remove(transitionToRemove);
            System.out.println("Transition removed successfully.");
        } else {
            System.out.println("Transition not found.");
        }
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

            writer.println("MACHINE:" + machine.getId() + "," + machine.getName());

            for (State state : machine.getStates()) {
                writer.println("STATE:" + state.getName() + ","
                        + state.isStartState() + ","
                        + state.isFinalState() + ","
                        + state.isRejectState());
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
                    String machineData = line.substring(8);
                    String[] parts = machineData.split(",");

                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];

                    machine = new TuringMachine(id, name);
                    machines.add(machine);

                    currentTape = null;
                    currentState = null;

                    if (id >= nextId) {
                        nextId = id + 1;
                    }

                } else if (line.startsWith("STATE:")) {
                    String[] parts = line.substring(6).split(",");

                    State state = new State(
                            parts[0],
                            Boolean.parseBoolean(parts[1]),
                            Boolean.parseBoolean(parts[2])
                    );

                    if (parts.length > 3) {
                        state.setRejectState(Boolean.parseBoolean(parts[3]));
                    }

                    machine.addState(state);

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

    private void listMachines() {
        if (machines.isEmpty()) {
            System.out.println("No machines created.");
            return;
        }

        System.out.println("\nMachines:");

        for (TuringMachine tm : machines) {
            System.out.println(
                    "ID: " + tm.getId()
                            + " | Name: " + tm.getName()
            );
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
        System.out.println("8 - Show help menu");
        System.out.println("9 - Exit program");
        System.out.println("10 - Set start state");
        System.out.println("11 - Add accept state");
        System.out.println("12 - Add reject state");
        System.out.println("13 - List machines");
        System.out.println("14 - Show tape");
        System.out.println("15 - Reset machine");
        System.out.println("16 - Remove transition");
    }
}