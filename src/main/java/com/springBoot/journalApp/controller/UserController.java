package com.springBoot.journalApp.controller;

import com.springBoot.journalApp.entity.JournalEntity;
import com.springBoot.journalApp.entity.User;
import com.springBoot.journalApp.repository.UserRepository;
import com.springBoot.journalApp.services.JournalEntryService;
import com.springBoot.journalApp.services.UserService;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        List<User> allUser = userService.getEntry();
        if(allUser != null){
            return new ResponseEntity<>(allUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



   @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User updatedUser){
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       String username = authentication.getName();
        User userInDb = userService.findByUserName(username);
            userInDb.setUsername(updatedUser.getUsername());
            userInDb.setPassword(updatedUser.getPassword());
            userService.saveNewEntry(userInDb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
