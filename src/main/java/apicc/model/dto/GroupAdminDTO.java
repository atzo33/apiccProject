package apicc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupAdminDTO {
    private Integer id;
    private UserDTO user;
    private Integer groupID;

}
