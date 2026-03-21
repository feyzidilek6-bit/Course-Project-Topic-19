package com.project.oop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.project.oop.model.Item;

public class ItemService {

    private List<Item> items = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void start() {

        boolean running = true;

        while (running) {

            System.out.println("\n=== MENU ===");
            System.out.println("1. Add item");
            System.out.println("2. Show items");
            System.out.println("3. Delete item");
            System.out.println("4. Search item");
            System.out.println("5. Exit");

            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    createItem();
                    break;

                case 2:
                    showItems();
                    break;

                case 3:
                    deleteItem();
                    break;

                case 4:
                    searchItem();
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

    private void createItem() {

        System.out.print("Enter item id: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter item name: ");
        String name = scanner.nextLine();

        addItem(id, name);

        System.out.println("Item added successfully!");
    }

    public void addItem(int id, String name) {
        Item item = new Item(id, name);
        items.add(item);
    }

    public void showItems() {

        if (items.isEmpty()) {
            System.out.println("No items in system.");
            return;
        }

        System.out.println("Items in system:");

        for (Item item : items) {
            System.out.println(item.getId() + " - " + item.getName());
        }
    }

    private void deleteItem() {

        System.out.print("Enter item id to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        boolean removed = items.removeIf(item -> item.getId() == id);

        if (removed) {
            System.out.println("Item deleted.");
        } else {
            System.out.println("Item not found.");
        }
    }

    private void searchItem() {

        System.out.print("Enter item id to search: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        for (Item item : items) {
            if (item.getId() == id) {
                System.out.println("Found: " + item.getId() + " - " + item.getName());
                return;
            }
        }

        System.out.println("Item not found.");
    }
}