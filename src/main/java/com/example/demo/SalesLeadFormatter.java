package com.example.demo;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
// This class is awkward, maybe due to the nature of this being a toy problem that would be better as a small
// python script. I would ask a coworker for their opinion on how divide responsibilities. However, this makes a natural
// testing boundary.
public class SalesLeadFormatter {

    public String WriteHouseholdSizesToString(Map<String, Integer> householdSizes) {
        return householdSizes.entrySet()
                .stream()
                .map(entry -> String.format("%d:\t%s", entry.getValue(), entry.getKey()))
                .collect(Collectors.joining("\n"));
    }

    // Formats leads into a single string
    public String WriteLeadsToString(List<SalesLead> leads) {
        return leads.stream()
                .map(l -> String.format("%s, %s, %s, %d", l.givenName(), l.surname(), l.streetAddress(), l.ageInYears()))
                .collect(Collectors.joining("\n"));
    }
}
