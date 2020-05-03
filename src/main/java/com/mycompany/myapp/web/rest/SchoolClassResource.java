package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.SchoolClass;
import com.mycompany.myapp.repository.SchoolClassRepository;
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

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.SchoolClass}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SchoolClassResource {

    private final Logger log = LoggerFactory.getLogger(SchoolClassResource.class);

    private static final String ENTITY_NAME = "schoolClass";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SchoolClassRepository schoolClassRepository;

    public SchoolClassResource(SchoolClassRepository schoolClassRepository) {
        this.schoolClassRepository = schoolClassRepository;
    }

    /**
     * {@code POST  /school-classes} : Create a new schoolClass.
     *
     * @param schoolClass the schoolClass to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new schoolClass, or with status {@code 400 (Bad Request)} if the schoolClass has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/school-classes")
    public ResponseEntity<SchoolClass> createSchoolClass(@RequestBody SchoolClass schoolClass) throws URISyntaxException {
        log.debug("REST request to save SchoolClass : {}", schoolClass);
        if (schoolClass.getId() != null) {
            throw new BadRequestAlertException("A new schoolClass cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SchoolClass result = schoolClassRepository.save(schoolClass);
        return ResponseEntity.created(new URI("/api/school-classes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /school-classes} : Updates an existing schoolClass.
     *
     * @param schoolClass the schoolClass to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated schoolClass,
     * or with status {@code 400 (Bad Request)} if the schoolClass is not valid,
     * or with status {@code 500 (Internal Server Error)} if the schoolClass couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/school-classes")
    public ResponseEntity<SchoolClass> updateSchoolClass(@RequestBody SchoolClass schoolClass) throws URISyntaxException {
        log.debug("REST request to update SchoolClass : {}", schoolClass);
        if (schoolClass.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SchoolClass result = schoolClassRepository.save(schoolClass);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, schoolClass.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /school-classes} : get all the schoolClasses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of schoolClasses in body.
     */
    @GetMapping("/school-classes")
    public List<SchoolClass> getAllSchoolClasses() {
        log.debug("REST request to get all SchoolClasses");
        return schoolClassRepository.findAll();
    }

    /**
     * {@code GET  /school-classes/:id} : get the "id" schoolClass.
     *
     * @param id the id of the schoolClass to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the schoolClass, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/school-classes/{id}")
    public ResponseEntity<SchoolClass> getSchoolClass(@PathVariable Long id) {
        log.debug("REST request to get SchoolClass : {}", id);
        Optional<SchoolClass> schoolClass = schoolClassRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(schoolClass);
    }

    /**
     * {@code DELETE  /school-classes/:id} : delete the "id" schoolClass.
     *
     * @param id the id of the schoolClass to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/school-classes/{id}")
    public ResponseEntity<Void> deleteSchoolClass(@PathVariable Long id) {
        log.debug("REST request to delete SchoolClass : {}", id);
        schoolClassRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
