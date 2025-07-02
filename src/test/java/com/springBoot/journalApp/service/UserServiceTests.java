package com.springBoot.journalApp.service;

import com.springBoot.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.springBoot.journalApp.entity.User;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;
    @Test
    @ParameterizedTest
    @CsvSource({
            "umang",
            "adminUser",
            "Kumar"
    })
    public void testAdd(String name){
//       User user = userRepository.findByUsername("user1");
//       assertTrue(!user.getJournalEntities().isEmpty());
        assertNotNull(userRepository.findByUsername(name));
    }
}
