package apicc.service.implementation;

import apicc.model.dto.UserDTO;
import apicc.model.entity.User;
import apicc.repository.UserRepository;
import apicc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findFirstByUsername(username);

        if (!user.isEmpty()) {
            return user.get();
        }
        return null;
    }

    @Override
    public User createUser(User user) {

        Optional<User> thisUser = userRepository.findFirstByUsername(user.getUsername());

        if(thisUser.isPresent()){
            return null;
        }

        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setEmail(user.getEmail());
        newUser.setLastLogin(user.getLastLogin());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser = userRepository.save(newUser);

        return newUser;
    }

}
