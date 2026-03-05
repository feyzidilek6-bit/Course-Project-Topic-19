package com.project.oop;

import com.project.oop.service.ItemService;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== Course Project System ===");

        ItemService service = new ItemService();
        service.start();

    }
}