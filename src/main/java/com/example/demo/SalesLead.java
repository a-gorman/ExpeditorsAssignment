package com.example.demo;

import lombok.Data;

import java.util.Objects;

// Potential extension point: Depending on the domain and application it might be useful to model full name and address
// explicitly as classes. This can help enforce invariants and data validity.
public record SalesLead(
        String firstName,
        String lastName,
        String streetAddress,
        String city,
        String state,
        int ageInYears) {
    public SalesLead {
        Objects.requireNonNull(firstName);
        Objects.requireNonNull(lastName);
        Objects.requireNonNull(streetAddress);
        Objects.requireNonNull(city);
        Objects.requireNonNull(state);
    }
}
