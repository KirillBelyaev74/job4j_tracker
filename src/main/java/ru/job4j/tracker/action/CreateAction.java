package ru.job4j.tracker.action;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.item.Item;
import ru.job4j.tracker.Store;

import java.util.function.Consumer;

public class CreateAction extends BaseAction {

    public CreateAction(int key, String name) {
        super(key, name);
    }

    public boolean execute(Input input, Store store, Consumer<String> output) {
        String name = input.askStr("Введите имя: ");
        Item item = new Item(name);
        store.add(item);
        return true;
    }
}
