package github.fast.xauth.service;

import github.fast.xauth.domain.Menu;
import github.fast.xauth.repository.MenuRepository;
import github.fast.xauth.repository.search.MenuSearchRepository;
import github.fast.xauth.service.dto.MenuDTO;
import github.fast.xauth.service.mapper.MenuMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Menu.
 */
@Service
@Transactional
public class MenuService {

    private final Logger log = LoggerFactory.getLogger(MenuService.class);

    private final MenuRepository menuRepository;

    private final MenuMapper menuMapper;

    private final MenuSearchRepository menuSearchRepository;

    public MenuService(MenuRepository menuRepository, MenuMapper menuMapper, MenuSearchRepository menuSearchRepository) {
        this.menuRepository = menuRepository;
        this.menuMapper = menuMapper;
        this.menuSearchRepository = menuSearchRepository;
    }

    /**
     * Save a menu.
     *
     * @param menuDTO the entity to save
     * @return the persisted entity
     */
    public MenuDTO save(MenuDTO menuDTO) {
        log.debug("Request to save Menu : {}", menuDTO);
        Menu menu = menuMapper.toEntity(menuDTO);
        menu = menuRepository.save(menu);
        MenuDTO result = menuMapper.toDto(menu);
        menuSearchRepository.save(menu);
        return result;
    }

    /**
     * Get all the menus.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MenuDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Menus");
        return menuRepository.findAll(pageable)
            .map(menuMapper::toDto);
    }

    /**
     * Get all the Menu with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<MenuDTO> findAllWithEagerRelationships(Pageable pageable) {
        return menuRepository.findAllWithEagerRelationships(pageable).map(menuMapper::toDto);
    }
    

    /**
     * Get one menu by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MenuDTO> findOne(Long id) {
        log.debug("Request to get Menu : {}", id);
        return menuRepository.findOneWithEagerRelationships(id)
            .map(menuMapper::toDto);
    }

    /**
     * Delete the menu by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Menu : {}", id);
        menuRepository.deleteById(id);
        menuSearchRepository.deleteById(id);
    }

    /**
     * Search for the menu corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MenuDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Menus for query {}", query);
        return menuSearchRepository.search(queryStringQuery(query), pageable)
            .map(menuMapper::toDto);
    }
}
