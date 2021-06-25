package ru.job4j.tracker.store;

import org.junit.After;
import org.junit.Test;
import ru.job4j.tracker.item.Items;

import java.sql.Timestamp;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class HbmTrackerTest {

    private final HbmTracker tracker = new HbmTracker();
    private final Items first = new Items("first", "descOne", new Timestamp(System.currentTimeMillis()));
    private final Items second = new Items("second", "descTwo", new Timestamp(System.currentTimeMillis()));
    private final Items third = new Items("third", "descThree", new Timestamp(System.currentTimeMillis()));

    @After
    public void finish() {
        tracker.action(session -> {
            session.createQuery("delete from Items").executeUpdate();
            return null;
        });
    }

    @Test
    public void whenAddItemsThenFindAllTheItems() {
        tracker.add(first);
        tracker.add(second);
        tracker.add(third);
        List<Items> items = tracker.findAll(null);
        assertThat(items.size(), is(3));
    }

    @Test
    public void whenReplaceNameDescOfItemsThenGetById() {
        tracker.add(first);
        tracker.replace(String.valueOf(first.getId()), second);
        Items items = tracker.findById(String.valueOf(first.getId()));
        assertThat(items.getName(), is("second"));
    }

    @Test
    public void whenDeleteItemWithIdFive() {
        tracker.add(first);
        tracker.delete(String.valueOf(first.getId()));
        Items result = tracker.findById(String.valueOf(first.getId()));
        assertNull(result);
    }

    @Test
    public void whenFindByNameOfItem() {
        tracker.add(second);
        List<Items> items =  tracker.findByName(second.getName());
        assertThat(items.get(0).getName(), is(second.getName()));
    }
}