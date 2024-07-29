package com.example.demo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExpeditorsAssignmentTests {

    @Mock
    private SalesLeadFileParser parser;

    @Mock
    private SalesLeadFormatter formatter;

    @InjectMocks
    private ExpeditorsAssignment assignmentClass;

    // I would ask the story owner/PM if they want leads that are 18 or older, as that makes more sense to me, but
    // requirements as written are for anyone *older* then 18 to be written
    @ParameterizedTest
    @ValueSource(ints = {1, 17, 18, 19, 30})
    public void onlyOccupants_olderThan18_areWritten(int age) throws Exception {
        when(parser.parse(any())).thenReturn(List.of(
                new SalesLead("given", "surname", "address", "city", "state", age)
        ));

        assignmentClass.run("-s", "inputString");

        var shouldPrint = age > 18;
        verify(formatter).WriteLeadsToString(argThat(x -> (x.isEmpty()) != shouldPrint));
    }

    @Test
    public void occupants_areSorted_BySurname_ThenGivenName() throws Exception {
        when(parser.parse(any())).thenReturn(List.of(
                new SalesLead("a", "x", "address", "city", "state", 50),
                new SalesLead("b", "x", "address", "city", "state", 50),
                new SalesLead("a", "y", "address", "city", "state", 50),
                new SalesLead("b", "y", "address", "city", "state", 50)
        ));

        assignmentClass.run("-s", "inputString");

        verify(formatter).WriteLeadsToString(argThat(result ->
                result.get(0).givenName().equals("a") && result.get(0).surname().equals("x")
                && result.get(1).givenName().equals("b") && result.get(1).surname().equals("x")
                && result.get(2).givenName().equals("a") && result.get(2).surname().equals("y")
                && result.get(3).givenName().equals("b") && result.get(3).surname().equals("y"))
        );
    }
}
