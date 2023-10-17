package com.ekino.petclassifierdemo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetToCreate extends PetAction {
    private AdoptionStatus status;
    private String species;

    public PetToCreate(String name, AdoptionStatus status, String species) {
        super(name);
        this.status = status;
        this.species = species;
    }
}
