package cs.ubb.hrelperbe.Implementations;

import cs.ubb.hrelperbe.BaseModels.Department;
import cs.ubb.hrelperbe.BaseModels.Technology;
import cs.ubb.hrelperbe.DatabaseConnection;
import cs.ubb.hrelperbe.Interfaces.TechnologyRepositoryInterface;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class TechnologyRepositoryImplementation implements TechnologyRepositoryInterface {
    private final DatabaseConnection databaseConnection;

    public TechnologyRepositoryImplementation(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public Technology getTechnologyById(Integer technologyId) {
        Connection connection = null;
        try {
            connection = databaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("select * from \"Technologies\" where \"technologyId\" = ?");
        ) {
            preparedStatement.setInt(1, technologyId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                Technology technology = new Technology(technologyId, name, description);
                return technology;
            }
            else{
                throw new RuntimeException("Role doesn't exist");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
