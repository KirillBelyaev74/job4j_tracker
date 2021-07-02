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
        tracker.findAll(System.out::println);
        assertThat(first.getName(), is(tracker.findById(first.getId()).getName()));
    }
}
