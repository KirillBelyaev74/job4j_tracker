package ru.job4j.tracker;

import java.util.ArrayList;

public class StubInput implements Input {

    private ArrayList<String> numberMenu;

    public StubInput(ArrayList<String> numberMenu) {
        this.numberMenu = numberMenu;
    }

    public int askInt(String question) {
        return Integer.parseInt(askStr(question));
    }

    @Override
    public String askStr(String question) {
        return null;
    }

    public int askInt(String question, int range) {
        return askInt(question);
    }
}
