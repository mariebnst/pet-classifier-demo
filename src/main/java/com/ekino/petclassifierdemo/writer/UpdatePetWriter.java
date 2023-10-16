package com.ekino.petclassifierdemo.writer;

import com.ekino.petclassifierdemo.model.PetToUpdate;
import com.ekino.petclassifierdemo.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdatePetWriter implements ItemWriter<PetToUpdate> {

    private final PetService petService;

    @Override
    public void write(Chunk<? extends PetToUpdate> pets) {
        pets.forEach(petService::updatePet);
    }
}
