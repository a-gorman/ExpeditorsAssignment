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

    @ParameterizedTest
    @CsvSource(value = {
            "Smith,smith",
            "Williams,williams",
            "Johnson,johnson",
            "Jones,jones",
            "Brown,brown",
    })
    public void Parser_Should_CorrectlyParse_LastName(String nameInput, String expectedValue) {
        var input = String.format("\"Dave\",\"%1$s\",\"123 main st.\",\"seattle\",\"wa\",\"43\"", nameInput).lines();

        var result = new SalesLeadFileParser().parse(input);
        assertThat(result.get(0).lastName()).isEqualTo(expectedValue);
    }

    @ParameterizedTest
    @CsvSource( value = {
            "123 main st.,123 main st",
            "123 Main St.,123 main st",
            "234 2nd Ave.,234 2nd ave",
            "234 2nd Ave,234 2nd ave",
            "234 2nd Ave.,234 2nd ave",
            "234 2nd Ave.,234 2nd ave",
            "345 3rd Blvd., Apt. 200,345 3rd blvd, apt 200",
            "345 3rd Blvd. Apt. 200,345 3rd blvd apt 200",
            "'123 main st ',123 main st ",
            "123 Main St.,123 main st",
    })
    public void Parser_Should_CorrectlyParse_Address(String address, String expectedValue) {
        var input = String.format("\"Dave\",\"Smith\",\"%1$s\",\"seattle\",\"wa\",\"43\"", address).lines();

        var result = new SalesLeadFileParser().parse(input);
        assertThat(result.get(0).streetAddress()).isEqualTo(expectedValue);
    }
}
