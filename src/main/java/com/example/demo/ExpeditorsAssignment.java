package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class ExpeditorsAssignment implements CommandLineRunner {

    final SalesLeadFileParser parser;

    public ExpeditorsAssignment(SalesLeadFileParser parser) {
        this.parser = parser;
    }

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

        try(Stream<String> stream = Files.lines(Path.of(inputFilePath))) {
            List<SalesLead> leads = parser.parse(stream);

            System.out.println(leads);
        }

    }
}
