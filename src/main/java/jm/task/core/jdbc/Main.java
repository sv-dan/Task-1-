package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {
    public static void main(String[] args) {
        final Logger logger = LoggerFactory.getLogger(UserDaoHibernateImpl.class);

        UserServiceImpl dao = new UserServiceImpl();
        dao.createUsersTable();
        logger.info("");
        dao.saveUser("Петя", "Бобров", (byte) 69);
        dao.saveUser("Маша", "Сидорова", (byte) 52);
        dao.saveUser("Евгений", "Михайлов", (byte) 41);
        dao.saveUser("Дима", "Дубров", (byte) 19);
        logger.info("");
        dao.getAllUsers();
        logger.info("");
        dao.cleanUsersTable();
        logger.info("");
        dao.dropUsersTable();


    }
}
