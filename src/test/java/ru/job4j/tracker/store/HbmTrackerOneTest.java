package ru.job4j.tracker.store;

import org.junit.After;
import org.junit.Test;
import ru.job4j.tracker.item.Items;

import java.sql.Timestamp;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class HbmTrackerOneTest {

    private final HbmTracker hbmTracker = new HbmTracker();

    @After
    public void finish() {
        hbmTracker.clearTable();
    }

    @Test
    public void whenSaveItemsThenGetAllTheItems() {
        Items one = new Items("one", "first", new Timestamp(System.currentTimeMillis()));
        Items two = new Items("two", "second", new Timestamp(System.currentTimeMillis()));
        hbmTracker.add(one);
        hbmTracker.add(two);
        List<Items> result = hbmTracker.findAll(null);
        assertThat(List.of(one, two), is(result));
    }

    @Test
    public void whenSaveItemsThenGetTheItems() {
        Items items = hbmTracker.add(
                new Items("name", "description", new Timestamp(System.currentTimeMillis())));
        Items result = hbmTracker.findById(String.valueOf(items.getId()));
        assertThat(items.getName(), is(result.getName()));
    }

    @Test
    public void whenSaveItemsThenGetTheItemsByName() {
        Items one = new Items("one", "first", new Timestamp(System.currentTimeMillis()));
        Items two = new Items("two", "second", new Timestamp(System.currentTimeMillis()));
        hbmTracker.add(one);
        hbmTracker.add(two);
        List<Items> result = hbmTracker.findByName(one.getName());
        assertThat(List.of(one), is(result));
    }

    @Test
    public void whenSaveItemsAndDeleteOneItemsThenGetAllTheItems() {
        Items one = new Items("one", "first", new Timestamp(System.currentTimeMillis()));
        hbmTracker.add(one);
        hbmTracker.delete(String.valueOf(one.getId()));
        Items result = hbmTracker.findById(String.valueOf(one.getId()));
        assertNull(result);
    }

    @Test
    public void whenSaveItemsAndUpdateOneItemsThenGetAllTheItems() {
        Items one = new Items("one", "first", new Timestamp(System.currentTimeMillis()));
        hbmTracker.add(one);
        Items three = new Items("three", "third", new Timestamp(System.currentTimeMillis()));
        three.setId(one.getId());
        hbmTracker.replace(String.valueOf(one.getId()), three);
        Items result = hbmTracker.findById(String.valueOf(three.getId()));
        assertThat(result, is(three));
    }
}
