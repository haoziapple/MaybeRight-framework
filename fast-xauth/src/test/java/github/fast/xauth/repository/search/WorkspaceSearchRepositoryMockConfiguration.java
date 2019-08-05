package github.fast.xauth.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of WorkspaceSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class WorkspaceSearchRepositoryMockConfiguration {

    @MockBean
    private WorkspaceSearchRepository mockWorkspaceSearchRepository;

}
