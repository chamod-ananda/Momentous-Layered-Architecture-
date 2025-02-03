package com.example.momentous.momentous_finalproject.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class ServiceDto {
    private String serviceId;
    private double price;
    private String serviceType;

}
