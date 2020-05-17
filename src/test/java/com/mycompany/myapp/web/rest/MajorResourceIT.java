package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;
import com.mycompany.myapp.domain.Major;
import com.mycompany.myapp.repository.MajorRepository;
import com.mycompany.myapp.repository.search.MajorSearchRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MajorResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class MajorResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private MajorRepository majorRepository;

    /**
     * This repository is mocked in the com.mycompany.myapp.repository.search test package.
     *
     * @see com.mycompany.myapp.repository.search.MajorSearchRepositoryMockConfiguration
     */
    @Autowired
    private MajorSearchRepository mockMajorSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMajorMockMvc;

    private Major major;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Major createEntity(EntityManager em) {
        Major major = new Major()
            .name(DEFAULT_NAME);
        return major;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Major createUpdatedEntity(EntityManager em) {
        Major major = new Major()
            .name(UPDATED_NAME);
        return major;
    }

    @BeforeEach
    public void initTest() {
        major = createEntity(em);
    }

    @Test
    @Transactional
    public void createMajor() throws Exception {
        int databaseSizeBeforeCreate = majorRepository.findAll().size();

        // Create the Major
        restMajorMockMvc.perform(post("/api/majors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(major)))
            .andExpect(status().isCreated());

        // Validate the Major in the database
        List<Major> majorList = majorRepository.findAll();
        assertThat(majorList).hasSize(databaseSizeBeforeCreate + 1);
        Major testMajor = majorList.get(majorList.size() - 1);
        assertThat(testMajor.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the Major in Elasticsearch
        verify(mockMajorSearchRepository, times(1)).save(testMajor);
    }

    @Test
    @Transactional
    public void createMajorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = majorRepository.findAll().size();

        // Create the Major with an existing ID
        major.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMajorMockMvc.perform(post("/api/majors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(major)))
            .andExpect(status().isBadRequest());

        // Validate the Major in the database
        List<Major> majorList = majorRepository.findAll();
        assertThat(majorList).hasSize(databaseSizeBeforeCreate);

        // Validate the Major in Elasticsearch
        verify(mockMajorSearchRepository, times(0)).save(major);
    }


    @Test
    @Transactional
    public void getAllMajors() throws Exception {
        // Initialize the database
        majorRepository.saveAndFlush(major);

        // Get all the majorList
        restMajorMockMvc.perform(get("/api/majors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(major.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getMajor() throws Exception {
        // Initialize the database
        majorRepository.saveAndFlush(major);

        // Get the major
        restMajorMockMvc.perform(get("/api/majors/{id}", major.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(major.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingMajor() throws Exception {
        // Get the major
        restMajorMockMvc.perform(get("/api/majors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMajor() throws Exception {
        // Initialize the database
        majorRepository.saveAndFlush(major);

        int databaseSizeBeforeUpdate = majorRepository.findAll().size();

        // Update the major
        Major updatedMajor = majorRepository.findById(major.getId()).get();
        // Disconnect from session so that the updates on updatedMajor are not directly saved in db
        em.detach(updatedMajor);
        updatedMajor
            .name(UPDATED_NAME);

        restMajorMockMvc.perform(put("/api/majors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMajor)))
            .andExpect(status().isOk());

        // Validate the Major in the database
        List<Major> majorList = majorRepository.findAll();
        assertThat(majorList).hasSize(databaseSizeBeforeUpdate);
        Major testMajor = majorList.get(majorList.size() - 1);
        assertThat(testMajor.getName()).isEqualTo(UPDATED_NAME);

        // Validate the Major in Elasticsearch
        verify(mockMajorSearchRepository, times(1)).save(testMajor);
    }

    @Test
    @Transactional
    public void updateNonExistingMajor() throws Exception {
        int databaseSizeBeforeUpdate = majorRepository.findAll().size();

        // Create the Major

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMajorMockMvc.perform(put("/api/majors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(major)))
            .andExpect(status().isBadRequest());

        // Validate the Major in the database
        List<Major> majorList = majorRepository.findAll();
        assertThat(majorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Major in Elasticsearch
        verify(mockMajorSearchRepository, times(0)).save(major);
    }

    @Test
    @Transactional
    public void deleteMajor() throws Exception {
        // Initialize the database
        majorRepository.saveAndFlush(major);

        int databaseSizeBeforeDelete = majorRepository.findAll().size();

        // Delete the major
        restMajorMockMvc.perform(delete("/api/majors/{id}", major.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Major> majorList = majorRepository.findAll();
        assertThat(majorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Major in Elasticsearch
        verify(mockMajorSearchRepository, times(1)).deleteById(major.getId());
    }

    @Test
    @Transactional
    public void searchMajor() throws Exception {
        // Initialize the database
        majorRepository.saveAndFlush(major);
        when(mockMajorSearchRepository.search(queryStringQuery("id:" + major.getId())))
            .thenReturn(Collections.singletonList(major));
        // Search the major
        restMajorMockMvc.perform(get("/api/_search/majors?query=id:" + major.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(major.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
}
