package ru.job4j.tracker;
import org.junit.Test;
import ru.job4j.tracker.item.Item;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SqlTrackerTest {

    @Test
    public void createItem() {
        try {
            SqlTracker tracker = new SqlTracker();
            tracker.init();
            tracker.add(new Item("100"));
            assertThat(tracker.findByName("100").get(0).getName(), is("100"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
