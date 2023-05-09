package apicc.service;

import apicc.model.entity.User;

public interface UserService {

    User findByUsername(String username);

    User createUser(User user);
}
