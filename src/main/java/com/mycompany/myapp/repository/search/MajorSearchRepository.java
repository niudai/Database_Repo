package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Major;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Major} entity.
 */
public interface MajorSearchRepository extends ElasticsearchRepository<Major, Long> {
}
