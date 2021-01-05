package ru.job4j.tracker;

import java.util.function.Consumer;

public class ReplaceAction extends BaseAction {

    public ReplaceAction(int key, String name) {
        super(key, name);
    }

    public boolean execute(Input input, Store store, Consumer<String> output) {
        String id;
        String newName;
        while (true) {
            id = input.askStr("Введите ID: ");
            newName = input.askStr("Введите новое имя: ");
            if (id != null && newName != null) {
                break;
            }
        }
        Item newItem = new Item(newName);
        store.replace(id, newItem);
        return true;
    }
}
