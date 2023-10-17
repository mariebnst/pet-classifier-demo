package com.ekino.petclassifierdemo.processor;

import com.ekino.petclassifierdemo.model.AdoptionStatus;
import com.ekino.petclassifierdemo.model.PetCsvLine;
import com.ekino.petclassifierdemo.model.PetToDelete;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AdoptedPetProcessorTest {

    @InjectMocks
    private AdoptedPetProcessor adoptedPetProcessor;

    @Test
    void should_process_adopted_pet() {
        //given
        var csvLine = new PetCsvLine("toto", AdoptionStatus.ADOPTED, "cat");

        //when
        var petToUpdate = adoptedPetProcessor.process(csvLine);

        //then
        assertThat(petToUpdate).isNotNull();
        assertThat(petToUpdate).isInstanceOf(PetToDelete.class);
        assertThat(petToUpdate.getName()).isEqualTo("toto");
    }
}
