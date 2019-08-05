package github.fast.xauth.service;

import github.fast.xauth.domain.Site;
import github.fast.xauth.repository.SiteRepository;
import github.fast.xauth.repository.search.SiteSearchRepository;
import github.fast.xauth.service.dto.SiteDTO;
import github.fast.xauth.service.mapper.SiteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Site.
 */
@Service
@Transactional
public class SiteService {

    private final Logger log = LoggerFactory.getLogger(SiteService.class);

    private final SiteRepository siteRepository;

    private final SiteMapper siteMapper;

    private final SiteSearchRepository siteSearchRepository;

    public SiteService(SiteRepository siteRepository, SiteMapper siteMapper, SiteSearchRepository siteSearchRepository) {
        this.siteRepository = siteRepository;
        this.siteMapper = siteMapper;
        this.siteSearchRepository = siteSearchRepository;
    }

    /**
     * Save a site.
     *
     * @param siteDTO the entity to save
     * @return the persisted entity
     */
    public SiteDTO save(SiteDTO siteDTO) {
        log.debug("Request to save Site : {}", siteDTO);
        Site site = siteMapper.toEntity(siteDTO);
        site = siteRepository.save(site);
        SiteDTO result = siteMapper.toDto(site);
        siteSearchRepository.save(site);
        return result;
    }

    /**
     * Get all the sites.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SiteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sites");
        return siteRepository.findAll(pageable)
            .map(siteMapper::toDto);
    }

    /**
     * Get all the Site with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<SiteDTO> findAllWithEagerRelationships(Pageable pageable) {
        return siteRepository.findAllWithEagerRelationships(pageable).map(siteMapper::toDto);
    }
    

    /**
     * Get one site by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<SiteDTO> findOne(Long id) {
        log.debug("Request to get Site : {}", id);
        return siteRepository.findOneWithEagerRelationships(id)
            .map(siteMapper::toDto);
    }

    /**
     * Delete the site by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Site : {}", id);
        siteRepository.deleteById(id);
        siteSearchRepository.deleteById(id);
    }

    /**
     * Search for the site corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SiteDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Sites for query {}", query);
        return siteSearchRepository.search(queryStringQuery(query), pageable)
            .map(siteMapper::toDto);
    }
}
