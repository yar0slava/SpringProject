package com.example.demo.client;

import com.example.demo.core.domain.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "someapi", url = "${client.someapi.url}")
public interface SomeClient {

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    List<User> getAllUsersFromApiClient();
}
