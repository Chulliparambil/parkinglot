package com.app.parkinglot.service;

import java.nio.file.Path;

/**
 * Interface for Parking Input processing
 */
public interface ParkingService {
    void processData(Path filePath);
}
