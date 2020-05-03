package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;
import com.mycompany.myapp.domain.Exception;
import com.mycompany.myapp.repository.ExceptionRepository;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ExceptionResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ExceptionResourceIT {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_YOUTH_LEAGUE = false;
    private static final Boolean UPDATED_IS_YOUTH_LEAGUE = true;

    private static final String DEFAULT_CAUSE = "AAAAAAAAAA";
    private static final String UPDATED_CAUSE = "BBBBBBBBBB";

    @Autowired
    private ExceptionRepository exceptionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExceptionMockMvc;

    private Exception exception;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Exception createEntity(EntityManager em) {
        Exception exception = new Exception()
            .date(DEFAULT_DATE)
            .isYouthLeague(DEFAULT_IS_YOUTH_LEAGUE)
            .cause(DEFAULT_CAUSE);
        return exception;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Exception createUpdatedEntity(EntityManager em) {
        Exception exception = new Exception()
            .date(UPDATED_DATE)
            .isYouthLeague(UPDATED_IS_YOUTH_LEAGUE)
            .cause(UPDATED_CAUSE);
        return exception;
    }

    @BeforeEach
    public void initTest() {
        exception = createEntity(em);
    }

    @Test
    @Transactional
    public void createException() throws Exception {
        int databaseSizeBeforeCreate = exceptionRepository.findAll().size();

        // Create the Exception
        restExceptionMockMvc.perform(post("/api/exceptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(exception)))
            .andExpect(status().isCreated());

        // Validate the Exception in the database
        List<Exception> exceptionList = exceptionRepository.findAll();
        assertThat(exceptionList).hasSize(databaseSizeBeforeCreate + 1);
        Exception testException = exceptionList.get(exceptionList.size() - 1);
        assertThat(testException.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testException.isIsYouthLeague()).isEqualTo(DEFAULT_IS_YOUTH_LEAGUE);
        assertThat(testException.getCause()).isEqualTo(DEFAULT_CAUSE);
    }

    @Test
    @Transactional
    public void createExceptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = exceptionRepository.findAll().size();

        // Create the Exception with an existing ID
        exception.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExceptionMockMvc.perform(post("/api/exceptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(exception)))
            .andExpect(status().isBadRequest());

        // Validate the Exception in the database
        List<Exception> exceptionList = exceptionRepository.findAll();
        assertThat(exceptionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllExceptions() throws Exception {
        // Initialize the database
        exceptionRepository.saveAndFlush(exception);

        // Get all the exceptionList
        restExceptionMockMvc.perform(get("/api/exceptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exception.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].isYouthLeague").value(hasItem(DEFAULT_IS_YOUTH_LEAGUE.booleanValue())))
            .andExpect(jsonPath("$.[*].cause").value(hasItem(DEFAULT_CAUSE)));
    }
    
    @Test
    @Transactional
    public void getException() throws Exception {
        // Initialize the database
        exceptionRepository.saveAndFlush(exception);

        // Get the exception
        restExceptionMockMvc.perform(get("/api/exceptions/{id}", exception.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(exception.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.isYouthLeague").value(DEFAULT_IS_YOUTH_LEAGUE.booleanValue()))
            .andExpect(jsonPath("$.cause").value(DEFAULT_CAUSE));
    }

    @Test
    @Transactional
    public void getNonExistingException() throws Exception {
        // Get the exception
        restExceptionMockMvc.perform(get("/api/exceptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateException() throws Exception {
        // Initialize the database
        exceptionRepository.saveAndFlush(exception);

        int databaseSizeBeforeUpdate = exceptionRepository.findAll().size();

        // Update the exception
        Exception updatedException = exceptionRepository.findById(exception.getId()).get();
        // Disconnect from session so that the updates on updatedException are not directly saved in db
        em.detach(updatedException);
        updatedException
            .date(UPDATED_DATE)
            .isYouthLeague(UPDATED_IS_YOUTH_LEAGUE)
            .cause(UPDATED_CAUSE);

        restExceptionMockMvc.perform(put("/api/exceptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedException)))
            .andExpect(status().isOk());

        // Validate the Exception in the database
        List<Exception> exceptionList = exceptionRepository.findAll();
        assertThat(exceptionList).hasSize(databaseSizeBeforeUpdate);
        Exception testException = exceptionList.get(exceptionList.size() - 1);
        assertThat(testException.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testException.isIsYouthLeague()).isEqualTo(UPDATED_IS_YOUTH_LEAGUE);
        assertThat(testException.getCause()).isEqualTo(UPDATED_CAUSE);
    }

    @Test
    @Transactional
    public void updateNonExistingException() throws Exception {
        int databaseSizeBeforeUpdate = exceptionRepository.findAll().size();

        // Create the Exception

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExceptionMockMvc.perform(put("/api/exceptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(exception)))
            .andExpect(status().isBadRequest());

        // Validate the Exception in the database
        List<Exception> exceptionList = exceptionRepository.findAll();
        assertThat(exceptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteException() throws Exception {
        // Initialize the database
        exceptionRepository.saveAndFlush(exception);

        int databaseSizeBeforeDelete = exceptionRepository.findAll().size();

        // Delete the exception
        restExceptionMockMvc.perform(delete("/api/exceptions/{id}", exception.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Exception> exceptionList = exceptionRepository.findAll();
        assertThat(exceptionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
