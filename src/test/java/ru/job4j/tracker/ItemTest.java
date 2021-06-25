package ru.job4j.tracker;

import org.junit.Test;
import ru.job4j.tracker.item.Item;
import ru.job4j.tracker.store.Tracker;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ItemTest {
    @Test
    public void whenFirstSecondThenFirstSecond() {
        Item first = new Item("Petr");
        Item second = new Item("Ivan");
        Item third = new Item("Artem");
        Tracker tracker = new Tracker();
        tracker.add(first);
        tracker.add(second);
        tracker.add(third);
        List<Item> result = tracker.findAll(null);
        Collections.sort(result);
        List<Item> expect = Arrays.asList(third, second, first);
        assertThat(result, is(expect));
    }
    @Test
    public void whenFirstSecondThenSecondFirst() {
        Item first = new Item("Artem");
        Item second = new Item("Petr");
        Item third = new Item("Ivan");
        Tracker tracker = new Tracker();
        tracker.add(first);
        tracker.add(second);
        tracker.add(third);
        List<Item> result = tracker.findAll(null);
        Collections.sort(result, Collections.reverseOrder());
        List<Item> expect = Arrays.asList(second, third, first);
        assertThat(result, is(expect));
    }
}
