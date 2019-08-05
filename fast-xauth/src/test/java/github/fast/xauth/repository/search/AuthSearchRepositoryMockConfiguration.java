package github.fast.xauth.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of AuthSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class AuthSearchRepositoryMockConfiguration {

    @MockBean
    private AuthSearchRepository mockAuthSearchRepository;

}
