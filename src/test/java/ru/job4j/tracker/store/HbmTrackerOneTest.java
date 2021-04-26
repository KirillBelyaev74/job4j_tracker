package ru.job4j.tracker.store;

import org.junit.Test;
import ru.job4j.tracker.item.Items;

import java.sql.Timestamp;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class HbmTrackerOneTest {

    @Test
    public void whenSaveItemsThenGetTheItems() {
        HbmTracker hbmTracker = new HbmTracker();
        Items items = hbmTracker.add(
                new Items("name", "description", new Timestamp(System.currentTimeMillis())));
        Items result = hbmTracker.findById(String.valueOf(items.getId()));
        assertThat(items.getName(), is(result.getName()));
    }

    @Test
    public void whenSaveItemsThenGetAllTheItems() {
        HbmTracker hbmTracker = new HbmTracker();
        Items one = new Items("one", "first", new Timestamp(System.currentTimeMillis()));
        Items two = new Items("two", "second", new Timestamp(System.currentTimeMillis()));
        hbmTracker.add(one);
        hbmTracker.add(two);
        List<Items> result = hbmTracker.findAll();
        assertThat(List.of(one, two), is(result));
    }

    @Test
    public void whenSaveItemsThenGetTheItemsByName() {
        HbmTracker hbmTracker = new HbmTracker();
        Items one = new Items("one", "first", new Timestamp(System.currentTimeMillis()));
        Items two = new Items("two", "second", new Timestamp(System.currentTimeMillis()));
        hbmTracker.add(one);
        hbmTracker.add(two);
        List<Items> result = hbmTracker.findByName(one.getName());
        assertThat(List.of(one), is(result));
    }

    @Test
    public void whenSaveItemsAndDeleteOneItemsThenGetAllTheItems() {
        HbmTracker hbmTracker = new HbmTracker();
        Items one = new Items("one", "first", new Timestamp(System.currentTimeMillis()));
        Items two = new Items("two", "second", new Timestamp(System.currentTimeMillis()));
        hbmTracker.add(one);
        hbmTracker.add(two);
        hbmTracker.delete(String.valueOf(one.getId()));
        List<Items> result = hbmTracker.findAll();
        assertThat(List.of(two), is(result));
    }

    @Test
    public void whenSaveItemsAndUpdateOneItemsThenGetAllTheItems() {
        HbmTracker hbmTracker = new HbmTracker();
        Items one = new Items("one", "first", new Timestamp(System.currentTimeMillis()));
        Items two = new Items("two", "second", new Timestamp(System.currentTimeMillis()));
        hbmTracker.add(one);
        hbmTracker.add(two);
        Items three = new Items("three", "third", new Timestamp(System.currentTimeMillis()));
        three.setId(one.getId());
        hbmTracker.replace(String.valueOf(one.getId()), three);
        List<Items> result = hbmTracker.findAll();
        assertThat(List.of(three, two), is(result));
    }
}
