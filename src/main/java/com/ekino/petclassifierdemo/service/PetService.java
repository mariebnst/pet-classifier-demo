package com.ekino.petclassifierdemo.service;

import java.util.Map;
import java.util.UUID;

import com.ekino.petclassifierdemo.mapper.PetDbRowMapper;
import com.ekino.petclassifierdemo.model.PetDbEntity;
import com.ekino.petclassifierdemo.model.PetToCreate;
import com.ekino.petclassifierdemo.model.PetToDelete;
import com.ekino.petclassifierdemo.model.PetToUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import static org.springframework.util.CollectionUtils.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetService {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final PetDbRowMapper petDbRowMapper;

    public PetDbEntity getPetByName(String name) {
        var selectPetQuery = """
            SELECT id, name, status, species
            FROM pet
            WHERE name = :name
            """;
        var foundPets = namedParameterJdbcTemplate.query(selectPetQuery, Map.of("name", name), petDbRowMapper);
        if (isEmpty(foundPets)) {
            throw new ValidationException("No pet with name: " + name);
        }
        return foundPets.get(0);
    }

    public void deletePet(PetToDelete petToDelete) {
        log.info("Delete pet with id: {}", petToDelete.getId());
        var deletePetQuery = """
            DELETE FROM pet
            WHERE id = :id
            """;
        namedParameterJdbcTemplate.update(deletePetQuery, Map.of("id", petToDelete.getId()));
    }

    public void createPet(PetToCreate petToCreate) {
        log.info("Create pet with name: {}", petToCreate.getName());
        var createPetQuery = """
            INSERT INTO pet(id, name, status, species)
            VALUES (:id, :name, :status, :species)
            """;
        var queryParams = Map.of(
                "id", UUID.randomUUID(),
                "name", petToCreate.getName(),
                "status", petToCreate.getStatus().toString(),
                "species", petToCreate.getSpecies()
        );
        namedParameterJdbcTemplate.update(createPetQuery, queryParams);
    }

    public void updatePet(PetToUpdate petToUpdate) {
        log.info("Update pet with id: {}", petToUpdate.getId());
        var updatePetQuery = """
            UPDATE pet
            SET status = :status
            WHERE id = :id
            """;
        var queryParams = Map.of(
                "id", petToUpdate.getId(),
                "status", petToUpdate.getStatus().toString()
        );
        namedParameterJdbcTemplate.update(updatePetQuery, queryParams);
    }
}
