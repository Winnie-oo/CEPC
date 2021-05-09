package com.example.cepcspringboot.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RecordsRepositoryTest {
    @Autowired
    private RecordsRepository recordsRepository;
    @Test
    void findAll(){
        System.out.println(recordsRepository.findAll());
    }
}