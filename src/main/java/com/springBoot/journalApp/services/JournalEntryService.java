package com.springBoot.journalApp.services;

import com.springBoot.journalApp.entity.JournalEntity;
import com.springBoot.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;
@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepo;

    public void saveEntry(JournalEntity journalEntry){
        journalEntryRepo.save(journalEntry);
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
