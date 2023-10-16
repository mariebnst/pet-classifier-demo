package com.ekino.petclassifierdemo.config;

import java.util.HashMap;

import com.ekino.petclassifierdemo.model.PetAction;
import com.ekino.petclassifierdemo.model.PetCsvLine;
import com.ekino.petclassifierdemo.model.PetToCreate;
import com.ekino.petclassifierdemo.model.PetToDelete;
import com.ekino.petclassifierdemo.model.PetToUpdate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.RecordFieldSetMapper;
import org.springframework.batch.item.support.ClassifierCompositeItemProcessor;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemProcessorBuilder;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemWriterBuilder;
import org.springframework.classify.Classifier;
import org.springframework.classify.SubclassClassifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfiguration {

    @Bean
    public FlatFileItemReader<PetCsvLine> csvReader() {
        return new FlatFileItemReaderBuilder<PetCsvLine>()
                .name("pet-item-reader")
                .resource(new ClassPathResource("sample-data.csv"))
                .delimited()
                .names("name", "status", "species")
                .fieldSetMapper(new RecordFieldSetMapper<>(PetCsvLine.class))
                .build();
    }

    @Bean
    public ClassifierCompositeItemProcessor<PetCsvLine, PetAction> petProcessor(
            Classifier<PetCsvLine, ItemProcessor<?, ? extends PetAction>> processorClassifier
    ) {
        return new ClassifierCompositeItemProcessorBuilder<PetCsvLine, PetAction>()
                .classifier(processorClassifier)
                .build();
    }

    @Bean
    public SubclassClassifier<PetAction, ItemWriter<? extends PetAction>> writerClassifier(ItemWriter<PetToDelete> deletePetWriter,
                                                                                           ItemWriter<PetToCreate> createPetWriter,
                                                                                           ItemWriter<PetToUpdate> updatePetWriter) {
        var typeMap = new HashMap<Class<? extends PetAction>, ItemWriter<? extends PetAction>>();
        typeMap.put(PetToDelete.class, deletePetWriter);
        typeMap.put(PetToCreate.class, createPetWriter);
        typeMap.put(PetToUpdate.class, updatePetWriter);

        var classifier = new SubclassClassifier<PetAction, ItemWriter<? extends PetAction>>();
        classifier.setTypeMap(typeMap);
        return classifier;
    }

    @Bean
    public ClassifierCompositeItemWriter petWriter(Classifier writerClassifier) {
        return new ClassifierCompositeItemWriterBuilder<PetAction>()
                .classifier(writerClassifier)
                .build();
    }

    @Bean
    public Step importPetsStep(JobRepository jobRepository,
                               PlatformTransactionManager transactionManager,
                               FlatFileItemReader<PetCsvLine> csvReader,
                               ClassifierCompositeItemProcessor<PetCsvLine, PetAction> petProcessor,
                               ClassifierCompositeItemWriter<PetAction> petWriter) {
        return new StepBuilder("import-pets-step", jobRepository)
                .<PetCsvLine, PetAction>chunk(100, transactionManager)
                .reader(csvReader)
                .processor(petProcessor)
                .writer(petWriter)
                .build();

    }

    @Bean
    public Job importPetsJob(JobRepository jobRepository,
                             Step importPetsStep) {
        return new JobBuilder("import-pets-job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(importPetsStep)
                .build();
    }
}
