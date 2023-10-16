package com.ekino.petclassifierdemo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.ekino.petclassifierdemo.model.AdoptionStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class PetDbRowMapperTest {

    @Mock
    private ResultSet resultSet;
    @InjectMocks
    private PetDbRowMapper petDbRowMapper;

    @Test
    void should_map_vehicle_row() throws SQLException {
        //given
        given(resultSet.getString("id")).willReturn("9ad4fd7d-018f-4e8b-a717-7dd084406b6a");
        given(resultSet.getString("name")).willReturn("toto");
        given(resultSet.getString("status")).willReturn("NEW");
        given(resultSet.getString("species")).willReturn("cat");

        //when
        var result = petDbRowMapper.mapRow(resultSet, 1);

        //then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(UUID.fromString("9ad4fd7d-018f-4e8b-a717-7dd084406b6a"));
        assertThat(result.name()).isEqualTo("toto");
        assertThat(result.status()).isEqualTo(AdoptionStatus.NEW);
        assertThat(result.species()).isEqualTo("cat");
    }

    @Test
    void should_throw_exception_for_null_field() throws SQLException {
        //given
        given(resultSet.getString("id")).willReturn(null);

        //when/then
        assertThatThrownBy(() -> petDbRowMapper.mapRow(resultSet, 1))
                .isInstanceOf(SQLException.class)
                .hasMessage("id should not be null");
    }
}
