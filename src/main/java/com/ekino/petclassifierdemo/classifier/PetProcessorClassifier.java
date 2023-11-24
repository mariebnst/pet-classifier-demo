package com.ekino.petclassifierdemo.classifier;

import com.ekino.petclassifierdemo.model.PetAction;
import com.ekino.petclassifierdemo.model.PetCsvLine;
import com.ekino.petclassifierdemo.model.PetToCreate;
import com.ekino.petclassifierdemo.model.PetToDelete;
import com.ekino.petclassifierdemo.model.PetToUpdate;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.classify.Classifier;
import org.springframework.stereotype.Component;

@Component
public class PetProcessorClassifier implements Classifier<PetCsvLine, ItemProcessor<?, ? extends PetAction>> {

    private final ItemProcessor<PetCsvLine, PetToUpdate> updatedPetProcessor;
    private final ItemProcessor<PetCsvLine, PetToDelete> adoptedPetProcessor;
    private final ItemProcessor<PetCsvLine, PetToCreate> newPetProcessor;

    public PetProcessorClassifier(ItemProcessor<PetCsvLine, PetToUpdate> updatedPetProcessor,
                                  ItemProcessor<PetCsvLine, PetToDelete> adoptedPetProcessor,
                                  ItemProcessor<PetCsvLine, PetToCreate> newPetProcessor) {
        this.updatedPetProcessor = updatedPetProcessor;
        this.adoptedPetProcessor = adoptedPetProcessor;
        this.newPetProcessor = newPetProcessor;
    }

    @Override
    public ItemProcessor<PetCsvLine, ? extends PetAction> classify(PetCsvLine petLine) {
        return switch (petLine.status()) {
            case NEW -> newPetProcessor;
            case ADOPTED -> adoptedPetProcessor;
            case WAITING, AVAILABLE -> updatedPetProcessor;
        };
    }
}
