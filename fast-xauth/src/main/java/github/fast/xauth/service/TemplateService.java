package github.fast.xauth.service;

import github.fast.xauth.domain.Template;
import github.fast.xauth.repository.TemplateRepository;
import github.fast.xauth.repository.search.TemplateSearchRepository;
import github.fast.xauth.service.dto.TemplateDTO;
import github.fast.xauth.service.mapper.TemplateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Template.
 */
@Service
@Transactional
public class TemplateService {

    private final Logger log = LoggerFactory.getLogger(TemplateService.class);

    private final TemplateRepository templateRepository;

    private final TemplateMapper templateMapper;

    private final TemplateSearchRepository templateSearchRepository;

    public TemplateService(TemplateRepository templateRepository, TemplateMapper templateMapper, TemplateSearchRepository templateSearchRepository) {
        this.templateRepository = templateRepository;
        this.templateMapper = templateMapper;
        this.templateSearchRepository = templateSearchRepository;
    }

    /**
     * Save a template.
     *
     * @param templateDTO the entity to save
     * @return the persisted entity
     */
    public TemplateDTO save(TemplateDTO templateDTO) {
        log.debug("Request to save Template : {}", templateDTO);
        Template template = templateMapper.toEntity(templateDTO);
        template = templateRepository.save(template);
        TemplateDTO result = templateMapper.toDto(template);
        templateSearchRepository.save(template);
        return result;
    }

    /**
     * Get all the templates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TemplateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Templates");
        return templateRepository.findAll(pageable)
            .map(templateMapper::toDto);
    }


    /**
     * Get one template by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TemplateDTO> findOne(Long id) {
        log.debug("Request to get Template : {}", id);
        return templateRepository.findById(id)
            .map(templateMapper::toDto);
    }

    /**
     * Delete the template by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Template : {}", id);
        templateRepository.deleteById(id);
        templateSearchRepository.deleteById(id);
    }

    /**
     * Search for the template corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TemplateDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Templates for query {}", query);
        return templateSearchRepository.search(queryStringQuery(query), pageable)
            .map(templateMapper::toDto);
    }
}
