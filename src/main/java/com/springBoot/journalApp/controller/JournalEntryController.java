package com.springBoot.journalApp.controller;

import com.springBoot.journalApp.entity.JournalEntity;
import com.springBoot.journalApp.entity.User;
import com.springBoot.journalApp.services.JournalEntryService;
import com.springBoot.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;


    @GetMapping("/{userName}")
    public ResponseEntity<?> getAllJournalEntryOfUser(@PathVariable String userName){
        User user = userService.findByUserName(userName);
        List<JournalEntity> userJournalEntry = user.getJournalEntities();
        if(userJournalEntry != null && !userJournalEntry.isEmpty()){
            return new ResponseEntity<>(userJournalEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{userName}")
    public ResponseEntity<JournalEntity> createEntry(@RequestBody JournalEntity myEntry, @PathVariable String userName){
        try{
            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }
        catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}") 
    public ResponseEntity<JournalEntity> getJournalById(@PathVariable ObjectId myId){
        Optional<JournalEntity> journalEntity = journalEntryService.getEntryById(myId);
        if(journalEntity.isPresent()){
            return new ResponseEntity<>(journalEntity.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{userName}/{myId}")
    public ResponseEntity<?> deleteJournalById(@PathVariable String userName,@PathVariable ObjectId myId){
        journalEntryService.deleteById(myId, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{userName}/{myId}")
    public ResponseEntity<JournalEntity> updateJournal(@PathVariable String userName, @PathVariable ObjectId myId, @RequestBody JournalEntity updatedJournal){
        JournalEntity old = journalEntryService.getEntryById(myId).orElse(null);
        if(old != null) {
            old.setContent(updatedJournal.getContent() != null && !updatedJournal.getContent().equals("") ? updatedJournal.getContent() : old.getContent());
            old.setTitle(updatedJournal.getTitle() != null && !updatedJournal.getTitle().equals("") ? updatedJournal.getTitle() : old.getTitle());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
