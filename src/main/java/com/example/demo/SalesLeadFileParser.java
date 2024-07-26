package com.example.demo;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
// This class should be given a name that reflects the domain from which the file type comes from. Here I imagine the
// input data represents sales lead data, possibly purchased from a third party company
public class SalesLeadFileParser {

    // Pure functions are easier to test. Technically this could be static but making it an instance method introduces
    // a testing seam for easier unit tests. Though Mockito these days can easily mock statics, so that might be unnecessary
    public List<SalesLead> parse(Stream<String> inputStream) {
        if (inputStream == null) {
            return Collections.emptyList();
        }

        // Eager evaluation might be inappropriate if files are very large.
        // Leaving things to operate on steams alleviates that pain in most cases
        return inputStream
                .map(entry -> Arrays.asList(entry.split(",")))
                .map(this::EntryToLead)
                .sequential()
                .collect(Collectors.toList());
    }

    // This method presumes that we will always have data in the same format. If we are getting data from multiple
    // or informal sources, we might want to use a more dynamic, configurable solution. If we wanted to be most general
    // we could parse to a map, but it's rare that we don't have a canonical data model
    private SalesLead EntryToLead(List<String> columns) {
        // Since Java doesn't have named arguments, implementing a builder (via lombok) could really help making this code self-documenting.
        // I just went with comments here to be lazy

        // Also note that it would be better to validate the input data and throw meaningful exceptions. We will just
        // blow up if parsing fails with bad error messages in this case.

        // Making everything lower case might not be appropriate if we want to capture the original data exactly. Often,
        // regularizing the data and handling casing on the front end can make things easier for the backend.
        return new SalesLead(
                columns.get(0).toLowerCase(), // First Name
                columns.get(1).toLowerCase(), // Last Name
                RegularizeAddress(columns.get(2)), // street Address
                columns.get(3).toLowerCase(), // City
                columns.get(4).toLowerCase(), // State
                Integer.parseInt(columns.get(5))  // Age
        );
    }

    // If rules like this become very complicated, it could be nice to kick this out to another class to introduce an
    // additional testing seam. Here things are simple enough that testing will still be pretty easy.
    // Address are a bit tricky since people can type dots after abbreviation.
    private String RegularizeAddress(String address) {

        // I imagine there would be more rules here, like st -> street, which justifies making it its own method
        return address.replace(".", "").toLowerCase();
    }
}
