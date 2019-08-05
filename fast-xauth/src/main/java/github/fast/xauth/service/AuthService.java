package github.fast.xauth.service;

import github.fast.xauth.domain.Auth;
import github.fast.xauth.repository.AuthRepository;
import github.fast.xauth.repository.search.AuthSearchRepository;
import github.fast.xauth.service.dto.AuthDTO;
import github.fast.xauth.service.mapper.AuthMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Auth.
 */
@Service
@Transactional
public class AuthService {

    private final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final AuthRepository authRepository;

    private final AuthMapper authMapper;

    private final AuthSearchRepository authSearchRepository;

    public AuthService(AuthRepository authRepository, AuthMapper authMapper, AuthSearchRepository authSearchRepository) {
        this.authRepository = authRepository;
        this.authMapper = authMapper;
        this.authSearchRepository = authSearchRepository;
    }

    /**
     * Save a auth.
     *
     * @param authDTO the entity to save
     * @return the persisted entity
     */
    public AuthDTO save(AuthDTO authDTO) {
        log.debug("Request to save Auth : {}", authDTO);
        Auth auth = authMapper.toEntity(authDTO);
        auth = authRepository.save(auth);
        AuthDTO result = authMapper.toDto(auth);
        authSearchRepository.save(auth);
        return result;
    }

    /**
     * Get all the auths.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AuthDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Auths");
        return authRepository.findAll(pageable)
            .map(authMapper::toDto);
    }

    /**
     * Get all the Auth with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<AuthDTO> findAllWithEagerRelationships(Pageable pageable) {
        return authRepository.findAllWithEagerRelationships(pageable).map(authMapper::toDto);
    }
    

    /**
     * Get one auth by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<AuthDTO> findOne(Long id) {
        log.debug("Request to get Auth : {}", id);
        return authRepository.findOneWithEagerRelationships(id)
            .map(authMapper::toDto);
    }

    /**
     * Delete the auth by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Auth : {}", id);
        authRepository.deleteById(id);
        authSearchRepository.deleteById(id);
    }

    /**
     * Search for the auth corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AuthDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Auths for query {}", query);
        return authSearchRepository.search(queryStringQuery(query), pageable)
            .map(authMapper::toDto);
    }
}
