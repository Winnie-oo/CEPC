package com.example.cepcspringboot.controller;
import com.example.cepcspringboot.entity.Users;
import com.example.cepcspringboot.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersHandler {
    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/findAll/{page}/{size}")
    public Page<Users> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable = PageRequest.of(page-1,size);
        return usersRepository.findAll(pageable);
    }

    @PostMapping("/save")
    public String save(@RequestBody Users users){
        users.setDay_mark(0);
        Users result = usersRepository.save(users);
        if(result!=null){
            return "success";
        }else{
            return "error";
        }
    }
}
