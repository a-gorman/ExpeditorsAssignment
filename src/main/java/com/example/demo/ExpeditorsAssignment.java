package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExpeditorsAssignment implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ExpeditorsAssignment.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // We can use or create a more meaningful exception type if we want
        if(args.length < 1) {
            throw new Exception("An input file path must be specified");
        }
        var inputFilePath = args[0];
        var outputFilePath = args.length > 1 ? args[1] : null;
    }
}
