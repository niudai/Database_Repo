package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.JException;
import com.mycompany.myapp.repository.JExceptionRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.JException}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class JExceptionResource {

    private final Logger log = LoggerFactory.getLogger(JExceptionResource.class);

    private static final String ENTITY_NAME = "jException";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JExceptionRepository jExceptionRepository;

    public JExceptionResource(JExceptionRepository jExceptionRepository) {
        this.jExceptionRepository = jExceptionRepository;
    }

    /**
     * {@code POST  /j-exceptions} : Create a new jException.
     *
     * @param jException the jException to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jException, or with status {@code 400 (Bad Request)} if the jException has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/j-exceptions")
    public ResponseEntity<JException> createJException(@RequestBody JException jException) throws URISyntaxException {
        log.debug("REST request to save JException : {}", jException);
        if (jException.getId() != null) {
            throw new BadRequestAlertException("A new jException cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JException result = jExceptionRepository.save(jException);
        return ResponseEntity.created(new URI("/api/j-exceptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /j-exceptions} : Updates an existing jException.
     *
     * @param jException the jException to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jException,
     * or with status {@code 400 (Bad Request)} if the jException is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jException couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/j-exceptions")
    public ResponseEntity<JException> updateJException(@RequestBody JException jException) throws URISyntaxException {
        log.debug("REST request to update JException : {}", jException);
        if (jException.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JException result = jExceptionRepository.save(jException);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jException.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /j-exceptions} : get all the jExceptions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jExceptions in body.
     */
    @GetMapping("/j-exceptions")
    public List<JException> getAllJExceptions() {
        log.debug("REST request to get all JExceptions");
        return jExceptionRepository.findAll();
    }

    /**
     * {@code GET  /j-exceptions/:id} : get the "id" jException.
     *
     * @param id the id of the jException to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jException, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/j-exceptions/{id}")
    public ResponseEntity<JException> getJException(@PathVariable Long id) {
        log.debug("REST request to get JException : {}", id);
        Optional<JException> jException = jExceptionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(jException);
    }

    /**
     * {@code DELETE  /j-exceptions/:id} : delete the "id" jException.
     *
     * @param id the id of the jException to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/j-exceptions/{id}")
    public ResponseEntity<Void> deleteJException(@PathVariable Long id) {
        log.debug("REST request to delete JException : {}", id);
        jExceptionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
