package ru.job4j.tracker.action;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.item.Item;
import ru.job4j.tracker.store.Store;

import java.util.function.Consumer;

public class FindByIdAction extends BaseAction {

    public FindByIdAction(int key, String name) {
        super(key, name);
    }

    public boolean execute(Input input, Store store, Consumer<String> output) {
        String id = input.askStr("Введите ID: ");
        Item item = store.findById(id);
        output.accept(String.format("%s, %s", item.getName(), item.getId()));
        return true;
    }
}
