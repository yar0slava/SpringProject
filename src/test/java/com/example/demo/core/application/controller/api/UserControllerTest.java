package com.example.demo.core.application.controller.api;

import com.example.demo.DemoApplication;
import com.example.demo.core.application.dto.UserDto;
import com.example.demo.core.database.entity.Authority;
import com.example.demo.core.database.entity.Gender;
import com.example.demo.core.domain.model.BankAccount;
import com.example.demo.core.domain.model.Hospital;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wiremock.com.fasterxml.jackson.databind.ObjectMapper;
import wiremock.com.fasterxml.jackson.databind.ObjectWriter;
import wiremock.com.fasterxml.jackson.databind.SerializationFeature;

import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = {DemoApplication.class})
@AutoConfigureWireMock(port = 6605, stubs = "classpath:/wiremock/stubs", files = "classpath:/wiremock")
class UserControllerTest {

    @Autowired
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    public void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void getAllUsers() throws Exception{
        MockHttpServletRequestBuilder builder = get("/api/users")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.elements", hasSize(7)))
                .andExpect(jsonPath("$.size", equalTo(7)));
    }

    @Test
    void getAllUsersFromClient() throws Exception{
        MockHttpServletRequestBuilder builder = get("/api/users/client")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(6)));
    }

    @Test
    void getUsersGrouped() throws Exception{
        MockHttpServletRequestBuilder builder = get("/api/users/group")
                .param("order","desc")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(7)));
    }

    @Test
    void updateUser() throws Exception{

        UserDto user = new UserDto();
        user.setId(14L);
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

        MockHttpServletRequestBuilder builder = put("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", equalTo(user)));
    }

    @Test
    void getUser() throws Exception{
        MockHttpServletRequestBuilder builder = get("/api/users/2")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(2)));
    }
}