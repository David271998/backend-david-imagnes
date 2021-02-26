package com.origamigt.backend.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PaginaMapperTest {

    private PaginaMapper paginaMapper;

    @BeforeEach
    public void setUp() {
        paginaMapper = new PaginaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(paginaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(paginaMapper.fromId(null)).isNull();
    }
}
