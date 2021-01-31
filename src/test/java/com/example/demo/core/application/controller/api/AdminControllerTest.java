package com.example.demo.core.application.controller.api;

import com.example.demo.DemoApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {DemoApplication.class})
class AdminControllerTest {

    @Autowired
    private AdminController adminController;

    private MockMvc mockMvc;

    @BeforeEach
    public void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    void deleteUser() throws Exception{
        MockHttpServletRequestBuilder builder = delete("/admin/14");

        mockMvc.perform(builder)
                .andExpect(status().isOk());
    }

}