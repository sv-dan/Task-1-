package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    // реализуйте настройку соеденения с БД
    private static String url = "jdbc:postgresql://localhost:5432/postgres";
    private static String user = "postgres";
    private static String password = "zadedi92";
    private static SessionFactory sessionFactory;

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);
        if (connection != null) {
            System.out.println("Подключение к postgreSQL");
        } else {
            System.out.println("Ошибка подключения к postgreSQL");
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {

            try {
                Configuration config = new Configuration();
                config.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
                config.setProperty("hibernate.connection.url", url);
                config.setProperty("hibernate.connection.username", user);
                config.setProperty("hibernate.connection.password", password);
                config.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
                config.setProperty("hibernate.show_sql", "true");
                config.setProperty("hibernate.hbm2ddl.auto", "create");

                config.addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(config.getProperties())
                        .build();

                sessionFactory = config.buildSessionFactory(serviceRegistry);
                System.out.println("sessionFactory для hibernate создан");
            } catch (Exception e) {
                System.out.println("sessionFactory для hibernate не создан");
                e.printStackTrace();
            }
            return sessionFactory;
        }
        return sessionFactory;
    }
}
