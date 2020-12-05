package com.example.demo.core.domain.service;

import com.example.demo.core.application.dto.UserDto;
import com.example.demo.core.database.entity.UserEntity;
import com.example.demo.core.database.repository.UserRepository;
import com.example.demo.core.domain.model.User;
import com.example.demo.core.mapper.UserMapper;
import javassist.NotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper){
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public List<User> getAll(){
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(userMapper::toModel)
                .collect(Collectors.toList());
    }

    public User getUser(Long id) throws NotFoundException {
        Optional<User> user = userRepository.findById(id).map(userMapper::toModel);
        return user.orElseThrow(() -> new NotFoundException(String.format("User not found with id %s",id)));
    }
}
