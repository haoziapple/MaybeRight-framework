package github.fast.xauth.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import github.fast.xauth.domain.Template;
import github.fast.xauth.domain.*; // for static metamodels
import github.fast.xauth.repository.TemplateRepository;
import github.fast.xauth.repository.search.TemplateSearchRepository;
import github.fast.xauth.service.dto.TemplateCriteria;
import github.fast.xauth.service.dto.TemplateDTO;
import github.fast.xauth.service.mapper.TemplateMapper;

/**
 * Service for executing complex queries for Template entities in the database.
 * The main input is a {@link TemplateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TemplateDTO} or a {@link Page} of {@link TemplateDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TemplateQueryService extends QueryService<Template> {

    private final Logger log = LoggerFactory.getLogger(TemplateQueryService.class);

    private final TemplateRepository templateRepository;

    private final TemplateMapper templateMapper;

    private final TemplateSearchRepository templateSearchRepository;

    public TemplateQueryService(TemplateRepository templateRepository, TemplateMapper templateMapper, TemplateSearchRepository templateSearchRepository) {
        this.templateRepository = templateRepository;
        this.templateMapper = templateMapper;
        this.templateSearchRepository = templateSearchRepository;
    }

    /**
     * Return a {@link List} of {@link TemplateDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TemplateDTO> findByCriteria(TemplateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Template> specification = createSpecification(criteria);
        return templateMapper.toDto(templateRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TemplateDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TemplateDTO> findByCriteria(TemplateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Template> specification = createSpecification(criteria);
        return templateRepository.findAll(specification, page)
            .map(templateMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TemplateCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Template> specification = createSpecification(criteria);
        return templateRepository.count(specification);
    }

    /**
     * Function to convert TemplateCriteria to a {@link Specification}
     */
    private Specification<Template> createSpecification(TemplateCriteria criteria) {
        Specification<Template> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Template_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Template_.name));
            }
            if (criteria.getClientType() != null) {
                specification = specification.and(buildSpecification(criteria.getClientType(), Template_.clientType));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), Template_.remark));
            }
            if (criteria.getExtmap() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExtmap(), Template_.extmap));
            }
            if (criteria.getWorkspaceId() != null) {
                specification = specification.and(buildSpecification(criteria.getWorkspaceId(),
                    root -> root.join(Template_.workspaces, JoinType.LEFT).get(Workspace_.id)));
            }
            if (criteria.getSiteId() != null) {
                specification = specification.and(buildSpecification(criteria.getSiteId(),
                    root -> root.join(Template_.sites, JoinType.LEFT).get(Site_.id)));
            }
            if (criteria.getAuthId() != null) {
                specification = specification.and(buildSpecification(criteria.getAuthId(),
                    root -> root.join(Template_.auths, JoinType.LEFT).get(Auth_.id)));
            }
            if (criteria.getMenuId() != null) {
                specification = specification.and(buildSpecification(criteria.getMenuId(),
                    root -> root.join(Template_.menus, JoinType.LEFT).get(Menu_.id)));
            }
        }
        return specification;
    }
}
