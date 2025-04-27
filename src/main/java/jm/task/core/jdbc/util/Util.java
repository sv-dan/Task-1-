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
    public final static String URL_KEY = "db.url";
    public final static String USER_KEY = "db.user";
    public final static String PASSWORD_KEY = "db.password";

    private static SessionFactory sessionFactory;

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(PropertiesUtil.getProperty(URL_KEY),PropertiesUtil.getProperty (USER_KEY),PropertiesUtil.getProperty(PASSWORD_KEY));
        if (connection != null) {
            log.info("Подключение к postgreSQL");
        } else {
            log.info("Ошибка подключения к postgreSQL");
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {

            try {
                Configuration config = new Configuration();
                config.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
                config.setProperty("hibernate.connection.url", PropertiesUtil.getProperty(URL_KEY));
                config.setProperty("hibernate.connection.username", PropertiesUtil.getProperty(USER_KEY));
                config.setProperty("hibernate.connection.password", PropertiesUtil.getProperty(PASSWORD_KEY));
                config.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
                config.setProperty("hibernate.show_sql", "true");
                config.setProperty("hibernate.hbm2ddl.auto", "create");

                config.addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(config.getProperties())
                        .build();

                sessionFactory = config.buildSessionFactory(serviceRegistry);
                log.info("sessionFactory для hibernate создан");
            } catch (CustomException e) {
                throw new CustomException(e.getMessage());
            }
            return sessionFactory;
        }
        return sessionFactory;
    }
}
