package com.origamigt.backend.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.origamigt.backend.web.rest.TestUtil;

public class PaginaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pagina.class);
        Pagina pagina1 = new Pagina();
        pagina1.setId(1L);
        Pagina pagina2 = new Pagina();
        pagina2.setId(pagina1.getId());
        assertThat(pagina1).isEqualTo(pagina2);
        pagina2.setId(2L);
        assertThat(pagina1).isNotEqualTo(pagina2);
        pagina1.setId(null);
        assertThat(pagina1).isNotEqualTo(pagina2);
    }
}
