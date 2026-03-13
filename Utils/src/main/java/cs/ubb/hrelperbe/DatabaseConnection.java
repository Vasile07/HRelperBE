package cs.ubb.hrelperbe;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DatabaseConnection {
    @Value(value = "${spring.datasource.url}")
    private String connectionString;
    private static Connection connection;

    public DatabaseConnection() {

    }

    public Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(connectionString);
        }
        return connection;
    }
}
