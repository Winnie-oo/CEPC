package com.example.cepcspringboot.repository;
import com.example.cepcspringboot.entity.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UsersRepositoryTest {
    @Autowired
    private UsersRepository usersRepository;
    @Test
    void findAll(){
        System.out.println(usersRepository.findAll());
    }

    @Test
    void save(){
        Users users = new Users();
        users.setName("王敬珂");
        users.setAddress("老七113");
        users.setPassword("123");
        users.setDay_mark(0);
        Users users1 =usersRepository.save(users);
        System.out.println(users1);
    }
}
