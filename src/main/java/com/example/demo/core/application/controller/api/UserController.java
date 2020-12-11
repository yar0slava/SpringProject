package com.example.demo.core.application.controller.api;

import com.example.demo.core.application.dto.AddUserDto;
import com.example.demo.core.application.dto.PageDto;
import com.example.demo.core.application.dto.UserDto;
import com.example.demo.core.application.facade.UserFacade;
import javassist.NotFoundException;
import javassist.tools.web.BadHttpRequest;
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

//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public List<UserDto> getAll(){
//        return userFacade.getAll();
//    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageDto<UserDto> getPaged(@RequestParam(value = "from", required = false, defaultValue = "0") int from,
                                     @RequestParam(value = "size", required = false, defaultValue = "10") int size){
        return userFacade.getAll(from, size);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUser(@PathVariable("id") Long userId) throws NotFoundException {
        return userFacade.getUser(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@RequestBody AddUserDto userDto){
        return userFacade.addUser(userDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateUser(@RequestBody UserDto userDto){
        return userFacade.updateUser(userDto);
    }

    @GetMapping("/group")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsersGrouped(@RequestParam(value = "field", required = false, defaultValue = "name") String field,
                                         @RequestParam(value = "order", required = false, defaultValue = "asc") String order) throws BadHttpRequest {
        return userFacade.getUserGrouped(field, order);
    }
}
