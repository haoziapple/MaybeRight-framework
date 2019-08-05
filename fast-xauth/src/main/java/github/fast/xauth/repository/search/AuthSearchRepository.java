package github.fast.xauth.repository.search;

import github.fast.xauth.domain.Auth;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Auth entity.
 */
public interface AuthSearchRepository extends ElasticsearchRepository<Auth, Long> {
}
