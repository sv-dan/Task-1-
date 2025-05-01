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
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS users";
}
