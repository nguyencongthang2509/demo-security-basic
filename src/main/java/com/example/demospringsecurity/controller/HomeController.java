package com.example.demospringsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thangncph26123
 */
@RestController
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "Hello world";
    }
}
