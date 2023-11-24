package com.ekino.petclassifierdemo.writer;

import com.ekino.petclassifierdemo.model.PetToDelete;
import com.ekino.petclassifierdemo.service.PetService;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class DeletePetWriter implements ItemWriter<PetToDelete> {

    private final PetService petService;

    public DeletePetWriter(PetService petService) {
        this.petService = petService;
    }

    @Override
    public void write(Chunk<? extends PetToDelete> pets) {
        pets.forEach(petService::deletePet);
    }
}
