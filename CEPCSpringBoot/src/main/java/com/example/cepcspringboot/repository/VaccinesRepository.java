package com.example.cepcspringboot.repository;

import com.example.cepcspringboot.entity.Vaccines;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccinesRepository extends JpaRepository<Vaccines,Integer> {


    Vaccines findByDateLike(String date);
}
