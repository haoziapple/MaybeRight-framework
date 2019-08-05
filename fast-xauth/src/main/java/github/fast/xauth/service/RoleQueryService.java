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

import github.fast.xauth.domain.Role;
import github.fast.xauth.domain.*; // for static metamodels
import github.fast.xauth.repository.RoleRepository;
import github.fast.xauth.repository.search.RoleSearchRepository;
import github.fast.xauth.service.dto.RoleCriteria;
import github.fast.xauth.service.dto.RoleDTO;
import github.fast.xauth.service.mapper.RoleMapper;

/**
 * Service for executing complex queries for Role entities in the database.
 * The main input is a {@link RoleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RoleDTO} or a {@link Page} of {@link RoleDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RoleQueryService extends QueryService<Role> {

    private final Logger log = LoggerFactory.getLogger(RoleQueryService.class);

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    private final RoleSearchRepository roleSearchRepository;

    public RoleQueryService(RoleRepository roleRepository, RoleMapper roleMapper, RoleSearchRepository roleSearchRepository) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
        this.roleSearchRepository = roleSearchRepository;
    }

    /**
     * Return a {@link List} of {@link RoleDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RoleDTO> findByCriteria(RoleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Role> specification = createSpecification(criteria);
        return roleMapper.toDto(roleRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RoleDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RoleDTO> findByCriteria(RoleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Role> specification = createSpecification(criteria);
        return roleRepository.findAll(specification, page)
            .map(roleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RoleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Role> specification = createSpecification(criteria);
        return roleRepository.count(specification);
    }

    /**
     * Function to convert RoleCriteria to a {@link Specification}
     */
    private Specification<Role> createSpecification(RoleCriteria criteria) {
        Specification<Role> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Role_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Role_.name));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), Role_.remark));
            }
            if (criteria.getExtmap() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExtmap(), Role_.extmap));
            }
            if (criteria.getRoleType() != null) {
                specification = specification.and(buildSpecification(criteria.getRoleType(), Role_.roleType));
            }
            if (criteria.getWorkspaceId() != null) {
                specification = specification.and(buildSpecification(criteria.getWorkspaceId(),
                    root -> root.join(Role_.workspace, JoinType.LEFT).get(Workspace_.id)));
            }
            if (criteria.getSiteId() != null) {
                specification = specification.and(buildSpecification(criteria.getSiteId(),
                    root -> root.join(Role_.sites, JoinType.LEFT).get(Site_.id)));
            }
            if (criteria.getAuthId() != null) {
                specification = specification.and(buildSpecification(criteria.getAuthId(),
                    root -> root.join(Role_.auths, JoinType.LEFT).get(Auth_.id)));
            }
            if (criteria.getMenuId() != null) {
                specification = specification.and(buildSpecification(criteria.getMenuId(),
                    root -> root.join(Role_.menus, JoinType.LEFT).get(Menu_.id)));
            }
            if (criteria.getProfileId() != null) {
                specification = specification.and(buildSpecification(criteria.getProfileId(),
                    root -> root.join(Role_.profiles, JoinType.LEFT).get(Profile_.id)));
            }
        }
        return specification;
    }
}
