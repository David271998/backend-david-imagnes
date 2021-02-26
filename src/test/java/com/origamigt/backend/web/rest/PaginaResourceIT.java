package com.origamigt.backend.web.rest;

import com.origamigt.backend.BackendApp;
import com.origamigt.backend.domain.Pagina;
import com.origamigt.backend.repository.PaginaRepository;
import com.origamigt.backend.service.PaginaService;
import com.origamigt.backend.service.dto.PaginaDTO;
import com.origamigt.backend.service.mapper.PaginaMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PaginaResource} REST controller.
 */
@SpringBootTest(classes = BackendApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PaginaResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_SUB_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDEN = 1;
    private static final Integer UPDATED_ORDEN = 2;

    private static final LocalDate DEFAULT_FECHA_PUBLICACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_PUBLICACION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_EXPIRACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_EXPIRACION = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ACTIVA = false;
    private static final Boolean UPDATED_ACTIVA = true;

    private static final String DEFAULT_ETIQUETA = "AAAAAAAAAA";
    private static final String UPDATED_ETIQUETA = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGES = "AAAAAAAAAA";
    private static final String UPDATED_IMAGES = "BBBBBBBBBB";

    @Autowired
    private PaginaRepository paginaRepository;

    @Autowired
    private PaginaMapper paginaMapper;

    @Autowired
    private PaginaService paginaService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaginaMockMvc;

    private Pagina pagina;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pagina createEntity(EntityManager em) {
        Pagina pagina = new Pagina()
            .titulo(DEFAULT_TITULO)
            .subTitulo(DEFAULT_SUB_TITULO)
            .descripcion(DEFAULT_DESCRIPCION)
            .orden(DEFAULT_ORDEN)
            .fechaPublicacion(DEFAULT_FECHA_PUBLICACION)
            .fechaExpiracion(DEFAULT_FECHA_EXPIRACION)
            .activa(DEFAULT_ACTIVA)
            .etiqueta(DEFAULT_ETIQUETA)
            .images(DEFAULT_IMAGES);
        return pagina;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pagina createUpdatedEntity(EntityManager em) {
        Pagina pagina = new Pagina()
            .titulo(UPDATED_TITULO)
            .subTitulo(UPDATED_SUB_TITULO)
            .descripcion(UPDATED_DESCRIPCION)
            .orden(UPDATED_ORDEN)
            .fechaPublicacion(UPDATED_FECHA_PUBLICACION)
            .fechaExpiracion(UPDATED_FECHA_EXPIRACION)
            .activa(UPDATED_ACTIVA)
            .etiqueta(UPDATED_ETIQUETA)
            .images(UPDATED_IMAGES);
        return pagina;
    }

    @BeforeEach
    public void initTest() {
        pagina = createEntity(em);
    }

    @Test
    @Transactional
    public void createPagina() throws Exception {
        int databaseSizeBeforeCreate = paginaRepository.findAll().size();
        // Create the Pagina
        PaginaDTO paginaDTO = paginaMapper.toDto(pagina);
        restPaginaMockMvc.perform(post("/api/paginas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paginaDTO)))
            .andExpect(status().isCreated());

        // Validate the Pagina in the database
        List<Pagina> paginaList = paginaRepository.findAll();
        assertThat(paginaList).hasSize(databaseSizeBeforeCreate + 1);
        Pagina testPagina = paginaList.get(paginaList.size() - 1);
        assertThat(testPagina.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testPagina.getSubTitulo()).isEqualTo(DEFAULT_SUB_TITULO);
        assertThat(testPagina.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testPagina.getOrden()).isEqualTo(DEFAULT_ORDEN);
        assertThat(testPagina.getFechaPublicacion()).isEqualTo(DEFAULT_FECHA_PUBLICACION);
        assertThat(testPagina.getFechaExpiracion()).isEqualTo(DEFAULT_FECHA_EXPIRACION);
        assertThat(testPagina.isActiva()).isEqualTo(DEFAULT_ACTIVA);
        assertThat(testPagina.getEtiqueta()).isEqualTo(DEFAULT_ETIQUETA);
        assertThat(testPagina.getImages()).isEqualTo(DEFAULT_IMAGES);
    }

    @Test
    @Transactional
    public void createPaginaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paginaRepository.findAll().size();

        // Create the Pagina with an existing ID
        pagina.setId(1L);
        PaginaDTO paginaDTO = paginaMapper.toDto(pagina);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaginaMockMvc.perform(post("/api/paginas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paginaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pagina in the database
        List<Pagina> paginaList = paginaRepository.findAll();
        assertThat(paginaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = paginaRepository.findAll().size();
        // set the field null
        pagina.setTitulo(null);

        // Create the Pagina, which fails.
        PaginaDTO paginaDTO = paginaMapper.toDto(pagina);


        restPaginaMockMvc.perform(post("/api/paginas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paginaDTO)))
            .andExpect(status().isBadRequest());

        List<Pagina> paginaList = paginaRepository.findAll();
        assertThat(paginaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrdenIsRequired() throws Exception {
        int databaseSizeBeforeTest = paginaRepository.findAll().size();
        // set the field null
        pagina.setOrden(null);

        // Create the Pagina, which fails.
        PaginaDTO paginaDTO = paginaMapper.toDto(pagina);


        restPaginaMockMvc.perform(post("/api/paginas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paginaDTO)))
            .andExpect(status().isBadRequest());

        List<Pagina> paginaList = paginaRepository.findAll();
        assertThat(paginaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActivaIsRequired() throws Exception {
        int databaseSizeBeforeTest = paginaRepository.findAll().size();
        // set the field null
        pagina.setActiva(null);

        // Create the Pagina, which fails.
        PaginaDTO paginaDTO = paginaMapper.toDto(pagina);


        restPaginaMockMvc.perform(post("/api/paginas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paginaDTO)))
            .andExpect(status().isBadRequest());

        List<Pagina> paginaList = paginaRepository.findAll();
        assertThat(paginaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEtiquetaIsRequired() throws Exception {
        int databaseSizeBeforeTest = paginaRepository.findAll().size();
        // set the field null
        pagina.setEtiqueta(null);

        // Create the Pagina, which fails.
        PaginaDTO paginaDTO = paginaMapper.toDto(pagina);


        restPaginaMockMvc.perform(post("/api/paginas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paginaDTO)))
            .andExpect(status().isBadRequest());

        List<Pagina> paginaList = paginaRepository.findAll();
        assertThat(paginaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPaginas() throws Exception {
        // Initialize the database
        paginaRepository.saveAndFlush(pagina);

        // Get all the paginaList
        restPaginaMockMvc.perform(get("/api/paginas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pagina.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].subTitulo").value(hasItem(DEFAULT_SUB_TITULO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].orden").value(hasItem(DEFAULT_ORDEN)))
            .andExpect(jsonPath("$.[*].fechaPublicacion").value(hasItem(DEFAULT_FECHA_PUBLICACION.toString())))
            .andExpect(jsonPath("$.[*].fechaExpiracion").value(hasItem(DEFAULT_FECHA_EXPIRACION.toString())))
            .andExpect(jsonPath("$.[*].activa").value(hasItem(DEFAULT_ACTIVA.booleanValue())))
            .andExpect(jsonPath("$.[*].etiqueta").value(hasItem(DEFAULT_ETIQUETA)))
            .andExpect(jsonPath("$.[*].images").value(hasItem(DEFAULT_IMAGES)));
    }
    
    @Test
    @Transactional
    public void getPagina() throws Exception {
        // Initialize the database
        paginaRepository.saveAndFlush(pagina);

        // Get the pagina
        restPaginaMockMvc.perform(get("/api/paginas/{id}", pagina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pagina.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.subTitulo").value(DEFAULT_SUB_TITULO))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.orden").value(DEFAULT_ORDEN))
            .andExpect(jsonPath("$.fechaPublicacion").value(DEFAULT_FECHA_PUBLICACION.toString()))
            .andExpect(jsonPath("$.fechaExpiracion").value(DEFAULT_FECHA_EXPIRACION.toString()))
            .andExpect(jsonPath("$.activa").value(DEFAULT_ACTIVA.booleanValue()))
            .andExpect(jsonPath("$.etiqueta").value(DEFAULT_ETIQUETA))
            .andExpect(jsonPath("$.images").value(DEFAULT_IMAGES));
    }
    @Test
    @Transactional
    public void getNonExistingPagina() throws Exception {
        // Get the pagina
        restPaginaMockMvc.perform(get("/api/paginas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePagina() throws Exception {
        // Initialize the database
        paginaRepository.saveAndFlush(pagina);

        int databaseSizeBeforeUpdate = paginaRepository.findAll().size();

        // Update the pagina
        Pagina updatedPagina = paginaRepository.findById(pagina.getId()).get();
        // Disconnect from session so that the updates on updatedPagina are not directly saved in db
        em.detach(updatedPagina);
        updatedPagina
            .titulo(UPDATED_TITULO)
            .subTitulo(UPDATED_SUB_TITULO)
            .descripcion(UPDATED_DESCRIPCION)
            .orden(UPDATED_ORDEN)
            .fechaPublicacion(UPDATED_FECHA_PUBLICACION)
            .fechaExpiracion(UPDATED_FECHA_EXPIRACION)
            .activa(UPDATED_ACTIVA)
            .etiqueta(UPDATED_ETIQUETA)
            .images(UPDATED_IMAGES);
        PaginaDTO paginaDTO = paginaMapper.toDto(updatedPagina);

        restPaginaMockMvc.perform(put("/api/paginas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paginaDTO)))
            .andExpect(status().isOk());

        // Validate the Pagina in the database
        List<Pagina> paginaList = paginaRepository.findAll();
        assertThat(paginaList).hasSize(databaseSizeBeforeUpdate);
        Pagina testPagina = paginaList.get(paginaList.size() - 1);
        assertThat(testPagina.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testPagina.getSubTitulo()).isEqualTo(UPDATED_SUB_TITULO);
        assertThat(testPagina.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testPagina.getOrden()).isEqualTo(UPDATED_ORDEN);
        assertThat(testPagina.getFechaPublicacion()).isEqualTo(UPDATED_FECHA_PUBLICACION);
        assertThat(testPagina.getFechaExpiracion()).isEqualTo(UPDATED_FECHA_EXPIRACION);
        assertThat(testPagina.isActiva()).isEqualTo(UPDATED_ACTIVA);
        assertThat(testPagina.getEtiqueta()).isEqualTo(UPDATED_ETIQUETA);
        assertThat(testPagina.getImages()).isEqualTo(UPDATED_IMAGES);
    }

    @Test
    @Transactional
    public void updateNonExistingPagina() throws Exception {
        int databaseSizeBeforeUpdate = paginaRepository.findAll().size();

        // Create the Pagina
        PaginaDTO paginaDTO = paginaMapper.toDto(pagina);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaginaMockMvc.perform(put("/api/paginas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paginaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pagina in the database
        List<Pagina> paginaList = paginaRepository.findAll();
        assertThat(paginaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePagina() throws Exception {
        // Initialize the database
        paginaRepository.saveAndFlush(pagina);

        int databaseSizeBeforeDelete = paginaRepository.findAll().size();

        // Delete the pagina
        restPaginaMockMvc.perform(delete("/api/paginas/{id}", pagina.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pagina> paginaList = paginaRepository.findAll();
        assertThat(paginaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
