package com.mycompany.myapp.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link JExceptionSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class JExceptionSearchRepositoryMockConfiguration {

    @MockBean
    private JExceptionSearchRepository mockJExceptionSearchRepository;

}
