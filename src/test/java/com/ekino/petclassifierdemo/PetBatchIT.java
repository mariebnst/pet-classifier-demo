package com.ekino.petclassifierdemo;

import com.ekino.petclassifierdemo.mapper.PetDbRowMapper;
import com.ekino.petclassifierdemo.model.AdoptionStatus;
import com.ekino.petclassifierdemo.model.PetDbEntity;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@SpringBatchTest
@ActiveProfiles({ "it" })
class PetBatchIT {

    @Autowired
    protected JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void should_launch_job() throws Exception {
        //when
        var jobExecution = jobLauncherTestUtils.launchJob();

        //then
        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);

        var petsInDb = jdbcTemplate.query("SELECT id, name, status, species FROM pet", new PetDbRowMapper());
        assertThat(petsInDb)
                .extracting(PetDbEntity::name, PetDbEntity::status, PetDbEntity::species)
                .containsExactlyInAnyOrder(
                        tuple("Isaac Mewton", AdoptionStatus.NEW, "cat"),
                        tuple("Chris P. Bacon", AdoptionStatus.NEW, "pig"),
                        tuple("Frodog", AdoptionStatus.WAITING, "dog"),
                        tuple("Meowy Poppins", AdoptionStatus.AVAILABLE, "cat")
                );
    }
}
