package cs.ubb.hrelperbe.Implementations;

import cs.ubb.hrelperbe.BaseModels.*;
import cs.ubb.hrelperbe.DatabaseConnection;
import cs.ubb.hrelperbe.Interfaces.JobRepositoryInterface;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class JobRepositoryImplementation implements JobRepositoryInterface {

    private final DatabaseConnection databaseConnection;

    public JobRepositoryImplementation(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    private void saveSkillsOfAJob(Connection connection, Integer jobId, List<MustHaveSkill> skills) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO \"MustHaveSkills\"(description, \"jobId\") VALUES (?, ?)"
        )) {
            for (MustHaveSkill skill : skills) {
                statement.setString(1, skill.getDescription());
                statement.setInt(2, jobId);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveGuideOfAJob(Connection connection, Integer jobId, List<InterviewGuideQuestion> guideQuestions) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO \"InterviewGuideQuestions\"(description, \"jobId\") VALUES (?, ?)"
        )) {
            for (InterviewGuideQuestion question : guideQuestions) {
                statement.setString(1, question.getDescription());
                statement.setInt(2, jobId);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveJobTechStack(Connection connection, Integer jobId, List<Technology> technologies) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO \"JobTechStack\"(\"jobId\", \"technologyId\") VALUES (?, ?)"
        )) {
            for (Technology tech : technologies) {
                statement.setInt(1, jobId);
                statement.setInt(2, tech.getTechnologyId());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Job job) {
        try (Connection connection = databaseConnection.getConnection()) {

            connection.setAutoCommit(false); // start transaction

            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO \"Jobs\"(\"roleId\", description) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            )) {

                statement.setInt(1, job.getRole().getRoleId());
                statement.setString(2, job.getDescription());

                int cols = statement.executeUpdate();
                if (cols == 0) {
                    throw new RuntimeException("Database error");
                }

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int jobId = generatedKeys.getInt(1);

                        saveSkillsOfAJob(connection, jobId, job.getMustHaveSkills());
                        saveGuideOfAJob(connection, jobId, job.getInterviewGuideQuestions());
                        saveJobTechStack(connection, jobId, job.getTechnologies());
                    }
                }

                connection.commit(); // success

            } catch (Exception e) {
                connection.rollback(); // rollback on error
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteJobMustHaveSkills(Connection connection, Integer jobId) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "DELETE FROM \"MustHaveSkills\" WHERE \"jobId\" = ?"
        )) {
            stmt.setInt(1, jobId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteJobInterviewGuideQuestions(Connection connection, Integer jobId) {
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM \"InterviewGuideQuestions\" WHERE \"jobId\" = ?"
        )) {
            statement.setInt(1, jobId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteJobTechnicalStack(Connection connection, Integer jobId) {
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM \"JobTechStack\" WHERE \"jobId\" = ?"
        )) {
            statement.setInt(1, jobId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void update(Job job) {
        try (Connection connection = databaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {

                try (PreparedStatement statement = connection.prepareStatement(
                        "UPDATE \"Jobs\" SET \"roleId\" = ?, description = ? WHERE \"jobId\" = ?"
                )) {
                    statement.setInt(1, job.getRole().getRoleId());
                    statement.setString(2, job.getDescription());
                    statement.setInt(3, job.getJobId());

                    int rows = statement.executeUpdate();
                    if (rows == 0) {
                        throw new RuntimeException("Job not found!");
                    }
                }


                deleteJobMustHaveSkills(connection, job.getJobId());
                deleteJobInterviewGuideQuestions(connection, job.getJobId());
                deleteJobTechnicalStack(connection, job.getJobId());


                saveSkillsOfAJob(connection, job.getJobId(), job.getMustHaveSkills());
                saveGuideOfAJob(connection, job.getJobId(), job.getInterviewGuideQuestions());
                saveJobTechStack(connection, job.getJobId(), job.getTechnologies());

                connection.commit();

            } catch (Exception e) {
                connection.rollback();
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
