package org.sumerge.careerpackageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.sumerge.shared"})
public class CareerPackageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CareerPackageServiceApplication.class, args);
    }

}
