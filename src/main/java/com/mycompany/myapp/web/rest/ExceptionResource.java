package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Exception;
import com.mycompany.myapp.repository.ExceptionRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Exception}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ExceptionResource {

    private final Logger log = LoggerFactory.getLogger(ExceptionResource.class);

    private static final String ENTITY_NAME = "exception";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExceptionRepository exceptionRepository;

    public ExceptionResource(ExceptionRepository exceptionRepository) {
        this.exceptionRepository = exceptionRepository;
    }

    /**
     * {@code POST  /exceptions} : Create a new exception.
     *
     * @param exception the exception to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new exception, or with status {@code 400 (Bad Request)} if the exception has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/exceptions")
    public ResponseEntity<Exception> createException(@RequestBody Exception exception) throws URISyntaxException {
        log.debug("REST request to save Exception : {}", exception);
        if (exception.getId() != null) {
            throw new BadRequestAlertException("A new exception cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Exception result = exceptionRepository.save(exception);
        return ResponseEntity.created(new URI("/api/exceptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /exceptions} : Updates an existing exception.
     *
     * @param exception the exception to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated exception,
     * or with status {@code 400 (Bad Request)} if the exception is not valid,
     * or with status {@code 500 (Internal Server Error)} if the exception couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/exceptions")
    public ResponseEntity<Exception> updateException(@RequestBody Exception exception) throws URISyntaxException {
        log.debug("REST request to update Exception : {}", exception);
        if (exception.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Exception result = exceptionRepository.save(exception);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, exception.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /exceptions} : get all the exceptions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of exceptions in body.
     */
    @GetMapping("/exceptions")
    public List<Exception> getAllExceptions() {
        log.debug("REST request to get all Exceptions");
        return exceptionRepository.findAll();
    }

    /**
     * {@code GET  /exceptions/:id} : get the "id" exception.
     *
     * @param id the id of the exception to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the exception, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/exceptions/{id}")
    public ResponseEntity<Exception> getException(@PathVariable Long id) {
        log.debug("REST request to get Exception : {}", id);
        Optional<Exception> exception = exceptionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(exception);
    }

    /**
     * {@code DELETE  /exceptions/:id} : delete the "id" exception.
     *
     * @param id the id of the exception to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/exceptions/{id}")
    public ResponseEntity<Void> deleteException(@PathVariable Long id) {
        log.debug("REST request to delete Exception : {}", id);
        exceptionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
