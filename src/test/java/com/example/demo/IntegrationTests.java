package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(classes = { SalesLeadFormatter.class, SalesLeadFileParser.class, ExpeditorsAssignment.class }, args = { "-f", "src/test/resources/TestInput.csv" })
class IntegrationTests {

    private final String expectedOutput =
            """
                    Households:
                    1:\t234 2nd ave,tacoma,FL
                    1:\t234 2nd ave,seattle,WA
                    4:\t123 main st,seattle,WA
                    2:\t234 2nd ave,tacoma,WA
                    2:\t345 3rd blvd apt 200,seattle,WA

                    Leads:
                    carol, johnson, 234 2nd ave, 67
                    frank, jones, 234 2nd ave, 23
                    alice, smith, 123 main st, 45
                    dave, smith, 123 main st, 43
                    eve, smith, 234 2nd ave, 25
                    bob, williams, 234 2nd ave, 26""";

    @Test
    void contextLoads() {
    }

    // We could also make sure we can write to files here, but I elided that effort
    @Test
    void expectedOutput_can_beWrittenToConsole(CapturedOutput output) {
        assertThat(output.getOut()).containsOnlyOnce(expectedOutput);
    }
}
