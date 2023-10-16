package com.ekino.petclassifierdemo.writer;

import com.ekino.petclassifierdemo.model.PetToCreate;
import com.ekino.petclassifierdemo.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreatePetWriter implements ItemWriter<PetToCreate> {

    private final PetService petService;

    @Override
    public void write(Chunk<? extends PetToCreate> pets) {
        pets.forEach(petService::createPet);
    }
}
