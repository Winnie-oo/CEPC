package com.example.cepcspringboot.repository;

import com.example.cepcspringboot.entity.Records;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordsRepository extends JpaRepository<Records,Integer> {

    List<Records> findTop14ByNameLikeOrderByIdDesc(String name);

    Records findByNameAndDateLike(String name, String date);

    List<Records> findByNameLike(String name);

}
