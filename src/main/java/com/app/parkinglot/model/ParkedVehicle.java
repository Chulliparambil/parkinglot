package com.app.parkinglot.model;

import lombok.Data;

/**
 * Parked vehicle details
 */
@Data
public class ParkedVehicle {
    private int age;
    private int parkingSlot;
    private String registrationNumber;
}
