package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final static Logger logger = LoggerFactory.getLogger(UserDaoJDBCImpl.class);
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String createTable = SQLqueries.CREATE_TABLE;
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            logger.info(String.valueOf(statement.execute(createTable)));
        } catch (CustomException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String dropTable = SQLqueries.DROP_TABLE;
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(dropTable);
            logger.info("Таблица 'users' успешно удалена (или не существовала).");
        } catch (CustomException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String saveUser = SQLqueries.INSERT_USER;
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(saveUser)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            logger.info("User с именем - " + name + " добавлен в базу данных");

        } catch (CustomException | SQLException e) {
            e.printStackTrace();
        }

    }

    public void removeUserById(long id) {
        String removeById = SQLqueries.DELETE_USER;
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(removeById)) {
            preparedStatement.setLong(1, id);
            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                logger.info("Пользователь с id = " + id + " удален из базы");
            } else {
               logger.info("Пользователь с id = " + id + " не найден в базе");
            }
        } catch (CustomException | SQLException e) {
            System.out.println("Ошибка при удаление пользователя с id = " + id + ":");
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String allUsers = SQLqueries.SELECT_ALL_USERS;
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(allUsers)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (CustomException | SQLException e) {
            e.printStackTrace();
        }
        for (User user : users) {
            logger.info("User: {}", user);
        }
        return users;
    }

    public void cleanUsersTable() {
        String cleanTable = SQLqueries.TRUNCATE_TABLE;
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(cleanTable);
        } catch (CustomException | SQLException e) {
            e.printStackTrace();
        }
    }
}
