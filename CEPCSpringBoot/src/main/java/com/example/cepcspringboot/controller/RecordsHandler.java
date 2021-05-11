package com.example.cepcspringboot.controller;

import com.example.cepcspringboot.entity.Records;
import com.example.cepcspringboot.entity.Users;
import com.example.cepcspringboot.repository.RecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/records")
public class RecordsHandler {
    @Autowired
    private RecordsRepository recordsRepository;

    @GetMapping("/findAll/{page}/{size}")
    public Page<Records> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable = PageRequest.of(page-1,size);
        return recordsRepository.findAll(pageable);
    }

    @PostMapping("/save")
    public String save(@RequestBody Records records){
        Records result = recordsRepository.save(records);
        if(result!=null){
            return "success";
        }else{
            return "error";
        }
    }

}
