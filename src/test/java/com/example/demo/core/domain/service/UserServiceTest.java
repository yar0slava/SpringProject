package com.example.demo.core.domain.service;

import com.example.demo.client.SomeClient;
import com.example.demo.core.application.dto.PageDto;
import com.example.demo.core.database.entity.Gender;
import com.example.demo.core.database.entity.UserEntity;
import com.example.demo.core.database.repository.UserRepository;
import com.example.demo.core.domain.model.AddUser;
import com.example.demo.core.domain.model.User;
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
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UpdateUserMapper updateUserMapper;

    @Mock
    private AddUserMapper addUserMapper;

    @Mock
    private SomeClient someClient;

    @Test
    void loadUserByUsername(){
        UserEntity userEntity = new UserEntity();

        User user = new User();
        user.setAge(24);
        user.setEmail("test@gmail.com");
        user.setGender(Gender.FEMALE);
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setPassword("Test123_");
        user.setId(123L);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userEntity));
        when(userMapper.toModel(any())).thenReturn(user);

        UserDetails userDetails = userService.loadUserByUsername("test@gmail.com");

        assertEquals("test@gmail.com", userDetails.getUsername());

        verify(userRepository, times(1)).findByEmail(anyString());
        verifyNoMoreInteractions(userRepository);

        verify(userMapper, times(1)).toModel(any());
        verifyNoMoreInteractions(userMapper);
    }

    @Test
    void loadUserByUsernameShouldThrowExceptioin(){

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername("test@gmail.com"));
    }

    @Test
    void getAllFromApiClientTest(){

        when(someClient.getAllUsersFromApiClient()).thenReturn(Collections.singletonList(new User()));
        List<User> allUsers = userService.getAllFromApiClient();
        assertEquals(1, allUsers.size());
    }

    @Test
    void getAllTest(){

        UserEntity userEntity = new UserEntity();

        when(userRepository.findAll()).thenReturn(Collections.singletonList(userEntity));

        PageDto<User> page = userService.getAll(null,null);
        assertEquals(1, page.getSize());

        verify(userRepository, times(1)).findAll();
        verifyNoMoreInteractions(userRepository);

        verify(userMapper, times(1)).toModel(any());
        verifyNoMoreInteractions(userMapper);
    }

    @Test
    void getUsersGroupedTest() throws BadHttpRequest {

        UserEntity userEntity = new UserEntity();

        when(userRepository.findAll(Sort.by(Sort.Order.asc("name")))).thenReturn(Collections.singletonList(userEntity));
        List<User> allUsers = userService.getUsersGrouped("name", "asc");
        assertEquals(1, allUsers.size());

        verify(userRepository, times(1)).findAll((Sort) any());
        verifyNoMoreInteractions(userRepository);

        verify(userMapper, times(1)).toModel(any());
        verifyNoMoreInteractions(userMapper);
    }

    @Test
    void getUserTest() throws NotFoundException {
        UserEntity userEntity = new UserEntity();

        long id = anyLong();
        User user = new User();
        user.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));
        when(userMapper.toModel(any())).thenReturn(user);

        User user1 = userService.getUser(user.getId());

        assertEquals(id, user1.getId());

        verify(userRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(userRepository);

        verify(userMapper, times(1)).toModel(any());
        verifyNoMoreInteractions(userMapper);
    }

    @Test
    void getUserShouldThrowNotFoundException(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> userService.getUser(anyLong()));
    }

    @Test
    void deleteUserTest(){
        userService.deleteUser(anyLong());

        verify(userRepository, times(1)).deleteById(anyLong());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void addUserTest(){

        AddUser addUser = new AddUser();
        addUser.setAge(24);
        addUser.setEmail("test@gmail.com");
        addUser.setGender(Gender.FEMALE);
        addUser.setFirstName("Test");
        addUser.setLastName("Test");
        addUser.setPassword("Test123_");

        User user = new User();
        user.setAge(24);
        user.setEmail("test@gmail.com");
        user.setGender(Gender.FEMALE);
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setPassword("Test123_");

        when(userRepository.save(any())).thenReturn(new UserEntity());
        when(userMapper.toModel(any())).thenReturn(user);

        User saved = userService.addUser(addUser);

        assertEquals("test@gmail.com", saved.getUsername());
        assertEquals(24, saved.getAge());
        assertEquals(Gender.FEMALE, saved.getGender());
        assertEquals("Test", saved.getFirstName());
        assertEquals("Test", saved.getLastName());
        assertEquals("Test123_", saved.getPassword());

        verify(userRepository, times(1)).save(any());
        verifyNoMoreInteractions(userRepository);

        verify(userMapper, times(1)).toModel(any());
        verifyNoMoreInteractions(userMapper);

        verify(addUserMapper, times(1)).toEntity(any());
        verifyNoMoreInteractions(addUserMapper);
    }

    @Test
    void updateUserTest(){

        User user = new User();
        user.setAge(24);
        user.setEmail("test@gmail.com");
        user.setGender(Gender.FEMALE);
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setPassword("Test123_");

        when(userRepository.save(any())).thenReturn(new UserEntity());
        when(userMapper.toModel(any())).thenReturn(user);

        User updated = userService.updateUser(user);

        assertEquals("test@gmail.com", updated.getUsername());
        assertEquals(24, updated.getAge());
        assertEquals(Gender.FEMALE, updated.getGender());
        assertEquals("Test", updated.getFirstName());
        assertEquals("Test", updated.getLastName());
        assertEquals("Test123_", updated.getPassword());

        verify(userRepository, times(1)).save(any());
        verifyNoMoreInteractions(userRepository);

        verify(userMapper, times(1)).toModel(any());
        verifyNoMoreInteractions(userMapper);

        verify(updateUserMapper, times(1)).toEntity(any());
        verifyNoMoreInteractions(updateUserMapper);
    }
}
