package com.example.cepcspringboot.controller;

import com.example.cepcspringboot.entity.Records;
import com.example.cepcspringboot.repository.RecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/records")
public class RecordsHandler {
    @Autowired
    private RecordsRepository recordsRepository;

    @GetMapping("/findAll/{page}/{size}")
    public Page<Records> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        Pageable pageable = PageRequest.of(page-1,size);
        return recordsRepository.findAll(pageable);
    }

    @GetMapping("/findByName/{name}")
    public List<Records> findByName(@PathVariable("name") String name){
        return recordsRepository.findByNameLike(name);
    }

    @GetMapping("/findById/{id}")
    public Records findById(@PathVariable("id") Integer id){
        return recordsRepository.findById(id).get();
    }

    @PostMapping("/save")
    public String save( Records records){
        System.out.println("result+++++++++++++++++++++++++++++++"+records);
        Records result = recordsRepository.save(records);
        if(result!=null){
            return "success";
        }else{
            return "error";
        }
    }

    @GetMapping("/findByNameAndDate/{name}/{date}")
    public Records findByNameAndDate(@PathVariable("name") String name,@PathVariable("date") String date){
        return recordsRepository.findByNameAndDateLike(name,date);
    }

    @GetMapping("/judgeRank/{name}")
    public Integer judgeRank(@PathVariable("name") String name) {
        List<Records> list = recordsRepository.findTop14ByNameLikeOrderByIdDesc(name);
        Integer rank = 0;//等级
        for (Records records : list) {
            double d = records.getTemperature();
            if (d > 37.3) {
                rank += 1;
                System.out.println(rank);
            }
            if (records.getPatient().equals("是")) {
                rank += 1;
            }
        }
        return rank;
    }

    @DeleteMapping("/deleteById/{id}")
    public void delete(@PathVariable("id") Integer id){
        recordsRepository.deleteById(id);
    }
}
