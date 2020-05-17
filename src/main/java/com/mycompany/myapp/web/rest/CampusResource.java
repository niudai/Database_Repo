package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Campus;
import com.mycompany.myapp.repository.CampusRepository;
import com.mycompany.myapp.repository.search.CampusSearchRepository;
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

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Campus}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CampusResource {

    private final Logger log = LoggerFactory.getLogger(CampusResource.class);

    private static final String ENTITY_NAME = "campus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CampusRepository campusRepository;

    private final CampusSearchRepository campusSearchRepository;

    public CampusResource(CampusRepository campusRepository, CampusSearchRepository campusSearchRepository) {
        this.campusRepository = campusRepository;
        this.campusSearchRepository = campusSearchRepository;
    }

    /**
     * {@code POST  /campuses} : Create a new campus.
     *
     * @param campus the campus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new campus, or with status {@code 400 (Bad Request)} if the campus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/campuses")
    public ResponseEntity<Campus> createCampus(@RequestBody Campus campus) throws URISyntaxException {
        log.debug("REST request to save Campus : {}", campus);
        if (campus.getId() != null) {
            throw new BadRequestAlertException("A new campus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Campus result = campusRepository.save(campus);
        campusSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/campuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /campuses} : Updates an existing campus.
     *
     * @param campus the campus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated campus,
     * or with status {@code 400 (Bad Request)} if the campus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the campus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/campuses")
    public ResponseEntity<Campus> updateCampus(@RequestBody Campus campus) throws URISyntaxException {
        log.debug("REST request to update Campus : {}", campus);
        if (campus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Campus result = campusRepository.save(campus);
        campusSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, campus.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /campuses} : get all the campuses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of campuses in body.
     */
    @GetMapping("/campuses")
    public List<Campus> getAllCampuses() {
        log.debug("REST request to get all Campuses");
        return campusRepository.findAll();
    }

    /**
     * {@code GET  /campuses/:id} : get the "id" campus.
     *
     * @param id the id of the campus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the campus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/campuses/{id}")
    public ResponseEntity<Campus> getCampus(@PathVariable Long id) {
        log.debug("REST request to get Campus : {}", id);
        Optional<Campus> campus = campusRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(campus);
    }

    /**
     * {@code DELETE  /campuses/:id} : delete the "id" campus.
     *
     * @param id the id of the campus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/campuses/{id}")
    public ResponseEntity<Void> deleteCampus(@PathVariable Long id) {
        log.debug("REST request to delete Campus : {}", id);
        campusRepository.deleteById(id);
        campusSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/campuses?query=:query} : search for the campus corresponding
     * to the query.
     *
     * @param query the query of the campus search.
     * @return the result of the search.
     */
    @GetMapping("/_search/campuses")
    public List<Campus> searchCampuses(@RequestParam String query) {
        log.debug("REST request to search Campuses for query {}", query);
        return StreamSupport
            .stream(campusSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
