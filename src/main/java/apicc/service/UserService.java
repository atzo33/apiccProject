package apicc.service;

import apicc.model.dto.UserDTO;
import apicc.model.entity.User;

import java.util.List;

public interface UserService {

    User findByUsername(String username);

    User createUser(UserDTO userDTO);

    List<UserDTO> findAllUsers();

    User loggedUser();
}
