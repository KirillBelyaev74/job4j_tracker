package ru.job4j.tracker;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class SqlTracker implements Store {

    private Connection connection;

    public SqlTracker(Connection connection) {
        this.connection = connection;
    }

    public void init() {
        try (InputStream in = SqlTracker.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("db.driver"));
            this.connection = DriverManager.getConnection(
                    config.getProperty("db.url"),
                    config.getProperty("db.username"),
                    config.getProperty("db.password"));
            this.createTable();
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

    public void createTable() {
        try {
            Statement statement = connection.createStatement();
            statement.execute(
                    "create table if not exists items(id serial primary key, name varchar(20) not null)");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Item add(Item item) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(
                "insert into items(name) values (initcap(?));", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.execute();
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
    public List<Item> findAll() {
        List<Item> list = new LinkedList<>();
        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from items;");
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

    public static void main(String[] args) {
        Input validate = new ValidateInput(new ConsoleInput());
        try (Store tracker = new SqlTracker(null)) {
            tracker.init();
            ArrayList<BaseAction> actions = new ArrayList<>();
            actions.add(new CreateAction(0, "Добавление"));
            actions.add(new ReplaceAction(1, "Редактирование"));
            actions.add(new DeleteAction(2, "Удаление"));
            actions.add(new FindAllAction(3, "Показать все"));
            actions.add(new FindByNameAction(4, "Найти по имени"));
            actions.add(new FindByIdAction(5, "Найти по ID"));
            new StartUI(validate, tracker, System.out::println).init(actions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}