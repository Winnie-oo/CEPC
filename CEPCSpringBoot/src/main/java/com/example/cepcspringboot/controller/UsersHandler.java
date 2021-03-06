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
    public String save(Users users){
        System.out.println("--------------"+users);
//        System.out.println(users.toString());

        Users result = usersRepository.save(users);
        if(result!=null){
            return "success";
        }else{
            return "error";
        }
    }

    @GetMapping("/findById/{id}")
    public Users findById(@PathVariable("id") Integer id){
        return usersRepository.findById(id).get();
    }

    @PostMapping("/upDateUser")
    public String upDateUser(Users users){
        System.out.println(users);
        Users users1 = usersRepository.findById(users.getId()).get();
        users1.setName(users.getName());
        users1.setAddress(users.getAddress());
        users1.setTel(users.getTel());
        users1.setGender(users.getGender());
        users1.setPassword(users.getPassword());
        Users result = usersRepository.save(users1);
        if(result!=null){
            return "success";
        }else{
            return "error";
        }
    }

    @PostMapping("/upDatePw")
    public String upDatePw(Users users){
        System.out.println(users);
        Users users1 = usersRepository.findByNameLike(users.getName());
        users1.setPassword(users.getPassword());
        Users result = usersRepository.save(users1);
        if(result!=null){
            return "success";
        }else{
            return "error";
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public void delete(@PathVariable("id") Integer id){
        usersRepository.deleteById(id);
    }

    @GetMapping("/findByName/{name}")
    public Users findByName(@PathVariable("name") String name){
        return usersRepository.findByNameLike(name);
    }

}
