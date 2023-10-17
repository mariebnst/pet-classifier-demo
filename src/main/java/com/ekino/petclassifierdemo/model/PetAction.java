package com.ekino.petclassifierdemo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class PetAction {
    public PetAction(String name) {
        this.name = name;
    }

    protected String name;
}
