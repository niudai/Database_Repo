package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Interval;
import com.mycompany.myapp.repository.IntervalRepository;
import com.mycompany.myapp.repository.search.IntervalSearchRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Interval}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class IntervalResource {

    private final Logger log = LoggerFactory.getLogger(IntervalResource.class);

    private static final String ENTITY_NAME = "interval";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IntervalRepository intervalRepository;

    private final IntervalSearchRepository intervalSearchRepository;

    public IntervalResource(IntervalRepository intervalRepository, IntervalSearchRepository intervalSearchRepository) {
        this.intervalRepository = intervalRepository;
        this.intervalSearchRepository = intervalSearchRepository;
    }

    /**
     * {@code POST  /intervals} : Create a new interval.
     *
     * @param interval the interval to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new interval, or with status {@code 400 (Bad Request)} if the interval has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/intervals")
    public ResponseEntity<Interval> createInterval(@RequestBody Interval interval) throws URISyntaxException {
        log.debug("REST request to save Interval : {}", interval);
        if (interval.getId() != null) {
            throw new BadRequestAlertException("A new interval cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Interval result = intervalRepository.save(interval);
        intervalSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/intervals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /intervals} : Updates an existing interval.
     *
     * @param interval the interval to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interval,
     * or with status {@code 400 (Bad Request)} if the interval is not valid,
     * or with status {@code 500 (Internal Server Error)} if the interval couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/intervals")
    public ResponseEntity<Interval> updateInterval(@RequestBody Interval interval) throws URISyntaxException {
        log.debug("REST request to update Interval : {}", interval);
        if (interval.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Interval result = intervalRepository.save(interval);
        intervalSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, interval.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /intervals} : get all the intervals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of intervals in body.
     */
    @GetMapping("/intervals")
    public List<Interval> getAllIntervals() {
        log.debug("REST request to get all Intervals");
        return intervalRepository.findAll();
    }

    /**
     * {@code GET  /intervals/:id} : get the "id" interval.
     *
     * @param id the id of the interval to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the interval, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/intervals/{id}")
    public ResponseEntity<Interval> getInterval(@PathVariable Long id) {
        log.debug("REST request to get Interval : {}", id);
        Optional<Interval> interval = intervalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(interval);
    }

    /**
     * {@code DELETE  /intervals/:id} : delete the "id" interval.
     *
     * @param id the id of the interval to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/intervals/{id}")
    public ResponseEntity<Void> deleteInterval(@PathVariable Long id) {
        log.debug("REST request to delete Interval : {}", id);

        intervalRepository.deleteById(id);
        intervalSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/intervals?query=:query} : search for the interval corresponding
     * to the query.
     *
     * @param query the query of the interval search.
     * @return the result of the search.
     */
    @GetMapping("/_search/intervals")
    public List<Interval> searchIntervals(@RequestParam String query) {
        log.debug("REST request to search Intervals for query {}", query);
        return StreamSupport
            .stream(intervalSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
