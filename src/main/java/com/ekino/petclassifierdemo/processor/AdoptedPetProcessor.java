package com.ekino.petclassifierdemo.processor;

import com.ekino.petclassifierdemo.model.PetCsvLine;
import com.ekino.petclassifierdemo.model.PetToDelete;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class AdoptedPetProcessor implements ItemProcessor<PetCsvLine, PetToDelete> {

    @Override
    public PetToDelete process(@NonNull PetCsvLine item) {
        return new PetToDelete(item.name());
    }
}
