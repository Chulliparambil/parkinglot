package com.app.parkinglot.model;

import lombok.Data;

/**
 * Parking lot details and operations
 */
@Data
public class ParkingLot {
    private int[] parkingSlots;

    /**
     * Create parking lots of size
     * @param lotSize
     * @return
     */
    public boolean createParkingLots(int lotSize) {
        if (null == parkingSlots || parkingSlots.length == 0) {
            parkingSlots = new int[lotSize];
            return true;
        }
        return false;
    }

    /**
     * Leave parking slot
     * @param lotNumber
     * @return
     */
    public boolean leaveParkingLot(int lotNumber) {
        if (lotNumber < parkingSlots.length && parkingSlots[lotNumber - 1] == 1) {
            parkingSlots[lotNumber - 1] = 0;
            return true;
        }
        return false;
    }

    /**
     * Allocate a parking slot
     * @return
     */
    public int allotParkingSlot() {
        int lotNumber = 0;
        for (int i = 0; i < parkingSlots.length; i++) {
            if (parkingSlots[i] == 0) {
                lotNumber = i + 1;
                parkingSlots[i] = 1;
                break;
            }
        }
        return lotNumber;
    }
}
