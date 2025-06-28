package com.springBoot.journalApp.repository;

import com.springBoot.journalApp.entity.JournalEntity;
import com.springBoot.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUsername(String username);
}
