package jm.task.core.jdbc.util;


import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class Util {

    public static final String URL = "db.url";
    public static final String USER = "db.user";
    public static final String PASSWORD = "db.password";

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(PropertiesUtil.get(URL), PropertiesUtil.get(USER), PropertiesUtil.get(PASSWORD));
        if (connection != null) {
            log.info("Подключение к postgreSQL");
        } else {
            log.info("Ошибка подключения к postgreSQL");
        }
        return connection;
    }
}
