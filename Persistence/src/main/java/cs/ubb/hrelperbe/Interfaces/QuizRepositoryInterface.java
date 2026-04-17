package cs.ubb.hrelperbe.Interfaces;

import cs.ubb.hrelperbe.BaseModels.Question;

import java.util.List;

public interface QuizRepositoryInterface {
    public List<Question> getQuizQuestionsForAJob(Integer jobId);
}
