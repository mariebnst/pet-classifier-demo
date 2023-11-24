package com.ekino.petclassifierdemo.writer;

import com.ekino.petclassifierdemo.model.PetToCreate;
import com.ekino.petclassifierdemo.service.PetService;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class CreatePetWriter implements ItemWriter<PetToCreate> {

    private final PetService petService;


    public CreatePetWriter(PetService petService) {
        this.petService = petService;
    }

    @Override
    public void write(Chunk<? extends PetToCreate> pets) {
        pets.forEach(petService::createPet);
    }
}
