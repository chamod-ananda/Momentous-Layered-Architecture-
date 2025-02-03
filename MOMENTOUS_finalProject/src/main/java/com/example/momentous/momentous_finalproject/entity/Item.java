package com.example.momentous.momentous_finalproject.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Item {
    private String itemId;
    private String itemName;
    private String itemDescription;
    private double cost;
    private int quantity;
    private String supplierId;
}
