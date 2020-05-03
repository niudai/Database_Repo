package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.People;
import com.mycompany.myapp.repository.PeopleRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.People}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PeopleResource {

    private final Logger log = LoggerFactory.getLogger(PeopleResource.class);

    private static final String ENTITY_NAME = "people";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PeopleRepository peopleRepository;

    public PeopleResource(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    /**
     * {@code POST  /people} : Create a new people.
     *
     * @param people the people to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new people, or with status {@code 400 (Bad Request)} if the people has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/people")
    public ResponseEntity<People> createPeople(@RequestBody People people) throws URISyntaxException {
        log.debug("REST request to save People : {}", people);
        if (people.getId() != null) {
            throw new BadRequestAlertException("A new people cannot already have an ID", ENTITY_NAME, "idexists");
        }
        People result = peopleRepository.save(people);
        return ResponseEntity.created(new URI("/api/people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /people} : Updates an existing people.
     *
     * @param people the people to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated people,
     * or with status {@code 400 (Bad Request)} if the people is not valid,
     * or with status {@code 500 (Internal Server Error)} if the people couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/people")
    public ResponseEntity<People> updatePeople(@RequestBody People people) throws URISyntaxException {
        log.debug("REST request to update People : {}", people);
        if (people.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        People result = peopleRepository.save(people);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, people.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /people} : get all the people.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of people in body.
     */
    @GetMapping("/people")
    public List<People> getAllPeople(@RequestParam(required = false) String filter) {
        if ("managemajor-is-null".equals(filter)) {
            log.debug("REST request to get all Peoples where manageMajor is null");
            return StreamSupport
                .stream(peopleRepository.findAll().spliterator(), false)
                .filter(people -> people.getManageMajor() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all People");
        return peopleRepository.findAll();
    }

    /**
     * {@code GET  /people/:id} : get the "id" people.
     *
     * @param id the id of the people to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the people, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/people/{id}")
    public ResponseEntity<People> getPeople(@PathVariable Long id) {
        log.debug("REST request to get People : {}", id);
        Optional<People> people = peopleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(people);
    }

    /**
     * {@code DELETE  /people/:id} : delete the "id" people.
     *
     * @param id the id of the people to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/people/{id}")
    public ResponseEntity<Void> deletePeople(@PathVariable Long id) {
        log.debug("REST request to delete People : {}", id);
        peopleRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
