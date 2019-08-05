package github.fast.xauth.repository.search;

import github.fast.xauth.domain.Workspace;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Workspace entity.
 */
public interface WorkspaceSearchRepository extends ElasticsearchRepository<Workspace, Long> {
}
