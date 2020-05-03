package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Record;
import com.mycompany.myapp.repository.RecordRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Record}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RecordResource {

    private final Logger log = LoggerFactory.getLogger(RecordResource.class);

    private static final String ENTITY_NAME = "record";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecordRepository recordRepository;

    public RecordResource(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    /**
     * {@code POST  /records} : Create a new record.
     *
     * @param record the record to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new record, or with status {@code 400 (Bad Request)} if the record has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/records")
    public ResponseEntity<Record> createRecord(@RequestBody Record record) throws URISyntaxException {
        log.debug("REST request to save Record : {}", record);
        if (record.getId() != null) {
            throw new BadRequestAlertException("A new record cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Record result = recordRepository.save(record);
        return ResponseEntity.created(new URI("/api/records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /records} : Updates an existing record.
     *
     * @param record the record to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated record,
     * or with status {@code 400 (Bad Request)} if the record is not valid,
     * or with status {@code 500 (Internal Server Error)} if the record couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/records")
    public ResponseEntity<Record> updateRecord(@RequestBody Record record) throws URISyntaxException {
        log.debug("REST request to update Record : {}", record);
        if (record.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Record result = recordRepository.save(record);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, record.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /records} : get all the records.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of records in body.
     */
    @GetMapping("/records")
    public List<Record> getAllRecords() {
        log.debug("REST request to get all Records");
        return recordRepository.findAll();
    }

    /**
     * {@code GET  /records/:id} : get the "id" record.
     *
     * @param id the id of the record to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the record, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/records/{id}")
    public ResponseEntity<Record> getRecord(@PathVariable Long id) {
        log.debug("REST request to get Record : {}", id);
        Optional<Record> record = recordRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(record);
    }

    /**
     * {@code DELETE  /records/:id} : delete the "id" record.
     *
     * @param id the id of the record to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/records/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        log.debug("REST request to delete Record : {}", id);
        recordRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
