package apicc.service;

import apicc.model.dto.UserDTO;
import apicc.model.entity.User;

import java.util.List;

public interface UserService {

    User findByUsername(String username);

    User findById(int id);

    User createUser(UserDTO userDTO);

    List<UserDTO> findAllUsers();

    User loggedUser();

    UserDTO updateUser(UserDTO userDTO,int id);

    UserDTO updatePassword(String password, int id);


}
