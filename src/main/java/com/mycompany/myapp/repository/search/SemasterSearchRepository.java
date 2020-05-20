package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Semaster;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Semaster} entity.
 */
public interface SemasterSearchRepository extends ElasticsearchRepository<Semaster, Long> {
}
