package com.ekino.petclassifierdemo.model;

public abstract class PetAction {
    public PetAction(String name) {
        this.name = name;
    }

    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
