package com.example.demo.core.application.facade;

import com.example.demo.core.application.dto.AddUserDto;
import com.example.demo.core.application.dto.PageDto;
import com.example.demo.core.application.dto.UserDto;
import com.example.demo.core.database.entity.Gender;
import com.example.demo.core.domain.model.User;
import com.example.demo.core.domain.service.UserService;
import com.example.demo.core.mapper.AddUserMapper;
import com.example.demo.core.mapper.UpdateUserMapper;
import com.example.demo.core.mapper.UserMapper;
import javassist.NotFoundException;
import javassist.tools.web.BadHttpRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserFacadeTest {

    @InjectMocks
    private UserFacade userFacade;

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UpdateUserMapper updateUserMapper;

    @Mock
    private AddUserMapper addUserMapper;

    @Test
    void getAllTest(){

        User user = new User();
        PageDto<User> pageDto = new PageDto<>();
        pageDto.setElements(Collections.singletonList(user));

        when(userService.getAll(2,2)).thenReturn(pageDto);

        PageDto<UserDto> page = userFacade.getAll(2,2);
        assertEquals(1, page.getSize());

        verify(userService, times(1)).getAll(2,2);
        verifyNoMoreInteractions(userService);

        verify(userMapper, times(1)).toDto(any());
        verifyNoMoreInteractions(userMapper);
    }

    @Test
    void getUsersGroupedTest() throws BadHttpRequest {

        User user = new User();

        when(userService.getUsersGrouped("name", "asc")).thenReturn(Collections.singletonList(user));
        List<UserDto> allUsers = userFacade.getUserGrouped("name", "asc");
        assertEquals(1, allUsers.size());

        verify(userService, times(1)).getUsersGrouped("name", "asc");
        verifyNoMoreInteractions(userService);

        verify(userMapper, times(1)).toDto(any());
        verifyNoMoreInteractions(userMapper);
    }

    @Test
    void getUserTest() throws NotFoundException {

        long id = anyLong();
        User user = new User();
        user.setId(id);

        UserDto userDto = new UserDto();
        userDto.setId(id);

        when(userService.getUser(id)).thenReturn(user);
        when(userMapper.toDto(any())).thenReturn(userDto);

        UserDto userDto1 = userFacade.getUser(user.getId());

        assertEquals(id, userDto1.getId());

        verify(userService, times(1)).getUser(anyLong());
        verifyNoMoreInteractions(userService);

        verify(userMapper, times(1)).toDto(any());
        verifyNoMoreInteractions(userMapper);
    }

    @Test
    void getUserShouldThrowNotFoundException() throws NotFoundException {
        when(userService.getUser(anyLong())).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class,
                () -> userFacade.getUser(anyLong()));
    }

    @Test
    void deleteUserTest(){
        userService.deleteUser(anyLong());

        verify(userService, times(1)).deleteUser(anyLong());
        verifyNoMoreInteractions(userService);
    }

    @Test
    void addUserTest(){

        AddUserDto addUser = new AddUserDto();
        addUser.setAge(24);
        addUser.setEmail("test@gmail.com");
        addUser.setGender(Gender.FEMALE);
        addUser.setFirstName("Test");
        addUser.setLastName("Test");
        addUser.setPassword("Test123_");

        UserDto userDto = new UserDto();
        userDto.setAge(24);
        userDto.setEmail("test@gmail.com");
        userDto.setGender(Gender.FEMALE);
        userDto.setFirstName("Test");
        userDto.setLastName("Test");

        when(userService.addUser(any())).thenReturn(new User());
        when(userMapper.toDto(any())).thenReturn(userDto);

        UserDto saved = userFacade.addUser(addUser);

        assertEquals(24, saved.getAge());
        assertEquals(Gender.FEMALE, saved.getGender());
        assertEquals("Test", saved.getFirstName());
        assertEquals("Test", saved.getLastName());

        verify(userService, times(1)).addUser(any());
        verifyNoMoreInteractions(userService);

        verify(userMapper, times(1)).toDto(any());
        verifyNoMoreInteractions(userMapper);

        verify(addUserMapper, times(1)).toModel(any());
        verifyNoMoreInteractions(addUserMapper);
    }

    @Test
    void updateUserTest(){

        UserDto user = new UserDto();
        user.setAge(24);
        user.setEmail("test@gmail.com");
        user.setGender(Gender.FEMALE);
        user.setFirstName("Test");
        user.setLastName("Test");

        when(userService.updateUser(any())).thenReturn(new User());
        when(userMapper.toDto(any())).thenReturn(user);

        UserDto updated = userFacade.updateUser(user);

        assertEquals(24, updated.getAge());
        assertEquals(Gender.FEMALE, updated.getGender());
        assertEquals("Test", updated.getFirstName());
        assertEquals("Test", updated.getLastName());

        verify(userService, times(1)).updateUser(any());
        verifyNoMoreInteractions(userService);

        verify(userMapper, times(1)).toDto(any());
        verifyNoMoreInteractions(userMapper);

        verify(updateUserMapper, times(1)).toModel(any());
        verifyNoMoreInteractions(updateUserMapper);
    }

}