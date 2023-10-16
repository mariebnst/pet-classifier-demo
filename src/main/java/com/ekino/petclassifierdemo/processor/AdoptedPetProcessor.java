package com.ekino.petclassifierdemo.processor;

import com.ekino.petclassifierdemo.service.PetService;
import com.ekino.petclassifierdemo.model.PetCsvLine;
import com.ekino.petclassifierdemo.model.PetToDelete;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdoptedPetProcessor implements ItemProcessor<PetCsvLine, PetToDelete> {

    private final PetService petService;

    @Override
    public PetToDelete process(@NonNull PetCsvLine item) {
        var foundPet = petService.getPetByName(item.name());
        return new PetToDelete(foundPet.id());
    }
}
