package com.origamigt.backend.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.origamigt.backend.web.rest.TestUtil;

public class PaginaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaginaDTO.class);
        PaginaDTO paginaDTO1 = new PaginaDTO();
        paginaDTO1.setId(1L);
        PaginaDTO paginaDTO2 = new PaginaDTO();
        assertThat(paginaDTO1).isNotEqualTo(paginaDTO2);
        paginaDTO2.setId(paginaDTO1.getId());
        assertThat(paginaDTO1).isEqualTo(paginaDTO2);
        paginaDTO2.setId(2L);
        assertThat(paginaDTO1).isNotEqualTo(paginaDTO2);
        paginaDTO1.setId(null);
        assertThat(paginaDTO1).isNotEqualTo(paginaDTO2);
    }
}
