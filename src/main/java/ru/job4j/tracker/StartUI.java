package ru.job4j.tracker;

import org.hibernate.Transaction;
import ru.job4j.tracker.action.*;
import ru.job4j.tracker.input.ConsoleInput;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.input.ValidateInput;
import ru.job4j.tracker.store.Context;
import ru.job4j.tracker.store.Store;
import ru.job4j.tracker.store.Tracker;

import java.util.ArrayList;
import java.util.function.Consumer;

public class StartUI {

    private final Input input;
    private final Store store;
    private final Consumer<String> output;

    public StartUI(Input input, Store store, Consumer<String> output) {
        this.input = input;
        this.store = store;
        this.output = output;
    }

    public void init(ArrayList<BaseAction> actions) {
        boolean run = true;
        while (run) {
            this.showMenu(actions);
            int select = this.input.askInt("Выбор: ", actions.size());
            UserAction action = actions.get(select);
            run = action.execute(this.input, this.store, this.output);
        }
    }

    private void showMenu(ArrayList<BaseAction> actions) {
        System.out.println("Меню: ");
        for (BaseAction action : actions) {
            System.out.println(action.info());
        }
    }

    public static void main(String[] args) {
        Context context = new Context();
        context.reg(ConsoleInput.class);
        context.reg(ValidateInput.class);
        context.reg(Tracker.class);

        Input validate = context.get(ValidateInput.class);
        Tracker tracker = context.get(Tracker.class);

        ArrayList<BaseAction> actions = new ArrayList<>();
        actions.add(new CreateAction(0, "Добавление"));
        actions.add(new ReplaceAction(1, "Редактирование"));
        actions.add(new DeleteAction(2, "Удаление"));
        actions.add(new FindAllAction(3, "Показать все"));
        actions.add(new FindByNameAction(4, "Найти по имени"));
        actions.add(new FindByIdAction(5, "Найти по ID"));
        new StartUI(validate, tracker, System.out::println).init(actions);
    }
}