package com.example.cepcspringboot.controller;

import com.example.cepcspringboot.entity.Vaccines;
import com.example.cepcspringboot.repository.VaccinesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vaccines")
public class VaccinesHandler {
    @Autowired
    private VaccinesRepository vaccinesRepository;

    @GetMapping("/findAll")
    public List<Vaccines> findAll(){
        return vaccinesRepository.findAll();
    }


}
