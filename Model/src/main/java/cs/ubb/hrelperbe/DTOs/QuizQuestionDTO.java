package cs.ubb.hrelperbe.DTOs;

import cs.ubb.hrelperbe.BaseModels.Answer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizQuestionDTO {
    private String question;
    private List<AnswerDTO> answers;
}
