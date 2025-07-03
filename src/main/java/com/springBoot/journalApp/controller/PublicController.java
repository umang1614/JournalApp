package com.springBoot.journalApp.controller;

import com.springBoot.journalApp.entity.User;
import com.springBoot.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;

    @GetMapping("/getHealth")
    public String getHealth(){
        return "Running fine :)";
    }

    @PostMapping("/create-user")
    public ResponseEntity<String> createUser(@RequestBody User user){
        try{
            userService.saveNewEntry(user);
            return new ResponseEntity<>("User Created", HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
