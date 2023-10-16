package com.ekino.petclassifierdemo.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PetToUpdate extends PetAction {
    private UUID id;
    private AdoptionStatus status;
}
