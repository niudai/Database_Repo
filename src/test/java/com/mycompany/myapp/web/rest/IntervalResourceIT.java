package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;
import com.mycompany.myapp.domain.Interval;
import com.mycompany.myapp.repository.IntervalRepository;
import com.mycompany.myapp.repository.search.IntervalSearchRepository;

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

import com.mycompany.myapp.domain.enumeration.WeekDay;
/**
 * Integration tests for the {@link IntervalResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class IntervalResourceIT {

    private static final WeekDay DEFAULT_DAY = WeekDay.Mon;
    private static final WeekDay UPDATED_DAY = WeekDay.Tue;

    private static final Integer DEFAULT_START = 1;
    private static final Integer UPDATED_START = 2;

    private static final Integer DEFAULT_END = 1;
    private static final Integer UPDATED_END = 2;

    @Autowired
    private IntervalRepository intervalRepository;

    /**
     * This repository is mocked in the com.mycompany.myapp.repository.search test package.
     *
     * @see com.mycompany.myapp.repository.search.IntervalSearchRepositoryMockConfiguration
     */
    @Autowired
    private IntervalSearchRepository mockIntervalSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIntervalMockMvc;

    private Interval interval;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Interval createEntity(EntityManager em) {
        Interval interval = new Interval()
            .day(DEFAULT_DAY)
            .start(DEFAULT_START)
            .end(DEFAULT_END);
        return interval;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Interval createUpdatedEntity(EntityManager em) {
        Interval interval = new Interval()
            .day(UPDATED_DAY)
            .start(UPDATED_START)
            .end(UPDATED_END);
        return interval;
    }

    @BeforeEach
    public void initTest() {
        interval = createEntity(em);
    }

    @Test
    @Transactional
    public void createInterval() throws Exception {
        int databaseSizeBeforeCreate = intervalRepository.findAll().size();
        // Create the Interval
        restIntervalMockMvc.perform(post("/api/intervals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(interval)))
            .andExpect(status().isCreated());

        // Validate the Interval in the database
        List<Interval> intervalList = intervalRepository.findAll();
        assertThat(intervalList).hasSize(databaseSizeBeforeCreate + 1);
        Interval testInterval = intervalList.get(intervalList.size() - 1);
        assertThat(testInterval.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testInterval.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testInterval.getEnd()).isEqualTo(DEFAULT_END);

        // Validate the Interval in Elasticsearch
        verify(mockIntervalSearchRepository, times(1)).save(testInterval);
    }

    @Test
    @Transactional
    public void createIntervalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = intervalRepository.findAll().size();

        // Create the Interval with an existing ID
        interval.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIntervalMockMvc.perform(post("/api/intervals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(interval)))
            .andExpect(status().isBadRequest());

        // Validate the Interval in the database
        List<Interval> intervalList = intervalRepository.findAll();
        assertThat(intervalList).hasSize(databaseSizeBeforeCreate);

        // Validate the Interval in Elasticsearch
        verify(mockIntervalSearchRepository, times(0)).save(interval);
    }


    @Test
    @Transactional
    public void getAllIntervals() throws Exception {
        // Initialize the database
        intervalRepository.saveAndFlush(interval);

        // Get all the intervalList
        restIntervalMockMvc.perform(get("/api/intervals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(interval.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY.toString())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START)))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END)));
    }
    
    @Test
    @Transactional
    public void getInterval() throws Exception {
        // Initialize the database
        intervalRepository.saveAndFlush(interval);

        // Get the interval
        restIntervalMockMvc.perform(get("/api/intervals/{id}", interval.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(interval.getId().intValue()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY.toString()))
            .andExpect(jsonPath("$.start").value(DEFAULT_START))
            .andExpect(jsonPath("$.end").value(DEFAULT_END));
    }
    @Test
    @Transactional
    public void getNonExistingInterval() throws Exception {
        // Get the interval
        restIntervalMockMvc.perform(get("/api/intervals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInterval() throws Exception {
        // Initialize the database
        intervalRepository.saveAndFlush(interval);

        int databaseSizeBeforeUpdate = intervalRepository.findAll().size();

        // Update the interval
        Interval updatedInterval = intervalRepository.findById(interval.getId()).get();
        // Disconnect from session so that the updates on updatedInterval are not directly saved in db
        em.detach(updatedInterval);
        updatedInterval
            .day(UPDATED_DAY)
            .start(UPDATED_START)
            .end(UPDATED_END);

        restIntervalMockMvc.perform(put("/api/intervals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInterval)))
            .andExpect(status().isOk());

        // Validate the Interval in the database
        List<Interval> intervalList = intervalRepository.findAll();
        assertThat(intervalList).hasSize(databaseSizeBeforeUpdate);
        Interval testInterval = intervalList.get(intervalList.size() - 1);
        assertThat(testInterval.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testInterval.getStart()).isEqualTo(UPDATED_START);
        assertThat(testInterval.getEnd()).isEqualTo(UPDATED_END);

        // Validate the Interval in Elasticsearch
        verify(mockIntervalSearchRepository, times(1)).save(testInterval);
    }

    @Test
    @Transactional
    public void updateNonExistingInterval() throws Exception {
        int databaseSizeBeforeUpdate = intervalRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntervalMockMvc.perform(put("/api/intervals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(interval)))
            .andExpect(status().isBadRequest());

        // Validate the Interval in the database
        List<Interval> intervalList = intervalRepository.findAll();
        assertThat(intervalList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Interval in Elasticsearch
        verify(mockIntervalSearchRepository, times(0)).save(interval);
    }

    @Test
    @Transactional
    public void deleteInterval() throws Exception {
        // Initialize the database
        intervalRepository.saveAndFlush(interval);

        int databaseSizeBeforeDelete = intervalRepository.findAll().size();

        // Delete the interval
        restIntervalMockMvc.perform(delete("/api/intervals/{id}", interval.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Interval> intervalList = intervalRepository.findAll();
        assertThat(intervalList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Interval in Elasticsearch
        verify(mockIntervalSearchRepository, times(1)).deleteById(interval.getId());
    }

    @Test
    @Transactional
    public void searchInterval() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        intervalRepository.saveAndFlush(interval);
        when(mockIntervalSearchRepository.search(queryStringQuery("id:" + interval.getId())))
            .thenReturn(Collections.singletonList(interval));

        // Search the interval
        restIntervalMockMvc.perform(get("/api/_search/intervals?query=id:" + interval.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(interval.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY.toString())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START)))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END)));
    }
}
