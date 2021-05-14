package com.example.cepcspringboot.repository;

import com.example.cepcspringboot.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users,Integer> {

    Users findByNameLike(String name);

}
