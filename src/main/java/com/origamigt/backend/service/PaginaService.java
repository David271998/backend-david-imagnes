package com.origamigt.backend.service;

import com.origamigt.backend.domain.Pagina;
import com.origamigt.backend.repository.PaginaRepository;
import com.origamigt.backend.service.dto.PaginaDTO;
import com.origamigt.backend.service.mapper.PaginaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Pagina}.
 */
@Service
@Transactional
public class PaginaService {

    private final Logger log = LoggerFactory.getLogger(PaginaService.class);

    private final PaginaRepository paginaRepository;

    private final PaginaMapper paginaMapper;

    public PaginaService(PaginaRepository paginaRepository, PaginaMapper paginaMapper) {
        this.paginaRepository = paginaRepository;
        this.paginaMapper = paginaMapper;
    }

    /**
     * Save a pagina.
     *
     * @param paginaDTO the entity to save.
     * @return the persisted entity.
     */
    public PaginaDTO save(PaginaDTO paginaDTO) {
        log.debug("Request to save Pagina : {}", paginaDTO);
        Pagina pagina = paginaMapper.toEntity(paginaDTO);
        pagina = paginaRepository.save(pagina);
        return paginaMapper.toDto(pagina);
    }
    public void saveImage()
    {

    }
    /**
     * Get all the paginas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PaginaDTO> findAll() {
        log.debug("Request to get all Paginas");
        return paginaRepository.findAll().stream()
            .map(paginaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one pagina by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PaginaDTO> findOne(Long id) {
        log.debug("Request to get Pagina : {}", id);
        return paginaRepository.findById(id)
            .map(paginaMapper::toDto);
    }

    /**
     * Delete the pagina by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Pagina : {}", id);
        paginaRepository.deleteById(id);
    }
}
