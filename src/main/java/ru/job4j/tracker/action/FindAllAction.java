package ru.job4j.tracker.action;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.item.Item;
import ru.job4j.tracker.store.Store;

import java.util.function.Consumer;

public class FindAllAction extends BaseAction {

    public FindAllAction(int key, String name) {
        super(key, name);
    }

    public boolean execute(Input input, Store store, Consumer<String> output) {
        store.findAll(System.out::print);
        return true;
    }
}
