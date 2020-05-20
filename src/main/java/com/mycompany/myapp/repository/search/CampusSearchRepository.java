package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Campus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Campus} entity.
 */
public interface CampusSearchRepository extends ElasticsearchRepository<Campus, Long> {
}
