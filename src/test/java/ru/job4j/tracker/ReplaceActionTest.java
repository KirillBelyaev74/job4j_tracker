package ru.job4j.tracker;

import org.junit.Test;
import ru.job4j.tracker.action.BaseAction;
import ru.job4j.tracker.action.ReplaceAction;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.input.ValidateInput;
import ru.job4j.tracker.item.Item;
import ru.job4j.tracker.store.Tracker;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReplaceActionTest {

    @Test
    public void whenReplaceNameOfItem() {
        Tracker tracker = new Tracker();
        Item item = new Item("Replaced item");
        tracker.add(item);
        String replacedName = "New item name";
        BaseAction baseAction = new ReplaceAction(1, "Редактирование");
        Input input = mock(ValidateInput.class);
        when(input.askStr(any(String.class))).thenReturn(item.getId(), replacedName);
        baseAction.execute(input, tracker, null);
        assertThat(tracker.findById(item.getId()).getName(), is(replacedName));
    }
}