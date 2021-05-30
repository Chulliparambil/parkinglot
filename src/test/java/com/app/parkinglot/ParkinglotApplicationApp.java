package com.app.parkinglot;

import com.app.parkinglot.service.ParkingService;
import com.app.parkinglot.service.ParkingServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.nio.file.Path;
import java.nio.file.Paths;


@SpringBootApplication
@ComponentScan(basePackages = "com.app.parkinglot")
class ParkinglotApplicationApp {

    @Bean
    public ParkingService parkingService() {
        return new ParkingServiceImpl();
    }
}
