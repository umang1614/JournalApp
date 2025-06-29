package com.springBoot.journalApp.controller;

import com.springBoot.journalApp.entity.JournalEntity;
import com.springBoot.journalApp.entity.User;
import com.springBoot.journalApp.services.JournalEntryService;
import com.springBoot.journalApp.services.UserService;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        List<User> allUser = userService.getEntry();
        if(allUser != null){
            return new ResponseEntity<>(allUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user){
        try{
            userService.saveEntry(user);
            return new ResponseEntity<>("User Created", HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

   @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@RequestBody User updatedUser, @PathVariable String username){
        User userInDb = userService.findByUserName(username);
        if(userInDb != null){
            userInDb.setUsername(updatedUser.getUsername());
            userInDb.setPassword(updatedUser.getPassword());
            userService.saveEntry(userInDb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
