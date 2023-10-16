package com.ekino.petclassifierdemo.processor;

import java.util.UUID;

import com.ekino.petclassifierdemo.model.AdoptionStatus;
import com.ekino.petclassifierdemo.model.PetCsvLine;
import com.ekino.petclassifierdemo.model.PetDbEntity;
import com.ekino.petclassifierdemo.model.PetToUpdate;
import com.ekino.petclassifierdemo.service.PetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UpdatedPetProcessorTest {

    @Mock
    private PetService petService;

    @InjectMocks
    private UpdatedPetProcessor updatedPetProcessor;

    @Test
    void should_process_updated_pet() {
        //given
        var csvLine = new PetCsvLine("toto", AdoptionStatus.WAITING, "cat");
        var petId = UUID.fromString("30b44295-e219-41f3-812b-52519ad197d6");
        given(petService.getPetByName("toto"))
                .willReturn(new PetDbEntity(petId, "toto", AdoptionStatus.NEW, "cat"));

        //when
        var petToUpdate = updatedPetProcessor.process(csvLine);

        //then
        assertThat(petToUpdate).isNotNull();
        assertThat(petToUpdate).isInstanceOf(PetToUpdate.class);
        assertThat(petToUpdate.getId()).isEqualTo(petId);
        assertThat(petToUpdate.getStatus()).isEqualTo(AdoptionStatus.WAITING);
    }
}
