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

    private final SalesLeadFileParser parser;
    private final SalesLeadFormatter formatter;

    public ExpeditorsAssignment(SalesLeadFileParser parser, SalesLeadFormatter formatter) {
        this.parser = parser;
        this.formatter = formatter;
    }

    public static void main(String[] args) {
        SpringApplication.run(ExpeditorsAssignment.class, args);
    }

    // If this method was any longer I would definitely break it down into more functions. It's at an awkward length:
    // some like it all laid out top to bottom, others like concise methods. I'd go with what my team likes to see.
    @Override
    public void run(String... args) throws Exception {
        // We can use or create a more meaningful exception type if we want
        if (args.length < 2) {
            throw new Exception("An input file path must be specified");
        }

        boolean useFile = switch(args[0]) {
            case "-f" -> true;
            case "-s" -> false;
            default -> throw new Exception("Unknown option: " + args[0]);
        };

        List<SalesLead> leads;
        if (useFile) {
            var inputFilePath = args[1];
            try (Stream<String> stream = Files.lines(Path.of(inputFilePath))) {
                leads = parser.parse(stream);
            }
        } else {
            leads = parser.parse(args[1].lines());
        }

        var adultSortedLeads = leads.stream()
                .filter(lead -> lead.ageInYears() > 18)
                .sorted(Comparator.comparing(SalesLead::surname).thenComparing(SalesLead::givenName))
                .toList();

        var output = String.format("Households:\n%s\n\nLeads:\n%s",
                formatter.WriteHouseholdSizesToString(CalculateHouseholdSizes(leads)),
                formatter.WriteLeadsToString(adultSortedLeads));

        // We could add some validation that the file paths are valid and are in areas we want to user to write to.
        var outputFilePath = args.length > 2 ? args[2] : null;
        if (outputFilePath != null) {
            try (var writer = new BufferedWriter(new FileWriter(outputFilePath))) {
                writer.write(output);
            }
        } else {
            System.out.print(output);
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
