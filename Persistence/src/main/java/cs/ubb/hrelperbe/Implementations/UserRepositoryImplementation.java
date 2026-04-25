package cs.ubb.hrelperbe.Implementations;

import cs.ubb.hrelperbe.BaseModels.User;
import cs.ubb.hrelperbe.Constants.UserType;
import cs.ubb.hrelperbe.CustomException;
import cs.ubb.hrelperbe.DatabaseConnection;
import cs.ubb.hrelperbe.Interfaces.UserRepositoryInterface;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepositoryImplementation implements UserRepositoryInterface {
    private final DatabaseConnection databaseConnection;

    public UserRepositoryImplementation(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public User getUserByEmail(String email) {
        try (
                Connection connection = databaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("select * from \"Users\" where email like ?");
        ) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Integer userId = resultSet.getInt("userId");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String password = resultSet.getString("password");
                String type = resultSet.getString("type");
                UserType userType = UserType.valueOf(type);
                User user = new User(userId, name, surname, email, password, userType);
                return user;
            } else {
                throw new CustomException("Invalid credentials", HttpStatus.NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserById(Integer userId) {
        try (
                Connection connection = databaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("select * from \"Users\" where \"userId\" = ?");
        ) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String type = resultSet.getString("type");
                UserType userType = UserType.valueOf(type);
                return new User(userId, name, surname, email, password, userType);
            } else {
                throw new RuntimeException("User doesn't exist.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean emailExists(String email) {
        Connection connection = null;
        try {
            connection = databaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (

                PreparedStatement statement = connection.prepareStatement("select * from \"Users\" where email like ?")
        ) {
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(User user) {
        Connection connection = null;
        try {
            connection = databaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (
                PreparedStatement statement = connection.prepareStatement("insert into \"Users\" (name, surname, email, password, type) values (?, ?, ?, ?, ?)");
        ) {
            if (emailExists(user.getEmail())) {
                throw new CustomException("A user with this email already exists!", HttpStatus.CONFLICT);
            }
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getType().toString());

            int cols = statement.executeUpdate();
            if (cols == 0){
                throw new RuntimeException("Database error!");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
