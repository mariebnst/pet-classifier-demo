package com.ekino.petclassifierdemo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PetToCreate extends PetAction {
    private String name;
    private AdoptionStatus status;
    private String species;
}
