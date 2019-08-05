package github.fast.xauth.repository.search;

import github.fast.xauth.domain.Site;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Site entity.
 */
public interface SiteSearchRepository extends ElasticsearchRepository<Site, Long> {
}
