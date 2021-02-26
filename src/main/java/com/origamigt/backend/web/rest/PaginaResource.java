package com.origamigt.backend.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.origamigt.backend.service.PaginaService;
import com.origamigt.backend.web.rest.errors.BadRequestAlertException;
import com.origamigt.backend.service.dto.PaginaDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.origamigt.backend.domain.Pagina}.
 */
@RestController
@RequestMapping("/api")
public class PaginaResource {

    private final Logger log = LoggerFactory.getLogger(PaginaResource.class);

    private static final String ENTITY_NAME = "pagina";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaginaService paginaService;

    public PaginaResource(PaginaService paginaService) {
        this.paginaService = paginaService;
    }

    /**
     * {@code POST  /paginas} : Create a new pagina.
     *
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paginaDTO, or with status {@code 400 (Bad Request)} if the pagina has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/paginas")
    public ResponseEntity<PaginaDTO> createPagina(@RequestParam("model") String model,
                                                  @RequestParam("file") MultipartFile imagen)
        throws URISyntaxException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        PaginaDTO paginaDTO = mapper.readValue(model, PaginaDTO.class);
        log.debug("REST request to save Pagina : {}", paginaDTO);
        if (paginaDTO.getId() != null) {
            throw new BadRequestAlertException("A new pagina cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (!imagen.isEmpty()) {
            Path directorioImagenes = Paths.get("src//main//resources/images");
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
            try {
                byte[] bytesImg = imagen.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);
                paginaDTO.setImages(imagen.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        PaginaDTO result = paginaService.save(paginaDTO);
        return ResponseEntity.created(new URI("/api/paginas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /paginas} : Updates an existing pagina.
     *
     * @param paginaDTO the paginaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paginaDTO,
     * or with status {@code 400 (Bad Request)} if the paginaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paginaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/paginas")
    public ResponseEntity<PaginaDTO> updatePagina(@Valid @RequestBody PaginaDTO paginaDTO) throws URISyntaxException {
        log.debug("REST request to update Pagina : {}", paginaDTO);
        if (paginaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaginaDTO result = paginaService.save(paginaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paginaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /paginas} : get all the paginas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paginas in body.
     */
    @GetMapping("/paginas")
    public List<PaginaDTO> getAllPaginas() {
        log.debug("REST request to get all Paginas");
        return paginaService.findAll();
    }

    /**
     * {@code GET  /paginas/:id} : get the "id" pagina.
     *
     * @param id the id of the paginaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paginaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/paginas/{id}")
    public ResponseEntity<PaginaDTO> getPagina(@PathVariable Long id) {
        log.debug("REST request to get Pagina : {}", id);
        Optional<PaginaDTO> paginaDTO = paginaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paginaDTO);
    }

    /**
     * {@code DELETE  /paginas/:id} : delete the "id" pagina.
     *
     * @param id the id of the paginaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/paginas/{id}")
    public ResponseEntity<Void> deletePagina(@PathVariable Long id) {
        log.debug("REST request to delete Pagina : {}", id);
        paginaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
