package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Interval;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Interval} entity.
 */
public interface IntervalSearchRepository extends ElasticsearchRepository<Interval, Long> {
}
