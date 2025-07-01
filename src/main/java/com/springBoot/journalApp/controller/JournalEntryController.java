package com.springBoot.journalApp.controller;

import com.springBoot.journalApp.entity.JournalEntity;
import com.springBoot.journalApp.entity.User;
import com.springBoot.journalApp.services.JournalEntryService;
import com.springBoot.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<?> getAllJournalEntryOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        List<JournalEntity> userJournalEntry = user.getJournalEntities();
        if(userJournalEntry != null && !userJournalEntry.isEmpty()){
            return new ResponseEntity<>(userJournalEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntity> createEntry(@RequestBody JournalEntity myEntry){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            journalEntryService.saveEntry(myEntry, username);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }
        catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}") 
    public ResponseEntity<JournalEntity> getJournalById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        List<JournalEntity> userJournals = user.getJournalEntities().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if(!userJournals.isEmpty()){
            Optional<JournalEntity> journalEntity = journalEntryService.getEntryById(myId);
            if(journalEntity.isPresent()){
                return new ResponseEntity<>(journalEntity.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
      boolean removeJournal = journalEntryService.deleteById(myId, username);
      if (removeJournal){
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<JournalEntity> updateJournal(@PathVariable ObjectId myId, @RequestBody JournalEntity updatedJournal){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        List<JournalEntity> userJournals = user.getJournalEntities().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if(!userJournals.isEmpty()){
            Optional<JournalEntity> journalEntity = journalEntryService.getEntryById(myId);
            if(journalEntity.isPresent()){
                JournalEntity old = journalEntity.get();
                old.setContent(updatedJournal.getContent() != null && !updatedJournal.getContent().equals("") ? updatedJournal.getContent() : old.getContent());
                old.setTitle(updatedJournal.getTitle() != null && !updatedJournal.getTitle().equals("") ? updatedJournal.getTitle() : old.getTitle());
                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
