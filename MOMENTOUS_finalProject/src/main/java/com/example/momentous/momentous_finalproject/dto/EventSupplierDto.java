package com.example.momentous.momentous_finalproject.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class EventSupplierDto {
    private String eventId;
    private String supplierId;
    private String itemId;
    private int itemQuantity;
    private double totalPrice;
}
