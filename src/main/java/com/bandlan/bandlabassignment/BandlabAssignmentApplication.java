package com.bandlan.bandlabassignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.bandlan.bandlabassignment.repository")
public class BandlabAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(BandlabAssignmentApplication.class, args);
    }

}
