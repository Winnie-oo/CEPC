package com.example.cepcspringboot.controller;

import com.example.cepcspringboot.entity.Community;
import com.example.cepcspringboot.entity.Vaccines;
import com.example.cepcspringboot.repository.VaccinesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/increaseVaccines/{date}")
    public void increaseVaccines(@PathVariable("date") String date){
        Vaccines vaccines = vaccinesRepository.findByDateLike(date);
        vaccines.setNumber(vaccines.getNumber()+1);
        vaccinesRepository.save(vaccines);
    }

    @PutMapping("/decreaseVaccines/{date}")
    public void decreaseVaccines(@PathVariable("date") String date){
        Vaccines vaccines = vaccinesRepository.findByDateLike(date);
        vaccines.setNumber(vaccines.getNumber()-1);
        vaccinesRepository.save(vaccines);
    }
}
