package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Grade;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Grade} entity.
 */
public interface GradeSearchRepository extends ElasticsearchRepository<Grade, Long> {
}
