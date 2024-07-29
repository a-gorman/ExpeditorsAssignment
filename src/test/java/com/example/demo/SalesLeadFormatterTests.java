package com.example.demo;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SalesLeadFormatterTests {

    private final SalesLeadFormatter formatter = new SalesLeadFormatter();

    @Test
    public void HouseholdSizes_areFormattedCorrectly() {
        // Makes test not flaky by guaranteeing iteration order
        var input = new LinkedHashMap<String, Integer>();
        input.put("address1", 2);
        input.put("address2", 10);

        var result = formatter.WriteHouseholdSizesToString(input);

        assertThat(result).isEqualTo("2:\taddress1\n10:\taddress2");
    }

    @Test
    public void SalesLeads_areFormattedCorrectly() {
        var input = List.of(
                new SalesLead("Dave", "Smith", "123 main st", "seattle", "WA", 43),
                new SalesLead("Alice", "Smith", "123 main st", "seattle", "WA", 45)
        );

        var result = formatter.WriteLeadsToString(input);

        assertThat(result).isEqualTo("Dave, Smith, 123 main st, seattle, WA, 43\nAlice, Smith, 123 main st, seattle, WA, 45");
    }
}
