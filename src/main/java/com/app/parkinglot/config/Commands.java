package com.app.parkinglot.config;

import lombok.experimental.UtilityClass;

/**
 * Commands provided in input file
 */
@UtilityClass
public class Commands {
    public static String CREATE_PARKING_LOT = "Create_parking_lot";
    public static String PARK = "Park";
    public static String DRIVER_AGE = "driver_age";
    public static String SLOT_NUMBERS_FOR_DRIVER_OF_AGE = "Slot_numbers_for_driver_of_age";
    public static String SLOT_NUMBER_FOR_CAR_WITH_NUMBER = "Slot_number_for_car_with_number";
    public static String VEHICLE_REGISTRATION_NUMBER_FOR_DRIVER_OF_AGE = "Vehicle_registration_number_for_driver_of_age";
    public static String LEAVE = "Leave";
}
