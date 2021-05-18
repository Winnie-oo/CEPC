package com.example.cepcspringboot.repository;

import com.example.cepcspringboot.entity.Appoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


public interface AppointRepository extends JpaRepository<Appoint,Integer> {

    Appoint findByNameLike(String name);

    @Transactional
    void deleteByNameLike(String name);
}
