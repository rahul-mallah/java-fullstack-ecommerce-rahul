package com.bank.app;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public class HelloController {
    @GetMapping
    public String welcomeMessage() {
        return "Welcome to Hello Banking API!";
    }

    @PostMapping
    public String welcomeMessagePost(@RequestBody String name) {
        return "Hello " + name + "! Welcome to Hello Banking API.";
    }
}
