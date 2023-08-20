package apicc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor

public class PostDTO {

    private Integer id;

    private UserDTO user;

    private String content;

    private Set<String> images = new HashSet<>();

    private String creationDate;
}
