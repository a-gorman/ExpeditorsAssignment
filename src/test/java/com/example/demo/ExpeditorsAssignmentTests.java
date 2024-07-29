package com.example.demo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
}
