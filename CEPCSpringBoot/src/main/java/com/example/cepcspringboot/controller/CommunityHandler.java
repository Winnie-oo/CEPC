package com.example.cepcspringboot.controller;

import com.example.cepcspringboot.entity.Community;
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

    @GetMapping("/find")
    public Community find(){
        return communityRepository.findAll().get(0);
    }

    @PutMapping("/increaseVaccines/{num}")
    public void increaseVaccines(@PathVariable("num") Integer num){
        Community community = communityRepository.findAll().get(0);
        community.setVaccines(community.getVaccines()+num);
        communityRepository.save(community);
    }

    @PutMapping("/decreaseVaccines/{num}")
    public void decreaseVaccines(@PathVariable("num") Integer num){
        Community community = communityRepository.findAll().get(0);
        community.setVaccines(community.getVaccines()-num);
        communityRepository.save(community);
    }

    @PutMapping("/increasePatients/{num}")
    public void increasePatients(@PathVariable("num") Integer num){
        Community community = communityRepository.findAll().get(0);
        community.setPatients(community.getPatients()+num);
        communityRepository.save(community);
    }

    @PutMapping("/decreasePatients/{num}")
    public void decreasePatients(@PathVariable("num") Integer num){
        Community community = communityRepository.findAll().get(0);
        community.setPatients(community.getPatients()-num);
        communityRepository.save(community);
    }
}
