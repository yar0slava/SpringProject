package com.example.demo.core.domain.service;

import com.example.demo.client.SomeClient;
import com.example.demo.core.database.entity.UserEntity;
import com.example.demo.core.database.repository.UserRepository;
import com.example.demo.core.domain.model.User;
import com.example.demo.core.mapper.UserMapper;
import javassist.NotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService implements UserDetailsService {

    private final SomeClient someClient;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(SomeClient someClient, UserRepository userRepository, UserMapper userMapper){
        this.someClient = someClient;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public List<User> getAll(){
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(userMapper::toModel)
                .collect(Collectors.toList());
    }

    public List<User> getAllFromApiClient(){
        return someClient.getAllUsersFromApiClient();
    }

    public User getUser(Long id) throws NotFoundException {
        Optional<User> user = userRepository.findById(id).map(userMapper::toModel);
        return user.orElseThrow(() -> new NotFoundException(String.format("User not found with id %s",id)));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(""));
        return userMapper.toModel(userEntity);
    }

}
