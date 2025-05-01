package jm.task.core.jdbc;


import jm.task.core.jdbc.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class Main {
    public static void main(String[] args) {

        UserServiceImpl dao = new UserServiceImpl();
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
