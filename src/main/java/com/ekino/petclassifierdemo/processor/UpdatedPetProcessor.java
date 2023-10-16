package com.ekino.petclassifierdemo.processor;

import com.ekino.petclassifierdemo.service.PetService;
import com.ekino.petclassifierdemo.model.PetCsvLine;
import com.ekino.petclassifierdemo.model.PetToUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdatedPetProcessor implements ItemProcessor<PetCsvLine, PetToUpdate> {

    private final PetService petService;

    @Override
    public PetToUpdate process(@NonNull PetCsvLine item) {
        var foundPet = petService.getPetByName(item.name());
        return new PetToUpdate(foundPet.id(), item.status());
    }
}
