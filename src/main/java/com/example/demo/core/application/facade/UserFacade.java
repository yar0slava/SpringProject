package com.example.demo.core.application.facade;

import com.example.demo.core.application.dto.AddUserDto;
import com.example.demo.core.application.dto.PageDto;
import com.example.demo.core.application.dto.UserDto;
import com.example.demo.core.domain.model.User;
import com.example.demo.core.domain.service.UserService;
import com.example.demo.core.mapper.AddUserMapper;
import com.example.demo.core.mapper.UpdateUserMapper;
import com.example.demo.core.mapper.UserMapper;
import javassist.NotFoundException;
import javassist.tools.web.BadHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserFacade {
    private final UserService userService;
    private final UserMapper userMapper;
    private final AddUserMapper addUserMapper;
    private final UpdateUserMapper updateUserMapper;

    public UserFacade(UserService userService, UserMapper userMapper, AddUserMapper addUserMapper, UpdateUserMapper updateUserMapper){
        this.userService = userService;
        this.userMapper = userMapper;
        this.addUserMapper = addUserMapper;
        this.updateUserMapper = updateUserMapper;
    }

    public PageDto<UserDto> getAll(Integer from, Integer size){
        List<UserDto> users = userService.getAll(from, size).getElements().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return new PageDto<UserDto>(users.size(), users);
    }

//    public List<UserDto> getAll(){
//        return userService.getAll().stream()
//                .map(userMapper::toDto)
//                .collect(Collectors.toList());
//    }

    public UserDto getUser(Long id) throws NotFoundException {
        User user = userService.getUser(id);
        return userMapper.toDto(user);
    }

    public List<UserDto> getUserGrouped(String field, String order) throws BadHttpRequest {
        return userService.getUsersGrouped(field, order).stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto addUser(AddUserDto addUserDto){
        User user = userService.addUser(addUserMapper.toModel(addUserDto));
        return userMapper.toDto(user);
    }

    public UserDto updateUser(UserDto userDto) {
        User user = userService.updateUser(updateUserMapper.toModel(userDto));
        return userMapper.toDto(user);
    }
}
