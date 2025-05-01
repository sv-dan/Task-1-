package jm.task.core.jdbc.util;


import jm.task.core.jdbc.dao.CustomException;
import jm.task.core.jdbc.dao.PropertiesUtil;
import jm.task.core.jdbc.model.User;


import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class Util {
    private static final String URL_KEY = "db.url";
    private static final String USER_KEY = "db.user";
    private static final String PASSWORD_KEY = "db.password";
    private static SessionFactory sessionFactory;



    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(
                    PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USER_KEY),
                    PropertiesUtil.get(PASSWORD_KEY));
            log.info("Соединение успешно установлено");
            return connection;
        } catch (SQLException ex) {
            log.error("Не удалось установить соединение с СУБД");
            throw new CustomException(ex.getMessage());
        }
    }

    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
                configuration.setProperty("hibernate.connection.url", PropertiesUtil.get(URL_KEY));
                configuration.setProperty("hibernate.connection.username", PropertiesUtil.get(USER_KEY));
                configuration.setProperty("hibernate.connection.password", PropertiesUtil.get(PASSWORD_KEY));

                configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
                configuration.setProperty("hibernate.hbm2ddl.auto", "create");
                configuration.setProperty("hibernate.show_sql", "true");

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                log.info("sessionFactory для Hibernate успешно добавлена");

            } catch (Exception ex) {
                log.error("sessionFactory для Hibernate не добавлена");
                throw new CustomException(ex.getMessage());
            }
            return sessionFactory;
        }
        return sessionFactory;
    }
}
