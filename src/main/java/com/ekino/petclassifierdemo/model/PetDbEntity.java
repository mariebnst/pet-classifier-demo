package com.ekino.petclassifierdemo.model;

import java.util.UUID;

public record PetDbEntity(UUID id,
                          String name,
                          AdoptionStatus status,
                          String species) {
}
