package com.a2.newsbyte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NewsBytesApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsBytesApplication.class, args);
    }

}
