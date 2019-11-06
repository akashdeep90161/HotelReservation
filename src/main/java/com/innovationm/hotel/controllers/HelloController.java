package com.innovationm.hotel.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String sayHello()
    {
        return "Hotel Reservation "+ new Date();
    }
}
