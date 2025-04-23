package jm.task.core.jdbc.dao;

public class SQLqueries {

    public static final String CREATE_TABLE = """
                                              CREATE TABLE IF NOT EXISTS users(
                                              id SERIAL PRIMARY KEY,
                                              name VARCHAR,
                                              lastname VARCHAR,
                                              age SMALLINT
                                           )
                                          """;

    public static final String DROP_TABLE = "DROP TABLE users";

    public static final String INSERT_USER = "INSERT INTO users(name, lastname, age) VALUES(?, ?, ?)";

    public static final String DELETE_USER = "DELETE FROM users WHERE id = ?";

    public static final String SELECT_ALL_USERS = "SELECT id, name, lastname, age FROM users";

    public static final String TRUNCATE_TABLE = "TRUNCATE TABLE users";

    private void UserQueries() {

    }
}
