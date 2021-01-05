package ru.job4j.tracker;

import java.util.Scanner;

public class ConsoleInput implements Input {

    private Scanner scanner = new Scanner(System.in);

    /**
     * Метод запрашивает у пользователя выбрать пунк меню
     *
     * @param question - вопрос пользователю
     * @return - введенный пользователем пункт меню
     */
    public int askInt(String question) {
        return Integer.parseInt(askStr(question));
    }

    public int askInt(String question, int range) {
        int select = askInt(question);
        if (!this.check(select, range)) {
            throw new MenuOutException(String.format("Out of about %s > [0, %s]", select, range));
        }
        return select;
    }

    public String askStr(String question) {
        System.out.println(question);
        return this.scanner.nextLine();
    }

    public boolean check(int select, int max) {
        return 0 <= select && select < max;
    }

}
