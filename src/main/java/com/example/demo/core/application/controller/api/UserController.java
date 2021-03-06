package com.example.demo.core.application.controller.api;

import com.example.demo.core.application.dto.PageDto;
import com.example.demo.core.application.dto.UserDto;
import com.example.demo.core.application.facade.UserFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import javassist.tools.web.BadHttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/users")
@Api(value = "User operations in the system", tags = "Operations pertaining to User", protocols = "https")
public class UserController {

    private final UserFacade userFacade;

    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @ApiOperation(value = "Operation of getting all of the Users", httpMethod = "GET", responseReference = "UserDto", responseContainer = "PageDto")
    @ApiImplicitParams(value =
            {@ApiImplicitParam(value = "number of page being returned", name = "page", type = "Integer"),
                    @ApiImplicitParam(value = "size of page being returned", name = "size", type = "Integer")})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageDto<UserDto> getAll(@RequestParam(value = "page", required = false) Integer page,
                                   @RequestParam(value = "size", required = false) Integer size) {
        return userFacade.getAll(page, size);
    }

    @ApiOperation(value = "Operation of getting all of the Users from other API", httpMethod = "GET", responseReference = "UserDto", responseContainer = "List")
    @GetMapping("/client")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAllFromApiClient() {
        return userFacade.getAllFromApiClient();
    }

    @ApiOperation(value = "Operation of getting the User by id", httpMethod = "GET", responseReference = "UserDto")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUser(@PathVariable("id") Long userId) throws NotFoundException {
        return userFacade.getUser(userId);
    }

    @ApiOperation(value = "Operation of updating User", httpMethod = "PUT", responseReference = "UserDto")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateUser(@RequestBody @Valid UserDto userDto) {
        return userFacade.updateUser(userDto);
    }

    @ApiOperation(value = "Operation of getting Users grouped by properties", httpMethod = "GET", responseReference = "UserDto", responseContainer = "List")
    @ApiImplicitParams(value =
            {@ApiImplicitParam(value = "name of the user field to be grouped by", name = "field", type = "String", defaultValue = "name"),
                    @ApiImplicitParam(value = "order of the grouped list of users", name = "order", type = "String", defaultValue = "asc", allowableValues = "asc, desc")})
    @GetMapping("/group")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsersGrouped(@RequestParam(value = "field", required = false, defaultValue = "name") String field,
                                         @RequestParam(value = "order", required = false, defaultValue = "asc") String order) throws BadHttpRequest {
        return userFacade.getUserGrouped(field, order);
    }

}
