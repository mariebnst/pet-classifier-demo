package com.ekino.petclassifierdemo;

import java.util.Map;

import com.ekino.petclassifierdemo.model.AdoptionStatus;
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

        var petsInDb = jdbcTemplate.queryForList("SELECT name, status, species FROM pet");
        assertThat(petsInDb)
                .containsExactlyInAnyOrder(
                        petMap("Isaac Mewton", AdoptionStatus.NEW, "cat"),
                        petMap("Chris P. Bacon", AdoptionStatus.NEW, "pig"),
                        petMap("Frodog", AdoptionStatus.WAITING, "dog"),
                        petMap("Meowy Poppins", AdoptionStatus.AVAILABLE, "cat")
                );
    }

    private static Map<String, Object> petMap(String name, AdoptionStatus status, String species) {
        return Map.of("NAME", name, "STATUS", status.toString(), "SPECIES", species);
    }
}
