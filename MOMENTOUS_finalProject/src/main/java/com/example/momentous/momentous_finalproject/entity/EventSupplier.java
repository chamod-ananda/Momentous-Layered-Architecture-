package com.example.momentous.momentous_finalproject.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class EventSupplier {
    private String eventId;
    private String supplierId;
    private String itemId;
    private int itemQuantity;
    private double totalPrice;
}
