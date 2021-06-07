package com.example.cepcspringboot.controller;

import com.example.cepcspringboot.entity.Community;
import com.example.cepcspringboot.entity.Records;
import com.example.cepcspringboot.entity.Users;
import com.example.cepcspringboot.entity.Vaccines;
import com.example.cepcspringboot.repository.VaccinesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/vaccines")
public class VaccinesHandler {
    @Autowired
    private VaccinesRepository vaccinesRepository;

    @GetMapping("/findAll")
    public List<Vaccines> findAll(){
        return vaccinesRepository.findAll();
    }

    @GetMapping("/findDate")
    public List<String> findDate(){
        List<Vaccines> list = vaccinesRepository.findAll();
        List<String> list1 = new ArrayList<String>();
        for (Vaccines vaccines: list) {
            String d = vaccines.getDate();
            list1.add(d);
        }
        System.out.println(list1);
        return list1;
    }

    @PutMapping("/increaseVaccines/{date}")
    public void increaseVaccines(@PathVariable("date") String date){
        Vaccines vaccines = vaccinesRepository.findByDateLike(date);
        vaccines.setNumber(vaccines.getNumber()+1);
        vaccinesRepository.save(vaccines);
    }

    @GetMapping("/findById/{id}")
    public Vaccines findById(@PathVariable("id") Integer id){
        return vaccinesRepository.findById(id).get();
    }


    @PostMapping("/update")
    public String update(@RequestBody Vaccines vaccines){
        System.out.println(vaccines);
        Vaccines vaccines1 = vaccinesRepository.findById(vaccines.getId()).get();
        vaccines1.setDate(vaccines.getDate());
        vaccines1.setNumber(vaccines.getNumber());
        Vaccines result = vaccinesRepository.save(vaccines1);
        if(result!=null){
            return "success";
        }else{
            return "error";
        }
    }

    @PostMapping("/save")
    public String save(@RequestBody Vaccines vaccines){
        Vaccines result = vaccinesRepository.save(vaccines);
        if(result!=null){
            return "success";
        }else{
            return "error";
        }
    }

    @PutMapping("/decreaseVaccines/{date}")
    public void decreaseVaccines(@PathVariable("date") String date){
        Vaccines vaccines = vaccinesRepository.findByDateLike(date);
        vaccines.setNumber(vaccines.getNumber()-1);
        vaccinesRepository.save(vaccines);
    }

    @DeleteMapping("/deleteById/{id}")
    public void delete(@PathVariable("id") Integer id){
        vaccinesRepository.deleteById(id);
    }
}
