package ru.job4j.tracker;

import java.util.function.Consumer;

public class FindByNameAction extends BaseAction {

    public FindByNameAction(int key, String name) {
        super(key, name);
    }

    public boolean execute(Input input, Store store, Consumer<String> output) {
        String key = input.askStr("Введите имя: ");
        for (Item items : store.findByName(key)) {
            output.accept(String.format("%s, %s", items.getName(), items.getId()));
        }
        return true;
    }
}
