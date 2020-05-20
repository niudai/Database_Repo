package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.JException;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link JException} entity.
 */
public interface JExceptionSearchRepository extends ElasticsearchRepository<JException, Long> {
}
