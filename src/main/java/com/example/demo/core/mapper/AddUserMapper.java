package com.example.demo.core.mapper;

import com.example.demo.core.application.dto.AddUserDto;
import com.example.demo.core.database.entity.UserEntity;
import com.example.demo.core.domain.model.AddUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AddUserMapper {

    private final ModelMapper mapper;

    public AddUserMapper(ModelMapper modelMapper){
        this.mapper = modelMapper;
    }

    public AddUser toModel(AddUserDto userDto){
        return mapper.map(userDto, AddUser.class);
    }

    public UserEntity toEntity(AddUser user){
        return mapper.map(user, UserEntity.class);
    }

}
