package com.ekino.petclassifierdemo.classifier;

import com.ekino.petclassifierdemo.model.AdoptionStatus;
import com.ekino.petclassifierdemo.model.PetCsvLine;
import com.ekino.petclassifierdemo.model.PetToCreate;
import com.ekino.petclassifierdemo.model.PetToDelete;
import com.ekino.petclassifierdemo.model.PetToUpdate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.*;

class PetProcessorClassifierTest {

    private final PetProcessorClassifier CLASSIFIER = new PetProcessorClassifier(
            it -> new PetToUpdate(),
            it -> new PetToDelete(),
            it -> new PetToCreate()
    );

    @Test
    void should_classify_as_new() throws Exception {
        //given
        var csvLine = new PetCsvLine("toto", AdoptionStatus.NEW, "cat");

        //when
        var processor = CLASSIFIER.classify(csvLine);

        //then
        assertThat(processor.process(csvLine)).isInstanceOf(PetToCreate.class);
    }

    @Test
    void should_classify_as_adopted() throws Exception {
        //given
        var csvLine = new PetCsvLine("toto", AdoptionStatus.ADOPTED, "cat");

        //when
        var processor = CLASSIFIER.classify(csvLine);

        //then
        assertThat(processor.process(csvLine)).isInstanceOf(PetToDelete.class);
    }

    @ParameterizedTest
    @EnumSource(value = AdoptionStatus.class, names = { "WAITING", "AVAILABLE" })
    void should_classify_as_updated(AdoptionStatus status) throws Exception {
        //given
        var csvLine = new PetCsvLine("toto", status, "cat");

        //when
        var processor = CLASSIFIER.classify(csvLine);

        //then
        assertThat(processor.process(csvLine)).isInstanceOf(PetToUpdate.class);
    }
}
