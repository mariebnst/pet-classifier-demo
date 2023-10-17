package com.ekino.petclassifierdemo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetToUpdate extends PetAction {
    private AdoptionStatus status;

    public PetToUpdate(String name, AdoptionStatus status) {
        super(name);
        this.status = status;
    }
}
