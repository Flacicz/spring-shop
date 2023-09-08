package com.example.springshop.service;

import com.example.springshop.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springshop.dto.UserDTO;
import com.example.springshop.entity.User;
import com.example.springshop.entity.enums.Role;
import com.example.springshop.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public void createUser(UserDTO userDTO) {
        String username = userDTO.getUsername();

        if (userRepository.findByUsername(username) != null) {
            return;
        }

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail())
                .roles(new HashSet<>(Role.ROLE_USER.ordinal()))
                .build();

        log.info("Saving new User with username : {}", username);

        userRepository.save(user);
    }

    public UserDTO findUserById(Long id) {
        UserDTO userDTO = new UserDTO();

        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            userDTO = userMapper.fromUserToDTO(optionalUser.get());
        } else {
            throw new NullPointerException("User not found");
        }

        return userDTO;
    }

    public void editUser(UserDTO userDTO) {
        String username = userDTO.getUsername();

        if (userRepository.findByUsername(username) != null) {
            throw new NullPointerException("User not found");
        }

        User user = User.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail())
                .build();

        userRepository.save(user);

        log.info("Edit new User with username : {}", username);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NullPointerException("User not found");
        }

        userRepository.deleteById(id);
    }
}
