package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;
import com.mycompany.myapp.domain.Semaster;
import com.mycompany.myapp.repository.SemasterRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.Season;
/**
 * Integration tests for the {@link SemasterResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class SemasterResourceIT {

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final Season DEFAULT_SEASON = Season.Spring;
    private static final Season UPDATED_SEASON = Season.Summer;

    @Autowired
    private SemasterRepository semasterRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSemasterMockMvc;

    private Semaster semaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Semaster createEntity(EntityManager em) {
        Semaster semaster = new Semaster()
            .year(DEFAULT_YEAR)
            .season(DEFAULT_SEASON);
        return semaster;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Semaster createUpdatedEntity(EntityManager em) {
        Semaster semaster = new Semaster()
            .year(UPDATED_YEAR)
            .season(UPDATED_SEASON);
        return semaster;
    }

    @BeforeEach
    public void initTest() {
        semaster = createEntity(em);
    }

    @Test
    @Transactional
    public void createSemaster() throws Exception {
        int databaseSizeBeforeCreate = semasterRepository.findAll().size();

        // Create the Semaster
        restSemasterMockMvc.perform(post("/api/semasters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(semaster)))
            .andExpect(status().isCreated());

        // Validate the Semaster in the database
        List<Semaster> semasterList = semasterRepository.findAll();
        assertThat(semasterList).hasSize(databaseSizeBeforeCreate + 1);
        Semaster testSemaster = semasterList.get(semasterList.size() - 1);
        assertThat(testSemaster.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testSemaster.getSeason()).isEqualTo(DEFAULT_SEASON);
    }

    @Test
    @Transactional
    public void createSemasterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = semasterRepository.findAll().size();

        // Create the Semaster with an existing ID
        semaster.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSemasterMockMvc.perform(post("/api/semasters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(semaster)))
            .andExpect(status().isBadRequest());

        // Validate the Semaster in the database
        List<Semaster> semasterList = semasterRepository.findAll();
        assertThat(semasterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSemasters() throws Exception {
        // Initialize the database
        semasterRepository.saveAndFlush(semaster);

        // Get all the semasterList
        restSemasterMockMvc.perform(get("/api/semasters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(semaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].season").value(hasItem(DEFAULT_SEASON.toString())));
    }
    
    @Test
    @Transactional
    public void getSemaster() throws Exception {
        // Initialize the database
        semasterRepository.saveAndFlush(semaster);

        // Get the semaster
        restSemasterMockMvc.perform(get("/api/semasters/{id}", semaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(semaster.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.season").value(DEFAULT_SEASON.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSemaster() throws Exception {
        // Get the semaster
        restSemasterMockMvc.perform(get("/api/semasters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSemaster() throws Exception {
        // Initialize the database
        semasterRepository.saveAndFlush(semaster);

        int databaseSizeBeforeUpdate = semasterRepository.findAll().size();

        // Update the semaster
        Semaster updatedSemaster = semasterRepository.findById(semaster.getId()).get();
        // Disconnect from session so that the updates on updatedSemaster are not directly saved in db
        em.detach(updatedSemaster);
        updatedSemaster
            .year(UPDATED_YEAR)
            .season(UPDATED_SEASON);

        restSemasterMockMvc.perform(put("/api/semasters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSemaster)))
            .andExpect(status().isOk());

        // Validate the Semaster in the database
        List<Semaster> semasterList = semasterRepository.findAll();
        assertThat(semasterList).hasSize(databaseSizeBeforeUpdate);
        Semaster testSemaster = semasterList.get(semasterList.size() - 1);
        assertThat(testSemaster.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testSemaster.getSeason()).isEqualTo(UPDATED_SEASON);
    }

    @Test
    @Transactional
    public void updateNonExistingSemaster() throws Exception {
        int databaseSizeBeforeUpdate = semasterRepository.findAll().size();

        // Create the Semaster

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSemasterMockMvc.perform(put("/api/semasters")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(semaster)))
            .andExpect(status().isBadRequest());

        // Validate the Semaster in the database
        List<Semaster> semasterList = semasterRepository.findAll();
        assertThat(semasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSemaster() throws Exception {
        // Initialize the database
        semasterRepository.saveAndFlush(semaster);

        int databaseSizeBeforeDelete = semasterRepository.findAll().size();

        // Delete the semaster
        restSemasterMockMvc.perform(delete("/api/semasters/{id}", semaster.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Semaster> semasterList = semasterRepository.findAll();
        assertThat(semasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
