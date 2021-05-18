package com.example.cepcspringboot.controller;

import com.example.cepcspringboot.entity.Community;
import com.example.cepcspringboot.entity.Users;
import com.example.cepcspringboot.repository.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/community")
public class CommunityHandler {
    @Autowired
    private CommunityRepository communityRepository;

    @GetMapping("/findAll/{page}/{size}")
    public Page<Community> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable = PageRequest.of(page-1,size);
        return communityRepository.findAll(pageable);
    }

    @PutMapping("/addVaccine")
    public void addVaccine(@RequestBody Integer num){
        Community community = communityRepository.findAll().get(1);
        community.setVaccines(num);
        communityRepository.save(community);
    }
}
