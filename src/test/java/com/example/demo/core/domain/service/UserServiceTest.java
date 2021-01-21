package com.example.demo.core.domain.service;

import com.example.demo.core.database.entity.Gender;
import com.example.demo.core.database.entity.UserEntity;
import com.example.demo.core.database.repository.UserRepository;
import com.example.demo.core.domain.model.User;
import com.example.demo.core.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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
}
