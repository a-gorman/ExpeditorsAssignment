package com.example.demo;

import lombok.Data;

import java.util.Objects;

/**
 * @param firstName Potential extension point: Depending on the domain and application it might be useful to model full name and address explicitly as classes. This can help enforce invariants and data validity.
 */ // Lombok might be a bit overkill in such a simple project, but it's very common in enterprise software, so I'll use it
// here as well.
public record SalesLead(
        String firstName,
        String lastName,
        int ageInYears,
        String streetAddress,
        String city,
        String state) {
    public SalesLead {
        Objects.requireNonNull(firstName);
        Objects.requireNonNull(lastName);
        Objects.requireNonNull(streetAddress);
        Objects.requireNonNull(city);
        Objects.requireNonNull(state);
    }
}
