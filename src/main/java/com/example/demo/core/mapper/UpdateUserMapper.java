package com.example.demo.core.mapper;

import com.example.demo.core.application.dto.UserDto;
import com.example.demo.core.database.entity.UserEntity;
import com.example.demo.core.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class UpdateUserMapper {
    private final ModelMapper mapper;

    public UpdateUserMapper(ModelMapper modelMapper){
        this.mapper = modelMapper;
    }

    public UserEntity toEntity(User user){
        return mapper.map(user, UserEntity.class);
    }

    public User toModel(UserDto userDto) {
        return mapper.map(userDto,User.class);
    }
}
