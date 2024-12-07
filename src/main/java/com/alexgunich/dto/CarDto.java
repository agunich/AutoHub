package com.alexgunich.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {
    private Long userId;
    private String brand;
    private String model;
    private Integer year;
    private Double mileage;
    private Double price;
    private String description;
}