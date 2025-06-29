package com.springBoot.journalApp.services;

import com.springBoot.journalApp.entity.JournalEntity;
import com.springBoot.journalApp.entity.User;
import com.springBoot.journalApp.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
@Component
@Slf4j
public class JournalEntryService {

    private static final Logger log = LoggerFactory.getLogger(JournalEntryService.class);
    @Autowired
    private JournalEntryRepository journalEntryRepo;
    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntity journalEntry, String userName){
        User user = userService.findByUserName(userName);
        try{
            journalEntry.setDate(LocalDateTime.now());
            JournalEntity saved = journalEntryRepo.save(journalEntry);
            user.getJournalEntities().add(saved);
            userService.saveEntry(user);
        } catch(Exception ex){
            log.error(String.valueOf(ex));
            throw new RuntimeException(ex);
        }

    }

    public void saveEntry(JournalEntity journalEntry){
        try{
            journalEntryRepo.save(journalEntry);
        } catch(Exception ex){
            log.error(String.valueOf(ex));
        }

    }

    public List<JournalEntity> getEntry(){
        return journalEntryRepo.findAll();
    }

    public Optional<JournalEntity> getEntryById(ObjectId id){
        return journalEntryRepo.findById(id);
    }

    public void deleteById(ObjectId id, String userName){
        User user = userService.findByUserName(userName);
        user.getJournalEntities().removeIf(x -> x.getId().equals(id));
        userService.saveEntry(user);
        journalEntryRepo.deleteById(id);
    }

}
