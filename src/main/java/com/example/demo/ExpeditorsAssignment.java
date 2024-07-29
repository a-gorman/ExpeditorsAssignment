package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@SpringBootApplication
public class ExpeditorsAssignment implements CommandLineRunner {

    final SalesLeadFileParser parser;
    final SalesLeadFormatter formatter;

    public ExpeditorsAssignment(SalesLeadFileParser parser, SalesLeadFormatter formatter) {
        this.parser = parser;
        this.formatter = formatter;
    }

    public static void main(String[] args) {
        SpringApplication.run(ExpeditorsAssignment.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // We can use or create a more meaningful exception type if we want
        if (args.length < 1) {
            throw new Exception("An input file path must be specified");
        }
        var inputFilePath = args[0];
        var outputFilePath = args.length > 1 ? args[1] : null;

        // We could add some validation that the file paths are valid and are in areas we want to user to write to.

        List<SalesLead> leads;
        try (Stream<String> stream = Files.lines(Path.of(inputFilePath))) {
            leads = parser.parse(stream);
        }

        var adultSortedLeads = leads.stream()
                .filter(lead -> lead.ageInYears() > 18)
                .sorted(Comparator.comparing(SalesLead::givenName).thenComparing(SalesLead::surname));

        var output = String.format("Households:\n%s\n\nLeads:%s",
                formatter.WriteHouseholdSizesToString(CalculateHouseholdSizes(leads)),
                formatter.WriteLeadsToString(adultSortedLeads));

        if (outputFilePath != null) {
            try (var writer = new BufferedWriter(new FileWriter(outputFilePath))) {
                writer.write(output);
            }
        } else {
            System.out.println(output);
        }
    }

    // If this was more complicated data, I would introduce a data class, and possibly a component for calculating it
    public Map<String, Integer> CalculateHouseholdSizes(List<SalesLead> salesLeads) {
        var households = new HashMap<String, Integer>();
        for (SalesLead lead : salesLeads) {
            var fullAddress = String.format("%s,%s,%s", lead.streetAddress(), lead.city(), lead.state());
            households.compute(fullAddress, (k, v) -> v == null ? 1 : v + 1);
        }

        return households;
    }
}
