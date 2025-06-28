package com.springBoot.journalApp.services;

import com.springBoot.journalApp.entity.JournalEntity;
import com.springBoot.journalApp.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
@Component
@Slf4j
public class JournalEntryService {

    private static final Logger log = LoggerFactory.getLogger(JournalEntryService.class);
    @Autowired
    private JournalEntryRepository journalEntryRepo;

    public void saveEntry(JournalEntity journalEntry){
        try{
            journalEntry.setDate(LocalDateTime.now());
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

    public void deleteById(ObjectId id){
        journalEntryRepo.deleteById(id);
    }

}
