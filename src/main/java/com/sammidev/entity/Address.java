package com.sammidev.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
public class Address {
    @NotBlank(message = "city must be not empty")
    private String city;
    @NotBlank(message = "postCode must be not empty")
    private String postCode;
    @NotBlank(message = "country must be not empty")
    private String country;
}