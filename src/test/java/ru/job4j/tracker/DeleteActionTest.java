package ru.job4j.tracker;

import org.junit.Test;
import ru.job4j.tracker.action.BaseAction;
import ru.job4j.tracker.action.DeleteAction;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.input.ValidateInput;
import ru.job4j.tracker.item.Item;
import ru.job4j.tracker.store.Store;
import ru.job4j.tracker.store.Tracker;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeleteActionTest {

    @Test
    public void whenDeleteAction() {
        Store tracker = new Tracker();
        Item item = new Item("Item");
        tracker.add(item);
        BaseAction baseAction = new DeleteAction(2, "Удаление");
        Input input = mock(ValidateInput.class);
        when(input.askStr(any(String.class))).thenReturn(item.getId());
        baseAction.execute(input, tracker, null);
        assertTrue(tracker.findAll(null).isEmpty());
    }
}