package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class Main {
    public static void main(String[] args) {


        UserDaoJDBCImpl dao = new UserDaoJDBCImpl();
        dao.createUsersTable();
        log.info("");
        dao.saveUser("Петя", "Бобров", (byte) 69);
        dao.saveUser("Маша", "Сидорова", (byte) 52);
        dao.saveUser("Евгений", "Михайлов", (byte) 41);
        dao.saveUser("Дима", "Дубров", (byte) 19);
        log.info("");
        dao.getAllUsers();
        log.info("");
        dao.cleanUsersTable();
        log.info("");
        dao.dropUsersTable();


    }
}
