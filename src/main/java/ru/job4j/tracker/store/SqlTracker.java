package ru.job4j.tracker.store;

import org.springframework.stereotype.Component;
import ru.job4j.tracker.item.Item;

import java.io.InputStream;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;

public class SqlTracker implements Store {

    private Connection connection;

    public void init() {
        try (InputStream inputStream = SqlTracker.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            Class.forName(properties.getProperty("driver"));
            this.connection = DriverManager.getConnection(
                    properties.getProperty("url"),
                    properties.getProperty("username"),
                    properties.getProperty("password"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    @Override
    public Item add(Item item) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(
                "insert into items(name) values (initcap(?))", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                item.setId(String.valueOf(resultSet.getInt("id")));
                item.setName(resultSet.getString("name"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return item;
    }

    @Override
    public boolean replace(String id, Item item) {
        int result = -1;
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(
                "update items set name = initcap(?) where id = ?;")) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.setInt(2, Integer.parseInt(id));
            result = preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result == 1;
    }

    @Override
    public boolean delete(String id) {
        int result = -1;
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(
                "delete from items where id = ?;")) {
            preparedStatement.setInt(1, Integer.parseInt(id));
            result = preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result == 1;
    }

    @Override
    public void findAll(Consumer<String> consumer) {
        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from items;");
            while (resultSet.next()) {
                Item item = new Item(resultSet.getString("name"));
                item.setId(String.valueOf(resultSet.getInt("id")));
                consumer.accept(String.format("%s, %s", item.getName(), item.getId()));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Item> findByName(String key) {
        List<Item> list = new LinkedList<>();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(
                "select * from items where name = initcap(?);")) {
            preparedStatement.setString(1, key);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Item item = new Item(resultSet.getString("name"));
                item.setId(String.valueOf(resultSet.getInt("id")));
                list.add(item);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    @Override
    public Item findById(String id) {
        Item item = null;
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(
                "select * from items where id = ?;")) {
            preparedStatement.setInt(1, Integer.parseInt(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                item = new Item(resultSet.getString("name"));
                item.setId(String.valueOf(resultSet.getInt("id")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return item;
    }
}