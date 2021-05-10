package com.example.cepcspringboot.repository;

import com.example.cepcspringboot.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users,Integer> {
}
