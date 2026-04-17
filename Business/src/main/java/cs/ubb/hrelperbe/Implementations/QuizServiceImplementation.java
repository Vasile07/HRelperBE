package cs.ubb.hrelperbe.Implementations;

import cs.ubb.hrelperbe.BaseModels.Question;
import cs.ubb.hrelperbe.DTOs.AnswerDTO;
import cs.ubb.hrelperbe.DTOs.QuizQuestionDTO;
import cs.ubb.hrelperbe.Interfaces.QuizRepositoryInterface;
import cs.ubb.hrelperbe.Interfaces.QuizServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizServiceImplementation implements QuizServiceInterface {

    private final QuizRepositoryInterface quizRepository;

    public QuizServiceImplementation(QuizRepositoryInterface quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Override
    public List<QuizQuestionDTO> getQuizForJob(Integer jobId) {
        List<Question> questions = quizRepository.getQuizQuestionsForAJob(jobId);

        return questions.stream()
                .map(q -> new QuizQuestionDTO(
                        q.getText(),
                        q.getAnswers().stream()
                                .map(a -> new AnswerDTO(a.getText(), a.getCorrect()))
                                .toList()
                ))
                .toList();
    }
}
