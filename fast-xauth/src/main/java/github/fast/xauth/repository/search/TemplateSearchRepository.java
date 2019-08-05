package github.fast.xauth.repository.search;

import github.fast.xauth.domain.Template;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Template entity.
 */
public interface TemplateSearchRepository extends ElasticsearchRepository<Template, Long> {
}
