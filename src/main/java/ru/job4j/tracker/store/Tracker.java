package ru.job4j.tracker.store;

import org.springframework.stereotype.Component;
import ru.job4j.tracker.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class Tracker implements Store {
    /**
     * Массив для хранение заявок.
     */
    private final List<Item> items = new ArrayList<>();

    @Override
    public void init() {

    }

    /**
     * Метод реализаущий добавление заявки в хранилище
     *
     * @param item новая заявка
     */
    public Item add(Item item) {
        item.setId(this.generateId());
        this.items.add(item);
        return item;
    }

    /**
     * Метод удаляет найденную заявку
     *
     * @param id - id заявки которую нужно удалить
     * @return - true or false
     */
    public boolean delete(String id) {
        boolean result = false;
        for (int index = 0; index != this.items.size(); index++) {
            if (this.items.get(index).getId().equals(id)) {
                this.items.remove(index);
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Метод заменяет найденую заявку
     *
     * @param id   - id заявки, которую нужно найти
     * @param item - новыя заявка
     * @return - true or false
     */
    public boolean replace(String id, Item item) {
        boolean result = false;
        for (int index = 0; index != this.items.size(); index++) {
            if (this.items.get(index).getId().equals(id)) {
                this.items.set(index, item);
                this.items.get(index).setId(id);
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Метод возвращает все заполненые items
     *
     * @return - массив без null элементов
     */
    public List<Item> findAll() {
        return this.items;
    }

    /**
     * Метод находит заявку по имени
     *
     * @param key - имя заявки
     * @return - найденная заявка
     */
    public List<Item> findByName(String key) {
        List<Item> result = new ArrayList<>();
        for (Item item : this.items) {
            if (item.getName().equals(key)) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * Метод находит заявку по id
     *
     * @param id - id заявки
     * @return - найденная заявка
     */
    public Item findById(String id) {
        Item result = null;
        for (Item item : this.items) {
            if (item.getId().equals(id)) {
                result = item;
                result.setId(id);
                break;
            }
        }
        return result;
    }

    /**
     * Метод генерирует уникальный ключ для заявки.
     * Так как у заявки нет уникальности полей, имени и описание. Для идентификации нам нужен уникальный ключ.
     *
     * @return Уникальный ключ.
     */
    private String generateId() {
        Random rm = new Random();
        return String.valueOf(rm.nextLong() + System.currentTimeMillis());
    }

    @Override
    public void close() throws Exception {

    }
}