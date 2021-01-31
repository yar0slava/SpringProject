package com.example.demo.core.application.controller.api;

import com.example.demo.DemoApplication;
import com.example.demo.core.application.dto.AddUserDto;
import com.example.demo.core.application.dto.LoginRequestDto;
import com.example.demo.core.database.entity.Authority;
import com.example.demo.core.database.entity.Gender;
import com.example.demo.core.domain.model.BankAccount;
import com.example.demo.core.domain.model.Hospital;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wiremock.com.fasterxml.jackson.databind.ObjectMapper;
import wiremock.com.fasterxml.jackson.databind.ObjectWriter;
import wiremock.com.fasterxml.jackson.databind.SerializationFeature;

import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = {DemoApplication.class})
class LoginControllerTest {

    @Autowired
    private AdminController adminController;

    private MockMvc mockMvc;

    @BeforeEach
    public void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    void login() throws Exception{

        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail("yaroslavakurashadmin2@gmail.com");
        loginRequestDto.setPassword("123456QWErty");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(loginRequestDto);

        MockHttpServletRequestBuilder builder = post("/login/login")
//                .param("field","id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token", notNullValue()));


    }

    @Test
    void signup() throws Exception{

        AddUserDto user = new AddUserDto();
        user.setAge(24);
        user.setEmail("test1@gmail.com");
        user.setGender(Gender.FEMALE);
        user.setFirstName("Test");
        user.setLastName("Test");
        Authority authority = new Authority();
        authority.setId(2L);
        authority.setName("USER");
        user.setAuthority(Collections.singleton(authority));
        Hospital hospital = new Hospital();
        hospital.setId(2);
        hospital.setName("second");
        user.setHospital(hospital);
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(61L);
        user.setBankAccount(Collections.singletonList(bankAccount));

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(user );

        MockHttpServletRequestBuilder builder = post("/login/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email", equalTo("test1@gmail.com")));

    }

}