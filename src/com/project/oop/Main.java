package com.project.oop;

import com.project.oop.service.TuringMachineService;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== Turing Machine Project ===");

        TuringMachineService service = new TuringMachineService();
        service.start();
    }
}