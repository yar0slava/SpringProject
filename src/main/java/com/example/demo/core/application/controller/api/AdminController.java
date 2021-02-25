package com.example.demo.core.application.controller.api;

import com.example.demo.core.application.facade.UserFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@Api(value = "Admin operations in the system", tags = "Operations pertaining to Admin", protocols = "https")
public class AdminController {

    private final UserFacade userFacade;

    public AdminController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @ApiOperation(value = "Operation of deleting User", httpMethod = "DELETE")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("id") Long userId) {
        userFacade.deleteUser(userId);
    }

}
