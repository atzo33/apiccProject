package apicc.controller;

import apicc.model.dto.PostDTO;
import apicc.model.dto.TokenResponse;
import apicc.model.dto.UserDTO;
import apicc.model.dto.UserLoginDTO;
import apicc.model.entity.User;
import apicc.security.TokenUtils;
import apicc.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenUtils tokenUtils;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> create(@RequestBody @Validated UserDTO newUser){

        User createdUser = userService.createUser(newUser);

        if(createdUser == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        UserDTO userDTO = new UserDTO(createdUser);

        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> loadAllUsers() {
        return this.userService.findAllUsers();
    }


    @GetMapping("/whoami")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public UserDTO loggedUserDTO() {
        User user = this.userService.loggedUser(); // Assuming loggedUser() returns a User entity
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO, @PathVariable int id) {
        return new ResponseEntity<>(this.userService.updateUser(userDTO,id), HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody UserLoginDTO userLoginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userLoginDTO.getUsername());
            String token = tokenUtils.generateToken(userDetails);
            TokenResponse responseDTO = new TokenResponse(token);

            return ResponseEntity.ok(responseDTO);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }



}
