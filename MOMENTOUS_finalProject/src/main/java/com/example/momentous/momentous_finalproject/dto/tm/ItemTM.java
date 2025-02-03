package com.example.momentous.momentous_finalproject.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ItemTM {
    private String itemId;
    private String itemName;
    private String itemDescription;
    private double cost;
    private int quantity;
    private String supplierId;
}
