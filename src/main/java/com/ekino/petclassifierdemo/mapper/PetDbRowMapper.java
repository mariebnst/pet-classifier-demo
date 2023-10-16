package com.ekino.petclassifierdemo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.ekino.petclassifierdemo.model.AdoptionStatus;
import com.ekino.petclassifierdemo.model.PetDbEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class PetDbRowMapper implements RowMapper<PetDbEntity> {

    @Override
    public PetDbEntity mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {

        var id = UUID.fromString(mapStringColumn(rs, "id"));
        var name = mapStringColumn(rs, "name");
        var status = AdoptionStatus.valueOf(mapStringColumn(rs, "status"));
        var species = mapStringColumn(rs, "species");

        return new PetDbEntity(id, name, status, species);
    }

    private static String mapStringColumn(ResultSet rs, String columnName) throws SQLException {
        var valueAsString = rs.getString(columnName);
        if (valueAsString == null) {
            throw new SQLException(columnName + " should not be null");
        }
        return valueAsString;
    }
}
