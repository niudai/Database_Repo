package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.SchoolClass;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link SchoolClass} entity.
 */
public interface SchoolClassSearchRepository extends ElasticsearchRepository<SchoolClass, Long> {
}
