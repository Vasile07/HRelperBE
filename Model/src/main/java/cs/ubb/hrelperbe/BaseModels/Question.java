package cs.ubb.hrelperbe.BaseModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    private Integer questionId;
    private String text;
    private Technology technology;
    private Role role;
    private List<Answer> answers;
}
