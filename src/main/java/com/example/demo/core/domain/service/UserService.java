package com.example.demo.core.domain.service;

import com.example.demo.core.application.dto.PageDto;
import com.example.demo.core.database.entity.UserEntity;
import com.example.demo.client.SomeClient;
import com.example.demo.core.database.repository.UserRepository;
import com.example.demo.core.domain.model.AddUser;
import com.example.demo.core.domain.model.User;
import com.example.demo.core.mapper.AddUserMapper;
import com.example.demo.core.mapper.UpdateUserMapper;
import com.example.demo.core.mapper.UserMapper;
import javassist.NotFoundException;
import javassist.tools.web.BadHttpRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    private final SomeClient someClient;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AddUserMapper addUserMapper;
    private final UpdateUserMapper updateUserMapper;

    public UserService(SomeClient someClient, UserRepository userRepository, UserMapper userMapper, AddUserMapper addUserMapper, UpdateUserMapper updateUserMapper){
        this.someClient = someClient;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.addUserMapper = addUserMapper;
        this.updateUserMapper = updateUserMapper;
    }

//    public List<User> getAll(){
//        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
//                .map(userMapper::toModel)
//                .collect(Collectors.toList());
//    }

    public PageDto<User> getAll(Integer page, Integer size){
        PageDto<User> pageDto = new PageDto<>();
        List<User> users = StreamSupport.stream(userRepository.findAll(PageRequest.of(page, size)).spliterator(), false)
                .map(userMapper::toModel)
                .collect(Collectors.toList());
        pageDto.setElements(users);
        pageDto.setSize(users.size());

        return pageDto;
    }

    public List<User> getUsersGrouped(String field, String order) throws BadHttpRequest {

        switch (order){
            case "asc": return StreamSupport.stream(userRepository.findAll(Sort.by(Sort.Order.asc(field))).spliterator(), false)
                    .map(userMapper::toModel)
                    .collect(Collectors.toList());
            case "desc": return StreamSupport.stream(userRepository.findAll(Sort.by(Sort.Order.desc("age"))).spliterator(), false)
                    .map(userMapper::toModel)
                    .collect(Collectors.toList());
        }
        throw new BadHttpRequest();
    }

    public List<User> getAllFromApiClient(){
        return someClient.getAllUsersFromApiClient();
    }

    public User getUser(Long id) throws NotFoundException {
        Optional<User> user = userRepository.findById(id).map(userMapper::toModel);
        return user.orElseThrow(() -> new NotFoundException(String.format("User not found with id %s",id)));
    }

    public User addUser(AddUser user){
        UserEntity addUser = userRepository.save(addUserMapper.toEntity(user));
        return userMapper.toModel(addUser);
    }

    public User updateUser(User user){
        UserEntity updateUser = userRepository.save(updateUserMapper.toEntity(user));
        return userMapper.toModel(updateUser);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

//    public void addUser(User user){
//        userRepository.save(user);
//    }
}
