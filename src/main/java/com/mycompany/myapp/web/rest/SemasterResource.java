package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Semaster;
import com.mycompany.myapp.repository.SemasterRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Semaster}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SemasterResource {

    private final Logger log = LoggerFactory.getLogger(SemasterResource.class);

    private static final String ENTITY_NAME = "semaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SemasterRepository semasterRepository;

    public SemasterResource(SemasterRepository semasterRepository) {
        this.semasterRepository = semasterRepository;
    }

    /**
     * {@code POST  /semasters} : Create a new semaster.
     *
     * @param semaster the semaster to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new semaster, or with status {@code 400 (Bad Request)} if the semaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/semasters")
    public ResponseEntity<Semaster> createSemaster(@RequestBody Semaster semaster) throws URISyntaxException {
        log.debug("REST request to save Semaster : {}", semaster);
        if (semaster.getId() != null) {
            throw new BadRequestAlertException("A new semaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Semaster result = semasterRepository.save(semaster);
        return ResponseEntity.created(new URI("/api/semasters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /semasters} : Updates an existing semaster.
     *
     * @param semaster the semaster to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated semaster,
     * or with status {@code 400 (Bad Request)} if the semaster is not valid,
     * or with status {@code 500 (Internal Server Error)} if the semaster couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/semasters")
    public ResponseEntity<Semaster> updateSemaster(@RequestBody Semaster semaster) throws URISyntaxException {
        log.debug("REST request to update Semaster : {}", semaster);
        if (semaster.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Semaster result = semasterRepository.save(semaster);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, semaster.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /semasters} : get all the semasters.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of semasters in body.
     */
    @GetMapping("/semasters")
    public List<Semaster> getAllSemasters() {
        log.debug("REST request to get all Semasters");
        return semasterRepository.findAll();
    }

    /**
     * {@code GET  /semasters/:id} : get the "id" semaster.
     *
     * @param id the id of the semaster to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the semaster, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/semasters/{id}")
    public ResponseEntity<Semaster> getSemaster(@PathVariable Long id) {
        log.debug("REST request to get Semaster : {}", id);
        Optional<Semaster> semaster = semasterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(semaster);
    }

    /**
     * {@code DELETE  /semasters/:id} : delete the "id" semaster.
     *
     * @param id the id of the semaster to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/semasters/{id}")
    public ResponseEntity<Void> deleteSemaster(@PathVariable Long id) {
        log.debug("REST request to delete Semaster : {}", id);
        semasterRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
