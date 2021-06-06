package com.example.cepcspringboot.controller;

import com.example.cepcspringboot.entity.Records;
import com.example.cepcspringboot.repository.RecordsRepository;
import com.example.cepcspringboot.vo.LineVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/Line")
public class LineHandler {
    @Autowired
    private RecordsRepository recordsRepository;

    @GetMapping("/findData")
    public List<Records> lineVOList(){



        return recordsRepository.findByNameLike(name);
    }

    @GetMapping("/findById/{id}")
    public Records findById(@PathVariable("id") Integer id){
        return recordsRepository.findById(id).get();
    }

    @PostMapping("/save")
    public String save( Records records){
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
        int rank = -1;//等级0不安全，1限制，2安全
        int count = checkContinuation(name);//查找连续填报是否超过七日
        if(list.size()>0){
            rank+=1;
            if (count >6){
                rank+=1;
            }
            count = 0;//查找发烧记录以及确诊记录
            for (Records records : list) {
                double d = records.getTemperature();
                if (d > 37.3) {
                    count++;
                }
                if (!(records.getPatient().equals("否"))) {
                    count++;
                }
            }
            if(count==0){
                rank+=1;
            }else rank = 0;//有发烧确证记录，直接判为红色
        }
        return rank;
    }

    @GetMapping("/checkContinuation/{name}")
    public Integer checkContinuation(@PathVariable("name") String name) {
        List<Records> list = recordsRepository.findTop14ByNameLikeOrderByIdDesc(name);
        if (list.size()>0 && continuation==0) continuation=1;
        for (Records records : list) {
            date_after = records.getDate();
            if(date_before!=null){
                int dd_after = Integer.parseInt(date_after.substring(8));
                int mm_after = Integer.parseInt(date_after.substring(5,7));
                int yy_after = Integer.parseInt(date_after.substring(0,4));

                int dd_before = Integer.parseInt(date_before.substring(8));
                int mm_before = Integer.parseInt(date_before.substring(5,7));
                int yy_before = Integer.parseInt(date_before.substring(0,4));

                if(yy_after==yy_before){
                    if(mm_after==mm_before){
                        if(dd_after==dd_before-1){
                            continuation++;
                        }
                        else break;
                    }
                    else if(mm_after==mm_before-1){
                        if(dd_after == getValue(yy_after,mm_after) || dd_before == 1 ){
                            continuation++;
                        }
                        else break;
                    }
                    else break;
                }else if(yy_after==yy_before-1) {
                    if (mm_after == 12 || mm_before == 1) {
                        if (dd_after == getValue(yy_after,mm_after) || dd_before == 1) {
                            continuation++;
                        }
                        else break;
                    }
                    else break;
                }
            }
            date_before = records.getDate();
        }
        System.out.println("continuation----------------"+continuation);
        return continuation;
    }

    @DeleteMapping("/deleteById/{id}")
    public void delete(@PathVariable("id") Integer id){
        recordsRepository.deleteById(id);
    }

    private int getValue(int year,int month){
        int m=0;
        switch (month){
            case 1: m=31;break;
            case 3: m=31;break;
            case 5: m=31;break;
            case 7: m=31;break;
            case 8: m=31;break;
            case 10: m=31;break;
            case 12: m=31;break;
            case 2:
                if(year%4==0 && year%100!=0)
                    m=29;
                else
                    m=28;
                break;
            case 4: m=30;break;
            case 6: m=30;break;
            case 9: m=30;break;
            case 11: m=30;break;
        }
        return m;
    }

}
