package com.example.cepcspringboot.entity;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Users {
    @Id
    private Integer id;
    private String name;
    private Integer day_mark;
    private String address;
    private String password;
}
