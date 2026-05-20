package org.example.spring_security_basic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController
{
    @RequestMapping
    public String test()
    {
        return "This is spring boot security default page";
    }
}
