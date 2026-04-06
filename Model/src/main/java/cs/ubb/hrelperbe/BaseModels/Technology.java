package cs.ubb.hrelperbe.BaseModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Technology {
    private Integer technologyId;
    private String name;
    private String description;
}
