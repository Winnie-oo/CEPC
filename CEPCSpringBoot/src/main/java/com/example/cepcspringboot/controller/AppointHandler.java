package com.example.cepcspringboot.controller;

import com.example.cepcspringboot.entity.Appoint;
import com.example.cepcspringboot.entity.Vaccines;
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
        appoint.setHad_appoint("否");
        Appoint result = appointRepository.save(appoint);
        if(result!=null){
            return "success";
        }else{
            return "error";
        }
    }

    @PostMapping("/update")
    public String update(@RequestBody Appoint appoint){
        Appoint appoint1 = appointRepository.findById(appoint.getId()).get();
        appoint1.setId(appoint.getId());
        appoint1.setName(appoint.getName());
        appoint1.setId_card(appoint.getId_card());
        appoint1.setDate(appoint.getDate());
        Appoint result = appointRepository.save(appoint1);
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
        appointRepository.deleteByNameLike(name);
    }

    @GetMapping("/findById/{id}")
    public Appoint findById(@PathVariable("id") Integer id){
        return appointRepository.findById(id).get();
    }


    @PutMapping("/change/{id}")
    public void change(@PathVariable("id") Integer id){
        Appoint appoint = appointRepository.findById(id).get();
        appoint.setHad_appoint("是");
        appointRepository.save(appoint);
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteByName(@PathVariable("id") Integer id){
        appointRepository.deleteById(id);
    }
}
