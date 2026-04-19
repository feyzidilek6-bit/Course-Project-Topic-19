package com.project.oop.service;

import java.util.Scanner;
import com.project.oop.model.State;
import com.project.oop.model.Transition;
import com.project.oop.model.TuringMachine;

public class TuringMachineService {

    private Scanner scanner = new Scanner(System.in);
    private TuringMachine machine;

    public void start() {

        boolean running = true;

        while (running) {

            System.out.println("\n=== MENU ===");
            System.out.println("1. Create new Turing machine");
            System.out.println("2. Add state");
            System.out.println("3. Add transition");
            System.out.println("4. Show machine");
            System.out.println("5. Exit");

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

        State state = new State(name, isStart, isFinal);
        machine.addState(state);

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
        char direction = scanner.nextLine().charAt(0);

        System.out.print("To state: ");
        String toState = scanner.nextLine();

        Transition transition = new Transition(fromState, readSymbol, writeSymbol, direction, toState);
        machine.addTransition(transition);

        System.out.println("Transition added successfully.");
    }

    private void showMachine() {
        if (machine == null) {
            System.out.println("No machine created.");
            return;
        }

        System.out.println("Machine name: " + machine.getName());

        System.out.println("States:");
        for (State state : machine.getStates()) {
            System.out.println(
                    state.getName()
                            + " | start: " + state.isStartState()
                            + " | final: " + state.isFinalState()
            );
        }

        System.out.println("Transitions:");
        for (Transition transition : machine.getTransitions()) {
            System.out.println(
                    transition.getFromState()
                            + " --(" + transition.getReadSymbol()
                            + "/" + transition.getWriteSymbol()
                            + "," + transition.getDirection()
                            + ")--> " + transition.getToState()
            );
        }
    }
}