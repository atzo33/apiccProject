package apicc.model.dto;

import apicc.model.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
@Getter
@Setter
@NoArgsConstructor
public class UserLoginDTO {

    private Integer id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public UserLoginDTO(User createdUser) {
        this.id = createdUser.getId();
        this.username = createdUser.getUsername();
    }



}
