package com.origamigt.backend.service.mapper;


import com.origamigt.backend.domain.*;
import com.origamigt.backend.service.dto.PaginaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pagina} and its DTO {@link PaginaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PaginaMapper extends EntityMapper<PaginaDTO, Pagina> {



    default Pagina fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pagina pagina = new Pagina();
        pagina.setId(id);
        return pagina;
    }
}
