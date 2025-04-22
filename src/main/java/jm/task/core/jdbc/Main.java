package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {

        UserServiceImpl dao = new UserServiceImpl();
        dao.createUsersTable();
        System.out.println();
        dao.saveUser("Петя", "Бобров", (byte) 69);
        dao.saveUser("Маша", "Сидорова", (byte) 52);
        dao.saveUser("Евгений", "Михайлов", (byte) 41);
        dao.saveUser("Дима", "Дубров", (byte) 19);
        System.out.println();
        dao.getAllUsers();
        System.out.println();
        dao.cleanUsersTable();
        System.out.println();
        dao.dropUsersTable();


    }
}
