package cs.ubb.hrelperbe;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DatabaseConnection {

    @Value("${spring.datasource.url}")
    private String connectionString;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    public DatabaseConnection() {
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionString, username, password);
    }
}