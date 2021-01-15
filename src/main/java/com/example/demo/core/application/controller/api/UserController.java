package com.example.demo.core.application.controller.api;

import com.example.demo.core.application.dto.AddUserDto;
import com.example.demo.core.application.dto.PageDto;
import com.example.demo.core.application.dto.UserDto;
import com.example.demo.core.application.facade.UserFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import javassist.tools.web.BadHttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Api(value = "User operations in the system", tags = "Operations pertaining to User", protocols = "https")
public class UserController {

    private final UserFacade userFacade;

    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @ApiOperation(value = "Operation of getting all of the Users", httpMethod = "GET", responseReference = "UserDto", responseContainer = "PageDto")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageDto<UserDto> getAll(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                     @RequestParam(value = "size", required = false, defaultValue = "10") int size){
        return userFacade.getAll(page, size);
    }

    @ApiOperation(value = "Operation of getting all of the Users from other API", httpMethod = "GET", responseReference = "UserDto", responseContainer = "List")
    @GetMapping("/client")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAllFromApiClient(){
        return userFacade.getAllFromApiClient();
    }

    @ApiOperation(value = "Operation of getting all of the User by id", httpMethod = "GET", responseReference = "UserDto")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUser(@PathVariable("id") Long userId) throws NotFoundException {
        return userFacade.getUser(userId);
    }

    @ApiOperation(value = "Operation of adding User", httpMethod = "POST", responseReference = "UserDto", code = 201)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@RequestBody @Valid AddUserDto userDto){
        return userFacade.addUser(userDto);
    }

    @ApiOperation(value = "Operation of updating User", httpMethod = "PUT", responseReference = "UserDto")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateUser(@RequestBody @Valid UserDto userDto){
        return userFacade.updateUser(userDto);
    }

    @ApiOperation(value = "Operation of deleting User", httpMethod = "DELETE")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("id") Long userId) {
        userFacade.deleteUser(userId);
    }

    @ApiOperation(value = "Operation of getting Users grouped by properties", httpMethod = "GET", responseReference = "UserDto", responseContainer = "List")
    @GetMapping("/group")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsersGrouped(@RequestParam(value = "field", required = false, defaultValue = "name") String field,
                                         @RequestParam(value = "order", required = false, defaultValue = "asc") String order) throws BadHttpRequest {
        return userFacade.getUserGrouped(field, order);
    }

}
