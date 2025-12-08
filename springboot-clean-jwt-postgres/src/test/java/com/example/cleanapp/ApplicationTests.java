package com.example.cleanapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = CleanAppApplication.class)
@ActiveProfiles("test")
class ApplicationTests {

    @Test
    void contextLoads() {
    }

}