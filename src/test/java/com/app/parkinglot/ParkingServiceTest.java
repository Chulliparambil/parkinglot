package com.app.parkinglot;

import com.app.parkinglot.service.ParkingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.nio.file.Path;
import java.nio.file.Paths;

@ActiveProfiles("test")
@SpringBootTest(classes = ParkinglotApplicationApp.class)
public class ParkingServiceTest {

    @Autowired
    ParkingService parkingService;

    @Test
    void testParkingAllocationService() {
        Path filePath = Paths.get("src/test/resources/sample/input.txt");
        parkingService.processData(filePath);
    }
}
