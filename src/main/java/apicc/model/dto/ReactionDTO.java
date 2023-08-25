package apicc.model.dto;

import apicc.model.entity.EReaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReactionDTO {

    private Integer id;
    private EReaction type;
    private String timestamp;
    private Integer commentIdReactedTo;
    private Integer postIdReactedTo;
    private UserDTO reactedBy;
}
