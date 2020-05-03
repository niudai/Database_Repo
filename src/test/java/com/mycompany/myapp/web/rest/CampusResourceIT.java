package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;
import com.mycompany.myapp.domain.Campus;
import com.mycompany.myapp.repository.CampusRepository;

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

/**
 * Integration tests for the {@link CampusResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CampusResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private CampusRepository campusRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCampusMockMvc;

    private Campus campus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Campus createEntity(EntityManager em) {
        Campus campus = new Campus()
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS);
        return campus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Campus createUpdatedEntity(EntityManager em) {
        Campus campus = new Campus()
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS);
        return campus;
    }

    @BeforeEach
    public void initTest() {
        campus = createEntity(em);
    }

    @Test
    @Transactional
    public void createCampus() throws Exception {
        int databaseSizeBeforeCreate = campusRepository.findAll().size();

        // Create the Campus
        restCampusMockMvc.perform(post("/api/campuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(campus)))
            .andExpect(status().isCreated());

        // Validate the Campus in the database
        List<Campus> campusList = campusRepository.findAll();
        assertThat(campusList).hasSize(databaseSizeBeforeCreate + 1);
        Campus testCampus = campusList.get(campusList.size() - 1);
        assertThat(testCampus.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCampus.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void createCampusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = campusRepository.findAll().size();

        // Create the Campus with an existing ID
        campus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCampusMockMvc.perform(post("/api/campuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(campus)))
            .andExpect(status().isBadRequest());

        // Validate the Campus in the database
        List<Campus> campusList = campusRepository.findAll();
        assertThat(campusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCampuses() throws Exception {
        // Initialize the database
        campusRepository.saveAndFlush(campus);

        // Get all the campusList
        restCampusMockMvc.perform(get("/api/campuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(campus.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));
    }
    
    @Test
    @Transactional
    public void getCampus() throws Exception {
        // Initialize the database
        campusRepository.saveAndFlush(campus);

        // Get the campus
        restCampusMockMvc.perform(get("/api/campuses/{id}", campus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(campus.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS));
    }

    @Test
    @Transactional
    public void getNonExistingCampus() throws Exception {
        // Get the campus
        restCampusMockMvc.perform(get("/api/campuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCampus() throws Exception {
        // Initialize the database
        campusRepository.saveAndFlush(campus);

        int databaseSizeBeforeUpdate = campusRepository.findAll().size();

        // Update the campus
        Campus updatedCampus = campusRepository.findById(campus.getId()).get();
        // Disconnect from session so that the updates on updatedCampus are not directly saved in db
        em.detach(updatedCampus);
        updatedCampus
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS);

        restCampusMockMvc.perform(put("/api/campuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCampus)))
            .andExpect(status().isOk());

        // Validate the Campus in the database
        List<Campus> campusList = campusRepository.findAll();
        assertThat(campusList).hasSize(databaseSizeBeforeUpdate);
        Campus testCampus = campusList.get(campusList.size() - 1);
        assertThat(testCampus.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCampus.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingCampus() throws Exception {
        int databaseSizeBeforeUpdate = campusRepository.findAll().size();

        // Create the Campus

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCampusMockMvc.perform(put("/api/campuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(campus)))
            .andExpect(status().isBadRequest());

        // Validate the Campus in the database
        List<Campus> campusList = campusRepository.findAll();
        assertThat(campusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCampus() throws Exception {
        // Initialize the database
        campusRepository.saveAndFlush(campus);

        int databaseSizeBeforeDelete = campusRepository.findAll().size();

        // Delete the campus
        restCampusMockMvc.perform(delete("/api/campuses/{id}", campus.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Campus> campusList = campusRepository.findAll();
        assertThat(campusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
