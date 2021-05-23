package com.example.cepcspringboot.controller;

import com.example.cepcspringboot.entity.Appoint;
import com.example.cepcspringboot.repository.AppointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/appointRecord")
public class AppointHandler {
    @Autowired
    private AppointRepository appointRepository;

    @GetMapping("/findAll/{page}/{size}")
    public Page<Appoint> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable = PageRequest.of(page-1,size);
        return appointRepository.findAll(pageable);
    }

    @GetMapping("/findByName/{name}")
    public Appoint findByName(@PathVariable("name") String name){
        return appointRepository.findByNameLike(name);
    }

    @PostMapping("/save")
    public String save(Appoint appoint){
        System.out.println(appoint);
        Appoint result = appointRepository.save(appoint);

        if(result!=null){
            return "success";
        }else{
            return "error";
        }
    }

    @Transactional
    @DeleteMapping("/deleteByName/{name}")
    public void deleteByName(@PathVariable("name") String name){
        System.out.println("----------------"+name);
        String date = appointRepository.findByNameLike(name).getDate();
        appointRepository.deleteByNameLike(name);
    }

}
