package com.springBoot.journalApp.services;

import com.springBoot.journalApp.entity.User;
import com.springBoot.journalApp.repository.JournalEntryRepository;
import com.springBoot.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepo;

    public void saveEntry(User user){
        try{
            userRepo.save(user);
        } catch(Exception ex){
            log.error(String.valueOf(ex));
        }

    }

    public List<User> getEntry(){
        return userRepo.findAll();
    }

    public Optional<User> getEntryById(ObjectId id){
        return userRepo.findById(id);
    }

    public void deleteById(ObjectId id){
        userRepo.deleteById(id);
    }

    public User findByUserName(String userName){
        return userRepo.findByUsername(userName);
    }

}
