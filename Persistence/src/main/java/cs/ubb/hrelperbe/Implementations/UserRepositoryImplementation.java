package cs.ubb.hrelperbe.Implementations;

import cs.ubb.hrelperbe.BaseModels.User;
import cs.ubb.hrelperbe.Constants.UserType;
import cs.ubb.hrelperbe.DatabaseConnection;
import cs.ubb.hrelperbe.Interfaces.UserRepositoryInterface;
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
            if (resultSet.next()){
                Integer userId = resultSet.getInt("userId");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String password = resultSet.getString("password");
                String type = resultSet.getString("type");
                UserType userType = UserType.valueOf(type);
                User user = new User(userId, name, surname, email, password, userType);
                return user;
            }
            else{
                throw new RuntimeException("User doesn't exist.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
