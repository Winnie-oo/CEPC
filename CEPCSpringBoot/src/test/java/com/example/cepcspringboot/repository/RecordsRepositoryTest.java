package com.example.cepcspringboot.repository;

import com.example.cepcspringboot.entity.Records;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RecordsRepositoryTest {
    @Autowired
    private RecordsRepository recordsRepository;
    @Test
    void findAll(){

        System.out.println(recordsRepository.findAll());

    }

    @Test
    void save(){
        List<Records> list = recordsRepository.findAll();
        for (Records records:list){
            records.setTemperature(36.2);
            recordsRepository.save(records);
        }
    }
}