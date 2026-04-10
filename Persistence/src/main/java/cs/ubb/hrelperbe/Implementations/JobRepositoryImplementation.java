package cs.ubb.hrelperbe.Implementations;

import cs.ubb.hrelperbe.BaseModels.*;
import cs.ubb.hrelperbe.Constants.UserType;
import cs.ubb.hrelperbe.DatabaseConnection;
import cs.ubb.hrelperbe.Interfaces.JobRepositoryInterface;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Stack;

@Repository
public class JobRepositoryImplementation implements JobRepositoryInterface {

    private final DatabaseConnection databaseConnection;

    public JobRepositoryImplementation(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    private void saveSkillsOfAJob(Integer jobId, List<MustHaveSkill> skills){
        Connection connection = null;
        try {
            connection = databaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (
                PreparedStatement statement = connection.prepareStatement("insert into \"MustHaveSkills\"(description, \"jobId\") values (?, ?)");
        ) {
            for (MustHaveSkill skill : skills){
                statement.setString(1, skill.getDescription());
                statement.setInt(2, jobId);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveGuideOfAJob(Integer jobId, List<InterviewGuideQuestion> guideQuestions){
        Connection connection = null;
        try {
            connection = databaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (
                PreparedStatement statement = connection.prepareStatement("insert into \"InterviewGuideQuestions\"(description, \"jobId\") values (?, ?)");
        ) {
            for (InterviewGuideQuestion question : guideQuestions){
                statement.setString(1, question.getDescription());
                statement.setInt(2, jobId);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveJobTechStack(Integer jobId, List<Technology> technologies){
        Connection connection = null;
        try {
            connection = databaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (
                PreparedStatement statement = connection.prepareStatement("insert into \"JobTechStack\"(\"jobId\", \"technologyId\") values (?, ?)");
        ) {
            for (Technology technology : technologies){
                statement.setInt(1, jobId);
                statement.setInt(2, technology.getTechnologyId());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Job job) {
        Connection connection = null;
        try {
            connection = databaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (
                PreparedStatement statement = connection.prepareStatement("insert into \"Jobs\"(\"roleId\", description) values (?, ?)", Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setInt(1, job.getRole().getRoleId());
            statement.setString(2, job.getDescription());

            int cols = statement.executeUpdate();
            if (cols == 0){
                throw new RuntimeException("Database error");
            }
            else{
                try(ResultSet generatedKeys = statement.getGeneratedKeys()){
                    if (generatedKeys.next()){
                        int jobId = generatedKeys.getInt(1);
                        saveSkillsOfAJob(jobId, job.getMustHaveSkills());
                        saveGuideOfAJob(jobId, job.getInterviewGuideQuestions());
                        saveJobTechStack(jobId, job.getTechnologies());
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
