package apicc.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
public class PasswordDTO {

    @NotBlank
    private String newPassword;
    @NotBlank
    private String oldPassword;
    @NotBlank
    private int id;

}
