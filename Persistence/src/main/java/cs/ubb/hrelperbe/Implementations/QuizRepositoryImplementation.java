package cs.ubb.hrelperbe.Implementations;

import cs.ubb.hrelperbe.BaseModels.Answer;
import cs.ubb.hrelperbe.BaseModels.Question;
import cs.ubb.hrelperbe.DatabaseConnection;
import cs.ubb.hrelperbe.Interfaces.QuizRepositoryInterface;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class QuizRepositoryImplementation implements QuizRepositoryInterface {

    private final DatabaseConnection databaseConnection;

    public QuizRepositoryImplementation(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    private List<Integer> getTechnologyIdsForJob(Connection connection, Integer jobId) {
        List<Integer> technologyIds = new ArrayList<>();
        try (
                PreparedStatement statement = connection.prepareStatement("select \"technologyId\" from \"JobTechStack\" where \"jobId\" = ?")
        ) {
            statement.setInt(1, jobId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                technologyIds.add(resultSet.getInt("technologyId"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return technologyIds;
    }


    private Integer getRoleIdForJob(Connection connection, Integer jobId) {
        try (
                PreparedStatement statement = connection.prepareStatement("select \"roleId\" from \"Jobs\" where \"jobId\" = ?")
        ) {
            statement.setInt(1, jobId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("roleId");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private Question mapQuestion(ResultSet rs) throws SQLException {
        Question q = new Question();
        q.setQuestionId(rs.getInt("questionId"));
        q.setText(rs.getString("text"));
        return q;
    }

    private List<Question> getQuestionsByTehnologies(Connection connection, List<Integer> technologyIds) {
        List<Question> questions = new ArrayList<>();
        if (technologyIds.isEmpty()) return questions;

        String placeholders = technologyIds.stream().map(t -> "?").collect(Collectors.joining(", "));
        try (
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"Questions\" WHERE \"technologyId\" IN (" + placeholders + ")")) {
            for (int i = 0; i < technologyIds.size(); i++) {
                statement.setInt(i + 1, technologyIds.get(i));
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                questions.add(mapQuestion(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return questions;
    }

    private List<Question> getQuestionsByRole(Connection connection, Integer roleId) {
        List<Question> questions = new ArrayList<>();
        try (
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"Questions\" WHERE  \"roleId\" = ?")) {
            statement.setInt(1, roleId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                questions.add(mapQuestion(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return questions;
    }

    private List<Answer> getAnswersForQuestion(Connection connection, Integer questionId) {
        List<Answer> answers = new ArrayList<>();
        try (
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"Answers\" WHERE  \"questionId\" = ?")) {
            statement.setInt(1, questionId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Answer answer = new Answer();
                answer.setAnswerId(resultSet.getInt("answerId"));
                answer.setText(resultSet.getString("text"));
                answer.setCorrect(resultSet.getBoolean("correct"));
                answers.add(answer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return answers;
    }

    private List<Question> selectFiveRandomQuestions(List<Question> techQuestions, List<Question> roleQuestions) {
        Collections.shuffle(techQuestions);
        Collections.shuffle(roleQuestions);

        List<Question> selectedQuestions = new ArrayList<>();

        if (!roleQuestions.isEmpty()) {
            selectedQuestions.add(roleQuestions.remove(0));
        }
        if (!techQuestions.isEmpty()) {
            selectedQuestions.add(techQuestions.remove(0));
        }

        List<Question> pool = new ArrayList<>();
        pool.addAll(techQuestions);
        pool.addAll(roleQuestions);
        Collections.shuffle(pool);

        for (Question question : pool) {
            if (selectedQuestions.size() >= 5) break;
            ;
            selectedQuestions.add(question);
        }

        return selectedQuestions;
    }

    @Override
    public List<Question> getQuizQuestionsForAJob(Integer jobId) {
        try (
                Connection connection = databaseConnection.getConnection();
        ) {
            connection.setAutoCommit(false);
            try {
                List<Integer> technologyIds = getTechnologyIdsForJob(connection, jobId);
                Integer roleId = getRoleIdForJob(connection, jobId);

                List<Question> techQuestions = getQuestionsByTehnologies(connection, technologyIds);
                List<Question> roleQuestions = getQuestionsByRole(connection, roleId);

                List<Question> selected = selectFiveRandomQuestions(techQuestions, roleQuestions);
                for (Question question : selected) {
                    question.setAnswers(getAnswersForQuestion(connection, question.getQuestionId()));
                }

                connection.commit();
                return selected;
            } catch (Exception e) {
                connection.rollback();
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
