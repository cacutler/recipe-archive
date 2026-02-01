package com.cacutler.recipearchive.service;

import com.cacutler.recipearchive.dto.UserCreateDTO;
import com.cacutler.recipearchive.dto.UserUpdateDTO;
import com.cacutler.recipearchive.entity.User;
import com.cacutler.recipearchive.dto.UserDTO;
import com.cacutler.recipearchive.exception.ResourceNotFoundException;
import com.cacutler.recipearchive.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return modelMapper.map(user, UserDTO.class);
    }

    public UserDTO createUser(UserCreateDTO userCreateDTO) {
        User user = new User();
        user.setFirstName(userCreateDTO.getFirstName());
        user.setLastName(userCreateDTO.getLastName());
        user.setUsername(userCreateDTO.getUsername());
        user.setEmail(userCreateDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    public UserDTO updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        if (userUpdateDTO.getFirstName() != null) {
            user.setFirstName(userUpdateDTO.getFirstName());
        }
        if (userUpdateDTO.getLastName() != null) {
            user.setLastName(userUpdateDTO.getLastName());
        }
        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserDTO.class);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }
}