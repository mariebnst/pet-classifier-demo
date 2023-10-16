package com.ekino.petclassifierdemo.processor;

import java.util.UUID;

import com.ekino.petclassifierdemo.model.AdoptionStatus;
import com.ekino.petclassifierdemo.model.PetCsvLine;
import com.ekino.petclassifierdemo.model.PetDbEntity;
import com.ekino.petclassifierdemo.model.PetToDelete;
import com.ekino.petclassifierdemo.service.PetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class AdoptedPetProcessorTest {

    @Mock
    private PetService petService;

    @InjectMocks
    private AdoptedPetProcessor adoptedPetProcessor;

    @Test
    void should_process_adopted_pet() {
        //given
        var csvLine = new PetCsvLine("toto", AdoptionStatus.ADOPTED, "cat");
        var petId = UUID.fromString("aaeb924b-bb45-4451-b401-3c704114a50b");
        given(petService.getPetByName("toto"))
                .willReturn(new PetDbEntity(petId, "toto", AdoptionStatus.WAITING, "cat"));

        //when
        var petToUpdate = adoptedPetProcessor.process(csvLine);

        //then
        assertThat(petToUpdate).isNotNull();
        assertThat(petToUpdate).isInstanceOf(PetToDelete.class);
        assertThat(petToUpdate.getId()).isEqualTo(petId);
    }
}
