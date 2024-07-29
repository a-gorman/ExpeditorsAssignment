package com.example.demo;

import java.util.Objects;

// Potential extension point: Depending on the domain and application it might be useful to model full name and address
// explicitly as classes. This can help enforce invariants and data validity.
public record SalesLead(
        String givenName,
        String surname,
        String streetAddress,
        String city,
        String state,
        int ageInYears) {
    public SalesLead {
        Objects.requireNonNull(givenName);
        Objects.requireNonNull(surname);
        Objects.requireNonNull(streetAddress);
        Objects.requireNonNull(city);
        Objects.requireNonNull(state);
    }
}
