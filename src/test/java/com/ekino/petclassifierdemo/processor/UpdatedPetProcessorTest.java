package com.ekino.petclassifierdemo.processor;

import com.ekino.petclassifierdemo.model.AdoptionStatus;
import com.ekino.petclassifierdemo.model.PetCsvLine;
import com.ekino.petclassifierdemo.model.PetToUpdate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UpdatedPetProcessorTest {

    @InjectMocks
    private UpdatedPetProcessor updatedPetProcessor;

    @Test
    void should_process_updated_pet() {
        //given
        var csvLine = new PetCsvLine("toto", AdoptionStatus.WAITING, "cat");

        //when
        var petToUpdate = updatedPetProcessor.process(csvLine);

        //then
        assertThat(petToUpdate).isNotNull();
        assertThat(petToUpdate).isInstanceOf(PetToUpdate.class);
        assertThat(petToUpdate.getName()).isEqualTo("toto");
        assertThat(petToUpdate.getStatus()).isEqualTo(AdoptionStatus.WAITING);
    }
}
