package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterApp;
import com.mycompany.myapp.domain.People;
import com.mycompany.myapp.repository.PeopleRepository;

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

import com.mycompany.myapp.domain.enumeration.IdType;
/**
 * Integration tests for the {@link PeopleResource} REST controller.
 */
@SpringBootTest(classes = JhipsterApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class PeopleResourceIT {

    private static final IdType DEFAULT_ID_TYPE = IdType.IdCard;
    private static final IdType UPDATED_ID_TYPE = IdType.Passport;

    private static final String DEFAULT_CHINESE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CHINESE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ENGLISH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENGLISH_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_GENDER = 1;
    private static final Integer UPDATED_GENDER = 2;

    private static final Instant DEFAULT_BIRTH_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BIRTH_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_RACE = "AAAAAAAAAA";
    private static final String UPDATED_RACE = "BBBBBBBBBB";

    private static final String DEFAULT_NATION = "AAAAAAAAAA";
    private static final String UPDATED_NATION = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_POSTCODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTCODE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPeopleMockMvc;

    private People people;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static People createEntity(EntityManager em) {
        People people = new People()
            .idType(DEFAULT_ID_TYPE)
            .chineseName(DEFAULT_CHINESE_NAME)
            .englishName(DEFAULT_ENGLISH_NAME)
            .gender(DEFAULT_GENDER)
            .birthDate(DEFAULT_BIRTH_DATE)
            .race(DEFAULT_RACE)
            .nation(DEFAULT_NATION)
            .address(DEFAULT_ADDRESS)
            .postcode(DEFAULT_POSTCODE)
            .telephone(DEFAULT_TELEPHONE);
        return people;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static People createUpdatedEntity(EntityManager em) {
        People people = new People()
            .idType(UPDATED_ID_TYPE)
            .chineseName(UPDATED_CHINESE_NAME)
            .englishName(UPDATED_ENGLISH_NAME)
            .gender(UPDATED_GENDER)
            .birthDate(UPDATED_BIRTH_DATE)
            .race(UPDATED_RACE)
            .nation(UPDATED_NATION)
            .address(UPDATED_ADDRESS)
            .postcode(UPDATED_POSTCODE)
            .telephone(UPDATED_TELEPHONE);
        return people;
    }

    @BeforeEach
    public void initTest() {
        people = createEntity(em);
    }

    @Test
    @Transactional
    public void createPeople() throws Exception {
        int databaseSizeBeforeCreate = peopleRepository.findAll().size();

        // Create the People
        restPeopleMockMvc.perform(post("/api/people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(people)))
            .andExpect(status().isCreated());

        // Validate the People in the database
        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeCreate + 1);
        People testPeople = peopleList.get(peopleList.size() - 1);
        assertThat(testPeople.getIdType()).isEqualTo(DEFAULT_ID_TYPE);
        assertThat(testPeople.getChineseName()).isEqualTo(DEFAULT_CHINESE_NAME);
        assertThat(testPeople.getEnglishName()).isEqualTo(DEFAULT_ENGLISH_NAME);
        assertThat(testPeople.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testPeople.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testPeople.getRace()).isEqualTo(DEFAULT_RACE);
        assertThat(testPeople.getNation()).isEqualTo(DEFAULT_NATION);
        assertThat(testPeople.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPeople.getPostcode()).isEqualTo(DEFAULT_POSTCODE);
        assertThat(testPeople.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
    }

    @Test
    @Transactional
    public void createPeopleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = peopleRepository.findAll().size();

        // Create the People with an existing ID
        people.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeopleMockMvc.perform(post("/api/people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(people)))
            .andExpect(status().isBadRequest());

        // Validate the People in the database
        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPeople() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList
        restPeopleMockMvc.perform(get("/api/people?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(people.getId().intValue())))
            .andExpect(jsonPath("$.[*].idType").value(hasItem(DEFAULT_ID_TYPE.toString())))
            .andExpect(jsonPath("$.[*].chineseName").value(hasItem(DEFAULT_CHINESE_NAME)))
            .andExpect(jsonPath("$.[*].englishName").value(hasItem(DEFAULT_ENGLISH_NAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].race").value(hasItem(DEFAULT_RACE)))
            .andExpect(jsonPath("$.[*].nation").value(hasItem(DEFAULT_NATION)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].postcode").value(hasItem(DEFAULT_POSTCODE)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)));
    }
    
    @Test
    @Transactional
    public void getPeople() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get the people
        restPeopleMockMvc.perform(get("/api/people/{id}", people.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(people.getId().intValue()))
            .andExpect(jsonPath("$.idType").value(DEFAULT_ID_TYPE.toString()))
            .andExpect(jsonPath("$.chineseName").value(DEFAULT_CHINESE_NAME))
            .andExpect(jsonPath("$.englishName").value(DEFAULT_ENGLISH_NAME))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.race").value(DEFAULT_RACE))
            .andExpect(jsonPath("$.nation").value(DEFAULT_NATION))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.postcode").value(DEFAULT_POSTCODE))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE));
    }

    @Test
    @Transactional
    public void getNonExistingPeople() throws Exception {
        // Get the people
        restPeopleMockMvc.perform(get("/api/people/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePeople() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        int databaseSizeBeforeUpdate = peopleRepository.findAll().size();

        // Update the people
        People updatedPeople = peopleRepository.findById(people.getId()).get();
        // Disconnect from session so that the updates on updatedPeople are not directly saved in db
        em.detach(updatedPeople);
        updatedPeople
            .idType(UPDATED_ID_TYPE)
            .chineseName(UPDATED_CHINESE_NAME)
            .englishName(UPDATED_ENGLISH_NAME)
            .gender(UPDATED_GENDER)
            .birthDate(UPDATED_BIRTH_DATE)
            .race(UPDATED_RACE)
            .nation(UPDATED_NATION)
            .address(UPDATED_ADDRESS)
            .postcode(UPDATED_POSTCODE)
            .telephone(UPDATED_TELEPHONE);

        restPeopleMockMvc.perform(put("/api/people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPeople)))
            .andExpect(status().isOk());

        // Validate the People in the database
        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeUpdate);
        People testPeople = peopleList.get(peopleList.size() - 1);
        assertThat(testPeople.getIdType()).isEqualTo(UPDATED_ID_TYPE);
        assertThat(testPeople.getChineseName()).isEqualTo(UPDATED_CHINESE_NAME);
        assertThat(testPeople.getEnglishName()).isEqualTo(UPDATED_ENGLISH_NAME);
        assertThat(testPeople.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPeople.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testPeople.getRace()).isEqualTo(UPDATED_RACE);
        assertThat(testPeople.getNation()).isEqualTo(UPDATED_NATION);
        assertThat(testPeople.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPeople.getPostcode()).isEqualTo(UPDATED_POSTCODE);
        assertThat(testPeople.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingPeople() throws Exception {
        int databaseSizeBeforeUpdate = peopleRepository.findAll().size();

        // Create the People

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeopleMockMvc.perform(put("/api/people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(people)))
            .andExpect(status().isBadRequest());

        // Validate the People in the database
        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePeople() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        int databaseSizeBeforeDelete = peopleRepository.findAll().size();

        // Delete the people
        restPeopleMockMvc.perform(delete("/api/people/{id}", people.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
