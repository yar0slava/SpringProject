package com.example.demo.core.application.facade;

import com.example.demo.core.application.dto.AddUserRequestDto;
import com.example.demo.core.application.dto.UserDto;
import com.example.demo.core.domain.model.User;
import com.example.demo.core.domain.service.UserService;
import com.example.demo.core.mapper.UserMapper;
import javassist.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserFacade {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserFacade(UserService userService, UserMapper userMapper){
        this.userService = userService;
        this.userMapper = userMapper;
    }

    public List<UserDto> getAll(){
        return userService.getAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto getUser(Long id) throws NotFoundException {
        User user = userService.getUser(id);
        return userMapper.toDto(user);
    }

    public List<UserDto> getAllFromApiClient() {
        return userService.getAllFromApiClient().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

//    public void addUser(AddUserRequestDto userDto) {
//        userService.addUser(userDto);
//    }
}
