package com.example.cepcspringboot.repository;

import com.example.cepcspringboot.entity.Records;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordsRepository extends JpaRepository<Records,Integer> {
}
