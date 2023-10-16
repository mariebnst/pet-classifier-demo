package com.ekino.petclassifierdemo.model;

public record PetCsvLine(
        String name,
        AdoptionStatus status,
        String species) {
}
