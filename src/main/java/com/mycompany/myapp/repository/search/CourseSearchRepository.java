package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Course;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Course} entity.
 */
public interface CourseSearchRepository extends ElasticsearchRepository<Course, Long> {
}
