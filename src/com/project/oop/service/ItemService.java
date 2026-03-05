package com.project.oop.service;

import java.util.ArrayList;
import java.util.List;
import com.project.oop.model.Item;

public class ItemService {

    private List<Item> items = new ArrayList<>();

    public void start() {

        System.out.println("System started...");

        addItem(1, "Example Item 1");
        addItem(2, "Example Item 2");

        showItems();

    }

    public void addItem(int id, String name) {
        Item item = new Item(id, name);
        items.add(item);
    }

    public void showItems() {

        System.out.println("Items in system:");

        for (Item item : items) {
            System.out.println(item.getId() + " - " + item.getName());
        }

    }
}