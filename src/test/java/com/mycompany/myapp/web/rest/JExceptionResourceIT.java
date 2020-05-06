package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;
import com.mycompany.myapp.domain.JException;
import com.mycompany.myapp.repository.JExceptionRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link JExceptionResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class JExceptionResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_YOUTH_LEAGUE = false;
    private static final Boolean UPDATED_IS_YOUTH_LEAGUE = true;

    private static final String DEFAULT_CAUSE = "AAAAAAAAAA";
    private static final String UPDATED_CAUSE = "BBBBBBBBBB";

    @Autowired
    private JExceptionRepository jExceptionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJExceptionMockMvc;

    private JException jException;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JException createEntity(EntityManager em) {
        JException jException = new JException()
            .date(DEFAULT_DATE)
            .isYouthLeague(DEFAULT_IS_YOUTH_LEAGUE)
            .cause(DEFAULT_CAUSE);
        return jException;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JException createUpdatedEntity(EntityManager em) {
        JException jException = new JException()
            .date(UPDATED_DATE)
            .isYouthLeague(UPDATED_IS_YOUTH_LEAGUE)
            .cause(UPDATED_CAUSE);
        return jException;
    }

    @BeforeEach
    public void initTest() {
        jException = createEntity(em);
    }

    @Test
    @Transactional
    public void createJException() throws Exception {
        int databaseSizeBeforeCreate = jExceptionRepository.findAll().size();

        // Create the JException
        restJExceptionMockMvc.perform(post("/api/j-exceptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jException)))
            .andExpect(status().isCreated());

        // Validate the JException in the database
        List<JException> jExceptionList = jExceptionRepository.findAll();
        assertThat(jExceptionList).hasSize(databaseSizeBeforeCreate + 1);
        JException testJException = jExceptionList.get(jExceptionList.size() - 1);
        assertThat(testJException.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testJException.isIsYouthLeague()).isEqualTo(DEFAULT_IS_YOUTH_LEAGUE);
        assertThat(testJException.getCause()).isEqualTo(DEFAULT_CAUSE);
    }

    @Test
    @Transactional
    public void createJExceptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jExceptionRepository.findAll().size();

        // Create the JException with an existing ID
        jException.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJExceptionMockMvc.perform(post("/api/j-exceptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jException)))
            .andExpect(status().isBadRequest());

        // Validate the JException in the database
        List<JException> jExceptionList = jExceptionRepository.findAll();
        assertThat(jExceptionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllJExceptions() throws Exception {
        // Initialize the database
        jExceptionRepository.saveAndFlush(jException);

        // Get all the jExceptionList
        restJExceptionMockMvc.perform(get("/api/j-exceptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jException.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].isYouthLeague").value(hasItem(DEFAULT_IS_YOUTH_LEAGUE.booleanValue())))
            .andExpect(jsonPath("$.[*].cause").value(hasItem(DEFAULT_CAUSE)));
    }
    
    @Test
    @Transactional
    public void getJException() throws Exception {
        // Initialize the database
        jExceptionRepository.saveAndFlush(jException);

        // Get the jException
        restJExceptionMockMvc.perform(get("/api/j-exceptions/{id}", jException.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jException.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.isYouthLeague").value(DEFAULT_IS_YOUTH_LEAGUE.booleanValue()))
            .andExpect(jsonPath("$.cause").value(DEFAULT_CAUSE));
    }

    @Test
    @Transactional
    public void getNonExistingJException() throws Exception {
        // Get the jException
        restJExceptionMockMvc.perform(get("/api/j-exceptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJException() throws Exception {
        // Initialize the database
        jExceptionRepository.saveAndFlush(jException);

        int databaseSizeBeforeUpdate = jExceptionRepository.findAll().size();

        // Update the jException
        JException updatedJException = jExceptionRepository.findById(jException.getId()).get();
        // Disconnect from session so that the updates on updatedJException are not directly saved in db
        em.detach(updatedJException);
        updatedJException
            .date(UPDATED_DATE)
            .isYouthLeague(UPDATED_IS_YOUTH_LEAGUE)
            .cause(UPDATED_CAUSE);

        restJExceptionMockMvc.perform(put("/api/j-exceptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedJException)))
            .andExpect(status().isOk());

        // Validate the JException in the database
        List<JException> jExceptionList = jExceptionRepository.findAll();
        assertThat(jExceptionList).hasSize(databaseSizeBeforeUpdate);
        JException testJException = jExceptionList.get(jExceptionList.size() - 1);
        assertThat(testJException.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testJException.isIsYouthLeague()).isEqualTo(UPDATED_IS_YOUTH_LEAGUE);
        assertThat(testJException.getCause()).isEqualTo(UPDATED_CAUSE);
    }

    @Test
    @Transactional
    public void updateNonExistingJException() throws Exception {
        int databaseSizeBeforeUpdate = jExceptionRepository.findAll().size();

        // Create the JException

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJExceptionMockMvc.perform(put("/api/j-exceptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jException)))
            .andExpect(status().isBadRequest());

        // Validate the JException in the database
        List<JException> jExceptionList = jExceptionRepository.findAll();
        assertThat(jExceptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJException() throws Exception {
        // Initialize the database
        jExceptionRepository.saveAndFlush(jException);

        int databaseSizeBeforeDelete = jExceptionRepository.findAll().size();

        // Delete the jException
        restJExceptionMockMvc.perform(delete("/api/j-exceptions/{id}", jException.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JException> jExceptionList = jExceptionRepository.findAll();
        assertThat(jExceptionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
