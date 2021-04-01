package ru.job4j.tracker.input;

import ru.job4j.tracker.MenuOutException;

public class ValidateInput implements Input {
    private final Input input;

    public ValidateInput(Input input) {
        this.input = input;
    }

    @Override
    public String askStr(String question) {
        return input.askStr(question);
    }

    @Override
    public int askInt(String question, int range) {
        boolean invalid = true;
        int value = -1;
        do {
            try {
                value = input.askInt(question, range);
                invalid = false;
            } catch (MenuOutException moe) {
                System.out.println("Выберите пункт из меню!");
            } catch (NumberFormatException nfe) {
                System.out.println(String.format("%s", "Выберите пункт из меню!"));
            }
        } while (invalid);
        return value;
    }

    @Override
    public int askInt(String question) {
        return 0;
    }
}