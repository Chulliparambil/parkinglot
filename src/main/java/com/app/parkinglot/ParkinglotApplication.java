package com.app.parkinglot;

import com.app.parkinglot.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Profile("!test")
@SpringBootApplication
public class ParkinglotApplication implements CommandLineRunner {

    @Autowired
    private ParkingService parkingService;

    public static void main(String[] args) {
        SpringApplication.run(ParkinglotApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Enter input file-path (eg." +
                ".D:\\work\\practice\\input.txt):");
        BufferedReader br =
                new BufferedReader(new InputStreamReader(System.in));
        String fileName = null;
        try {
            fileName = br.readLine().trim();
            Path filePath = Paths.get(fileName);
            if (Files.notExists(filePath)) {
                System.out.println("File not found. Please provide a valid input file path");
            } else {
                parkingService.processData(filePath);
            }
        } catch (IOException e) {
            System.out.println("Invalid input. Please provide a valid input file path");
        }
    }
}
