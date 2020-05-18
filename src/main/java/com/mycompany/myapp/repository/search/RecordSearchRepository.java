package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Record;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Record} entity.
 */
public interface RecordSearchRepository extends ElasticsearchRepository<Record, Long> {
}
