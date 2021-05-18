package com.example.cepcspringboot.repository;

import com.example.cepcspringboot.entity.Trips;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripsRepository extends JpaRepository<Trips,Integer> {
}
