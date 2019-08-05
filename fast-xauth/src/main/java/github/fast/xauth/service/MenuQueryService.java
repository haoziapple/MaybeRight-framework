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

import github.fast.xauth.domain.Menu;
import github.fast.xauth.domain.*; // for static metamodels
import github.fast.xauth.repository.MenuRepository;
import github.fast.xauth.repository.search.MenuSearchRepository;
import github.fast.xauth.service.dto.MenuCriteria;
import github.fast.xauth.service.dto.MenuDTO;
import github.fast.xauth.service.mapper.MenuMapper;

/**
 * Service for executing complex queries for Menu entities in the database.
 * The main input is a {@link MenuCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MenuDTO} or a {@link Page} of {@link MenuDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MenuQueryService extends QueryService<Menu> {

    private final Logger log = LoggerFactory.getLogger(MenuQueryService.class);

    private final MenuRepository menuRepository;

    private final MenuMapper menuMapper;

    private final MenuSearchRepository menuSearchRepository;

    public MenuQueryService(MenuRepository menuRepository, MenuMapper menuMapper, MenuSearchRepository menuSearchRepository) {
        this.menuRepository = menuRepository;
        this.menuMapper = menuMapper;
        this.menuSearchRepository = menuSearchRepository;
    }

    /**
     * Return a {@link List} of {@link MenuDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MenuDTO> findByCriteria(MenuCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Menu> specification = createSpecification(criteria);
        return menuMapper.toDto(menuRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MenuDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MenuDTO> findByCriteria(MenuCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Menu> specification = createSpecification(criteria);
        return menuRepository.findAll(specification, page)
            .map(menuMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MenuCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Menu> specification = createSpecification(criteria);
        return menuRepository.count(specification);
    }

    /**
     * Function to convert MenuCriteria to a {@link Specification}
     */
    private Specification<Menu> createSpecification(MenuCriteria criteria) {
        Specification<Menu> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Menu_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Menu_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Menu_.code));
            }
            if (criteria.getSeq() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSeq(), Menu_.seq));
            }
            if (criteria.getClientType() != null) {
                specification = specification.and(buildSpecification(criteria.getClientType(), Menu_.clientType));
            }
            if (criteria.getUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrl(), Menu_.url));
            }
            if (criteria.getLeaf() != null) {
                specification = specification.and(buildSpecification(criteria.getLeaf(), Menu_.leaf));
            }
            if (criteria.getShowFlag() != null) {
                specification = specification.and(buildSpecification(criteria.getShowFlag(), Menu_.showFlag));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), Menu_.remark));
            }
            if (criteria.getExtmap() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExtmap(), Menu_.extmap));
            }
            if (criteria.getParentId() != null) {
                specification = specification.and(buildSpecification(criteria.getParentId(),
                    root -> root.join(Menu_.parent, JoinType.LEFT).get(Menu_.id)));
            }
            if (criteria.getRoleId() != null) {
                specification = specification.and(buildSpecification(criteria.getRoleId(),
                    root -> root.join(Menu_.roles, JoinType.LEFT).get(Role_.id)));
            }
            if (criteria.getTemplateId() != null) {
                specification = specification.and(buildSpecification(criteria.getTemplateId(),
                    root -> root.join(Menu_.templates, JoinType.LEFT).get(Template_.id)));
            }
            if (criteria.getAuthId() != null) {
                specification = specification.and(buildSpecification(criteria.getAuthId(),
                    root -> root.join(Menu_.auths, JoinType.LEFT).get(Auth_.id)));
            }
        }
        return specification;
    }
}
