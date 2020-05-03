package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;
import com.mycompany.myapp.domain.Interval;
import com.mycompany.myapp.repository.IntervalRepository;

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

import com.mycompany.myapp.domain.enumeration.WeekDay;
/**
 * Integration tests for the {@link IntervalResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)

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
    }

    @Test
    @Transactional
    public void updateNonExistingInterval() throws Exception {
        int databaseSizeBeforeUpdate = intervalRepository.findAll().size();

        // Create the Interval

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntervalMockMvc.perform(put("/api/intervals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(interval)))
            .andExpect(status().isBadRequest());

        // Validate the Interval in the database
        List<Interval> intervalList = intervalRepository.findAll();
        assertThat(intervalList).hasSize(databaseSizeBeforeUpdate);
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
    }
}
