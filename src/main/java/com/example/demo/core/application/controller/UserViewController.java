package com.example.demo.core.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Controller
@RequestMapping("/test")
public class UserViewController {

    @RequestMapping(value = "/getDateAndTime")
    @ResponseBody
    public ModelAndView getDateAndTime(){

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyy/MM/dd HH:mm:ss");
        String format = dtf.format(now);

        HashMap<String,Object> params = new HashMap<>();
        params.put("date_time", format);

        return new ModelAndView("showMessage", params);
    }

    @RequestMapping(value = "/hello")
    public String hello(){
        return "index.html";
    }
}
