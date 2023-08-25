package apicc.service.implementation;

import apicc.model.dto.UserDTO;
import apicc.model.entity.Roles;
import apicc.model.entity.User;
import apicc.repository.UserRepository;
import apicc.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;
    @Autowired
    public UserServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findFirstByUsername(username);

        if (!user.isEmpty()) {
            return user.get();
        }
        return null;
    }


    @Override
    public User createUser(UserDTO userDTO) {

        Optional<User> user = userRepository.findFirstByUsername(userDTO.getUsername());

        if(user.isPresent()){
            return null;
        }

        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        newUser.setEmail(userDTO.getEmail());
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setLastLogin(LocalDateTime.now());
        newUser.setRole(Roles.USER);
        newUser = userRepository.save(newUser);

        return newUser;
    }

    @Override
    public List <UserDTO> findAllUsers() {

        List<User> users = userRepository.findAll();
        List<UserDTO>userDTOS = new ArrayList<>();
        for (User user :users){
            UserDTO userDTO = modelMapper.map(user,UserDTO.class);
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }



    @Override
    public User loggedUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return this.findByUsername(username);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, int id) {

        User user = userRepository.findById(id).orElseThrow(()->new RuntimeException());




        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        userRepository.save(user);

        return modelMapper.map(user, UserDTO.class);
    }
    @Override
    public UserDTO updatePassword(String password, int id) {

        User user = userRepository.findById(id).orElseThrow(()->new RuntimeException());




        user.setPassword(password);

        userRepository.save(user);

        return modelMapper.map(user, UserDTO.class);
    }


}
