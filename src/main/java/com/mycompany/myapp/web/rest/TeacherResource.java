package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Teacher;
import com.mycompany.myapp.repository.TeacherRepository;
import com.mycompany.myapp.repository.search.TeacherSearchRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Teacher}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TeacherResource {

    private final Logger log = LoggerFactory.getLogger(TeacherResource.class);

    private static final String ENTITY_NAME = "teacher";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TeacherRepository teacherRepository;

    private final TeacherSearchRepository teacherSearchRepository;

    public TeacherResource(TeacherRepository teacherRepository, TeacherSearchRepository teacherSearchRepository) {
        this.teacherRepository = teacherRepository;
        this.teacherSearchRepository = teacherSearchRepository;
    }

    /**
     * {@code POST  /teachers} : Create a new teacher.
     *
     * @param teacher the teacher to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new teacher, or with status {@code 400 (Bad Request)} if the teacher has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/teachers")
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher teacher) throws URISyntaxException {
        log.debug("REST request to save Teacher : {}", teacher);
        if (teacher.getId() != null) {
            throw new BadRequestAlertException("A new teacher cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Teacher result = teacherRepository.save(teacher);
        teacherSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/teachers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /teachers} : Updates an existing teacher.
     *
     * @param teacher the teacher to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teacher,
     * or with status {@code 400 (Bad Request)} if the teacher is not valid,
     * or with status {@code 500 (Internal Server Error)} if the teacher couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/teachers")
    public ResponseEntity<Teacher> updateTeacher(@RequestBody Teacher teacher) throws URISyntaxException {
        log.debug("REST request to update Teacher : {}", teacher);
        if (teacher.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Teacher result = teacherRepository.save(teacher);
        teacherSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, teacher.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /teachers} : get all the teachers.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of teachers in body.
     */
    @GetMapping("/teachers")
    public List<Teacher> getAllTeachers(@RequestParam(required = false) String filter) {
        if ("schoolclass-is-null".equals(filter)) {
            log.debug("REST request to get all Teachers where schoolClass is null");
            return StreamSupport
                .stream(teacherRepository.findAll().spliterator(), false)
                .filter(teacher -> teacher.getSchoolClass() == null)
                .collect(Collectors.toList());
        }
        if ("people-is-null".equals(filter)) {
            log.debug("REST request to get all Teachers where people is null");
            return StreamSupport
                .stream(teacherRepository.findAll().spliterator(), false)
                .filter(teacher -> teacher.getPeople() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Teachers");
        return teacherRepository.findAll();
    }

    /**
     * {@code GET  /teachers/:id} : get the "id" teacher.
     *
     * @param id the id of the teacher to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the teacher, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/teachers/{id}")
    public ResponseEntity<Teacher> getTeacher(@PathVariable Long id) {
        log.debug("REST request to get Teacher : {}", id);
        Optional<Teacher> teacher = teacherRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(teacher);
    }

    /**
     * {@code DELETE  /teachers/:id} : delete the "id" teacher.
     *
     * @param id the id of the teacher to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/teachers/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        log.debug("REST request to delete Teacher : {}", id);
        teacherRepository.deleteById(id);
        teacherSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/teachers?query=:query} : search for the teacher corresponding
     * to the query.
     *
     * @param query the query of the teacher search.
     * @return the result of the search.
     */
    @GetMapping("/_search/teachers")
    public List<Teacher> searchTeachers(@RequestParam String query) {
        log.debug("REST request to search Teachers for query {}", query);
        return StreamSupport
            .stream(teacherSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
