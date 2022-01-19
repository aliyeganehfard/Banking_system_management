package Model;

import java.sql.Connection;
import java.sql.DriverManager;

public class PostgresConnection {
    public static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres",
                    "postgres","ali.1381");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
