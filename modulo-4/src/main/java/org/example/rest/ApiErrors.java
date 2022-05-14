package org.example.rest;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApiErrors {
    private List<String> errors = new ArrayList<>();

    public ApiErrors(String message) {
        errors.add(message);
    }

    public ApiErrors(List<String> errors) {
        this.errors = errors;
    }
}
