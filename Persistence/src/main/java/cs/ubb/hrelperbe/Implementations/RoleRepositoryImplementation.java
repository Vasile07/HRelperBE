package cs.ubb.hrelperbe.Implementations;

import cs.ubb.hrelperbe.BaseModels.Department;
import cs.ubb.hrelperbe.BaseModels.Role;
import cs.ubb.hrelperbe.DatabaseConnection;
import cs.ubb.hrelperbe.Interfaces.RoleRepositoryInterface;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoleRepositoryImplementation implements RoleRepositoryInterface {

    private final DatabaseConnection databaseConnection;

    public RoleRepositoryImplementation(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }
    private Department getDepartmentById(Integer departmentId) {
        Connection connection = null;
        try {
            connection = databaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("select * from \"Departments\" where \"departmentId\" = ?");
        ) {
            preparedStatement.setInt(1, departmentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                Department department = new Department(departmentId, name);
                return department;
            }
            else{
                throw new RuntimeException("Role doesn't exist");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Role getRoleById(Integer roleId) {
        Connection connection = null;
        try {
            connection = databaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("select * from \"Roles\" where \"roleId\" = ?");
        ) {
            preparedStatement.setInt(1, roleId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
               String name = resultSet.getString("name");
               Integer departmentId = resultSet.getInt("departmentId");
               Department department = getDepartmentById(departmentId);
               Role role = new Role(roleId, name, department);
               return role;
            }
            else{
                throw new RuntimeException("Role doesn't exist");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Role> getAllRoles() {
        Connection connection = null;
        try {
            connection = databaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String query = "select r.\"roleId\", r.\"name\" as roleName, d.\"departmentId\", d.\"name\" as departmentName from \"Roles\" r join \"Departments\" d on r.\"departmentId\" = d.\"departmentId\" order by r.\"roleId\"";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            List<Role> roles = new ArrayList<>();
            while (resultSet.next()) {
                Integer roleId = resultSet.getInt("roleId");
                String roleName = resultSet.getString("roleName");
                Integer departmentId = resultSet.getInt("departmentId");
                String departmentName = resultSet.getString("departmentName");
                Department department = new Department(departmentId, departmentName);
                roles.add(new Role(roleId, roleName, department));
            }
            return roles;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
