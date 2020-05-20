package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.People;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link People} entity.
 */
public interface PeopleSearchRepository extends ElasticsearchRepository<People, Long> {
}
