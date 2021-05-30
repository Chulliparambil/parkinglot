package com.app.parkinglot.service;

import com.app.parkinglot.config.Commands;
import com.app.parkinglot.config.MessageConstants;
import com.app.parkinglot.model.ParkedVehicle;
import com.app.parkinglot.model.ParkingLot;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Implementation of Parking service
 */
@Service
public class ParkingServiceImpl implements ParkingService {

    private ParkingLot parkingLot;
    private List<ParkedVehicle> parkedVehicleList;

    /**
     * Processes file input commands and outputs parking allocation details for the given input.
     *
     * @param filePath File path provided by user
     */
    @Override
    public void processData(Path filePath) {
        try (Stream<String> commandInputs = Files.lines(filePath)) {
            commandInputs.forEach(command -> {
                String[] commandArray = command.split(" ");
                if (commandArray.length == 2) {
                    if (commandArray[0].equalsIgnoreCase(Commands.CREATE_PARKING_LOT)) {
                        if (null == parkingLot) {
                            parkingLot = new ParkingLot();
                        }
                        if (parkingLot.createParkingLots(Integer.parseInt(commandArray[1]))) {
                            System.out.println("Created parking of " + commandArray[1] + " slots");
                        } else {
                            System.out.println(MessageConstants.PARKING_LOT_EXISTS);
                        }
                    } else if (commandArray[0].equalsIgnoreCase(Commands.LEAVE)) {
                        leaveParking(parkingLot, parkedVehicleList, commandArray[1]);
                    } else if (commandArray[0].equalsIgnoreCase(Commands.SLOT_NUMBER_FOR_CAR_WITH_NUMBER)) {
                        getSlotForRegisteredVehicleNumber(parkedVehicleList, commandArray);
                    } else if (commandArray[0].equalsIgnoreCase(Commands.SLOT_NUMBERS_FOR_DRIVER_OF_AGE)) {
                        getSlotsForDriversOfAge(parkedVehicleList, commandArray);
                    } else if (commandArray[0].equalsIgnoreCase(Commands.VEHICLE_REGISTRATION_NUMBER_FOR_DRIVER_OF_AGE)) {
                        getRegistrationNumbersForDriverOfAge(parkedVehicleList, commandArray);
                    }
                } else if (commandArray.length == 4 && commandArray[0].equals(Commands.PARK)) {
                    if (parkedVehicleList == null) {
                        parkedVehicleList = new ArrayList<>();
                    }
                    parkVehicleInSlot(parkingLot, parkedVehicleList, commandArray);
                } else {
                    System.out.println(MessageConstants.INVALID_COMMAND);
                }
            });
        } catch (IOException e) {
            System.out.println("Error while reading input file. Please provide valid input file");
        }
    }

    /**
     * Parks vehicle in slot if available
     *
     * @param parkingLot
     * @param parkedVehicleList
     * @param commandArray
     */
    private void parkVehicleInSlot(ParkingLot parkingLot,
                                   List<ParkedVehicle> parkedVehicleList,
                                   String[] commandArray) {
        if (commandArray[2].equals(Commands.DRIVER_AGE)) {
            int lotNo = parkingLot.allotParkingSlot();
            if (lotNo == 0) {
                System.out.println(MessageConstants.PARKING_NOT_AVAILABLE);
                return;
            }
            ParkedVehicle parkedVehicle = new ParkedVehicle();
            parkedVehicle.setRegistrationNumber(commandArray[1]);
            parkedVehicle.setParkingSlot(lotNo);
            parkedVehicle.setAge(Integer.parseInt(commandArray[3]));
            parkedVehicleList.add(parkedVehicle);
            System.out.println("Car with vehicle registration " +
                    "number \"" + parkedVehicle.getRegistrationNumber() + "\" has been " +
                    "parked at " +
                    "slot number " + parkedVehicle.getParkingSlot());
        } else {
            System.out.println(MessageConstants.INVALID_COMMAND);
        }
    }

    /**
     * Get registration number of cars for given driver age
     *
     * @param parkedVehicleList
     * @param commandArray
     */
    private void getRegistrationNumbersForDriverOfAge(List<ParkedVehicle> parkedVehicleList,
                                                      String[] commandArray) {
        int vehiclesFound = 0;
        StringBuilder registrationNumbers = new StringBuilder();
        for (ParkedVehicle parkedVehicle : parkedVehicleList) {
            if (parkedVehicle.getAge() == Integer.parseInt(commandArray[1])) {
                vehiclesFound++;
                registrationNumbers.append(parkedVehicle.getRegistrationNumber()).append(",");
            }
        }
        if (vehiclesFound > 0) {
            System.out.println(registrationNumbers.substring(0, registrationNumbers.length() - 1));
        } else {
            System.out.println(MessageConstants.NO_PARKED_CAR_MATCHES_THE_QUERY);
        }
    }

    /**
     * Get slots for driver of given age
     *
     * @param parkedVehicleList
     * @param commandArray
     */
    private void getSlotsForDriversOfAge(List<ParkedVehicle> parkedVehicleList,
                                         String[] commandArray) {
        int vehiclesFound = 0;
        StringBuilder slots = new StringBuilder();
        for (ParkedVehicle parkedVehicle : parkedVehicleList) {
            if (parkedVehicle.getAge() == Integer.parseInt(commandArray[1])) {
                vehiclesFound++;
                slots.append(parkedVehicle.getParkingSlot()).append(",");
            }
        }
        if (vehiclesFound > 0) {
            System.out.println(slots.substring(0, slots.length() - 1));
        } else {
            System.out.println(MessageConstants.NO_PARKED_CAR_MATCHES_THE_QUERY);
        }
    }

    /**
     * Get slot for registered vehicle number
     *
     * @param parkedVehicleList
     * @param commandArray
     */
    private void getSlotForRegisteredVehicleNumber(List<ParkedVehicle> parkedVehicleList,
                                                   String[] commandArray) {
        boolean vehicleFound = false;
        for (ParkedVehicle parkedVehicle : parkedVehicleList) {
            if (parkedVehicle.getRegistrationNumber().equals(commandArray[1])) {
                vehicleFound = true;
                System.out.println(parkedVehicle.getParkingSlot());
                break;
            }
        }
        if (!vehicleFound) {
            System.out.println(MessageConstants.NO_PARKED_CAR_MATCHES_THE_QUERY);
        }
    }

    /**
     * Leave parking slot
     *
     * @param parkingLot
     * @param parkedVehicleList
     * @param slotNumber
     */
    private void leaveParking(ParkingLot parkingLot, List<ParkedVehicle> parkedVehicleList
            , String slotNumber) {
        if (!parkingLot.leaveParkingLot(Integer.parseInt(slotNumber))) {
            System.out.println(MessageConstants.NO_PARKED_CAR_MATCHES_THE_QUERY);
        }
        Predicate<ParkedVehicle> isParked =
                item -> item.getParkingSlot() == Integer.parseInt(slotNumber);
        parkedVehicleList.stream().filter(isParked).forEach(p -> System.out.println("Slot " +
                "number " + p.getParkingSlot() + " vacated, the car with vehicle " +
                "registration number \"" + p.getRegistrationNumber() + "\" left the " +
                "space, " +
                "the driver of the car was of age " + p.getAge()));
        parkedVehicleList.removeIf(isParked);
    }
}
