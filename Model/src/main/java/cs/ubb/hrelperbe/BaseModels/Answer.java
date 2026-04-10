package cs.ubb.hrelperbe.BaseModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    private Integer answerId;
    private String text;
    private Boolean correct;
}
