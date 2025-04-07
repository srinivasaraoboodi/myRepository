package com.service.orderHistory.bean;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private double price;

}
