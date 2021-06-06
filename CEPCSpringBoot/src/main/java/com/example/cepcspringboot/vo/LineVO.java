package com.example.cepcspringboot.vo;

import lombok.Data;

import java.util.List;

@Data
public class LineVO {
    private List<String> dates;
    private List<Integer> number;
}
