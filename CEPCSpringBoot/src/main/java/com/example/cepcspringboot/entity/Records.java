package com.example.cepcspringboot.entity;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Records {
    @Id
    private Integer id;
    private String name;
    private String temperature;
    private String date;
    private String address;
}
