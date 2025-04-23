package jm.task.core.jdbc.util;


import jm.task.core.jdbc.dao.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {

    // реализуйте настройку соеденения с БД

    private static final Logger logger = LoggerFactory.getLogger(Util.class);

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(Database.URL, Database.USER, Database.PASSWORD);
        if (connection != null) {
            logger.info("Подключение к postgreSQL");
        } else {
            logger.info("Ошибка подключения к postgreSQL");
        }
        return connection;
    }
}
