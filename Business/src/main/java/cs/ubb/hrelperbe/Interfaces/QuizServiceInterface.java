package cs.ubb.hrelperbe.Interfaces;

import cs.ubb.hrelperbe.DTOs.QuizQuestionDTO;

import java.util.List;

public interface QuizServiceInterface {
    public List<QuizQuestionDTO> getQuizForJob(Integer jobId);
}
