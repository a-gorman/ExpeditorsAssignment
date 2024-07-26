package com.example.demo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SaleLeadFileParserTests {

    final SalesLeadFileParser parser = new SalesLeadFileParser();

    @ParameterizedTest
    @CsvSource(value = {
            "Dave,dave",
            "Alice,alice",
            "Bob,bob",
            "Carol,carol",
            "Eve,eve",
            "Frank,frank",
            "George,george",
            "Helen,helen",
            "Ian,ian",
            "Jane,jane"
    })
    public void Parser_Should_CorrectlyParse_FirstName(String nameInput, String expectedValue) {
        var input = String.format("\"%1$s\",\"Smith\",\"123 main st.\",\"seattle\",\"wa\",\"43\"", nameInput).lines();

        var result = new SalesLeadFileParser().parse(input);
        assertThat(result.get(0).firstName()).isEqualTo(expectedValue);
    }
}
