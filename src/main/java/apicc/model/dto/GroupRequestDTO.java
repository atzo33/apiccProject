package apicc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupRequestDTO {
    private Integer id;
    private boolean approved;
    private String createdAt;
    private String at;
    private UserDTO userRequesting;
    private GroupDTO groupTarget;

}
