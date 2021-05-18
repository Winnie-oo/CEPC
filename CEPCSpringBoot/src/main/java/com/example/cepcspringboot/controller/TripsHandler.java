package com.example.cepcspringboot.controller;

import com.example.cepcspringboot.entity.Trips;
import com.example.cepcspringboot.repository.TripsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trips")
public class TripsHandler {
    @Autowired
    private TripsRepository tripsRepository;

    @GetMapping("/findAll/{page}/{size}")
    public Page<Trips> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable = PageRequest.of(page-1,size);
        return tripsRepository.findAll(pageable);
    }
}
