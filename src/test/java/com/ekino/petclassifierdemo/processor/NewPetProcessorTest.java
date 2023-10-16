package com.ekino.petclassifierdemo.processor;

import com.ekino.petclassifierdemo.model.AdoptionStatus;
import com.ekino.petclassifierdemo.model.PetCsvLine;
import com.ekino.petclassifierdemo.model.PetToCreate;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class NewPetProcessorTest {

    private static final NewPetProcessor NEW_PET_PROCESSOR = new NewPetProcessor();

    @Test
    void should_process_new_pet() {
        //given
        var csvLine = new PetCsvLine("toto", AdoptionStatus.NEW, "cat");

        //when
        var petToCreate = NEW_PET_PROCESSOR.process(csvLine);

        //then
        assertThat(petToCreate).isNotNull();
        assertThat(petToCreate).isInstanceOf(PetToCreate.class);
        assertThat(petToCreate.getName()).isEqualTo("toto");
        assertThat(petToCreate.getStatus()).isEqualTo(AdoptionStatus.NEW);
        assertThat(petToCreate.getSpecies()).isEqualTo("cat");
    }
}
