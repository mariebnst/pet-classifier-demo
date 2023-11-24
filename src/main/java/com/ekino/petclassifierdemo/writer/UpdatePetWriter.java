package com.ekino.petclassifierdemo.writer;

import com.ekino.petclassifierdemo.model.PetToUpdate;
import com.ekino.petclassifierdemo.service.PetService;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class UpdatePetWriter implements ItemWriter<PetToUpdate> {

    private final PetService petService;

    public UpdatePetWriter(PetService petService) {
        this.petService = petService;
    }

    @Override
    public void write(Chunk<? extends PetToUpdate> pets) {
        pets.forEach(petService::updatePet);
    }
}
