package com.example.demo.core.application.controller.api;

import com.example.demo.config.util.JwtUtil;
import com.example.demo.core.application.dto.AddUserDto;
import com.example.demo.core.application.dto.LoginRequestDto;
import com.example.demo.core.application.dto.LoginResponseDto;
import com.example.demo.core.application.dto.UserDto;
import com.example.demo.core.application.facade.UserFacade;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final UserFacade userFacade;

    public LoginController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsService userDetailsService, UserFacade userFacade) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userFacade = userFacade;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) throws  Exception{
        authenticate(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDto.getEmail());
        final String token = jwtUtil.generateToken(userDetails);
        return new LoginResponseDto(token);
    }

    @ApiOperation(value = "Operation of signing up", httpMethod = "POST", responseReference = "UserDto")
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto signUp(@RequestBody @Valid AddUserDto userDto) {
        return userFacade.addUser(userDto);
    }

    private void authenticate(String username, String password) throws Exception{
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }catch (DisabledException e){
            throw new Exception("USER_DISABLED", e);
        }catch (BadCredentialsException e){
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
