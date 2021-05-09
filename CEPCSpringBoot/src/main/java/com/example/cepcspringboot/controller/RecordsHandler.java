package com.example.cepcspringboot.controller;

import com.example.cepcspringboot.entity.Records;
import com.example.cepcspringboot.repository.RecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/records")
public class RecordsHandler {
    @Autowired
    private RecordsRepository recordsRepository;

    @GetMapping("/findAll")
    public List<Records> findAll(){
        return recordsRepository.findAll();
    }
}
