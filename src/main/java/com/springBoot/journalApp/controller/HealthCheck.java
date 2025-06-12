package com.springBoot.journalApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {

    @GetMapping("/getHealth")
    public String getHealth(){
        return "Ok";
    }
}
