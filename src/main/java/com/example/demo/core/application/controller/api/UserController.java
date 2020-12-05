package com.example.demo.core.application.controller.api;

import com.example.demo.core.application.dto.UserDto;
import com.example.demo.core.application.facade.UserFacade;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserFacade userFacade;

    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAll(){
        return userFacade.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUser(@PathVariable("id") Long userId) throws NotFoundException {
        return userFacade.getUser(userId);
    }
}
