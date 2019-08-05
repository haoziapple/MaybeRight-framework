package github.fast.xauth.web.rest;
import github.fast.xauth.service.SiteService;
import github.fast.xauth.web.rest.errors.BadRequestAlertException;
import github.fast.xauth.web.rest.util.HeaderUtil;
import github.fast.xauth.web.rest.util.PaginationUtil;
import github.fast.xauth.service.dto.SiteDTO;
import github.fast.xauth.service.dto.SiteCriteria;
import github.fast.xauth.service.SiteQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Site.
 */
@RestController
@RequestMapping("/api")
public class SiteResource {

    private final Logger log = LoggerFactory.getLogger(SiteResource.class);

    private static final String ENTITY_NAME = "xauthSite";

    private final SiteService siteService;

    private final SiteQueryService siteQueryService;

    public SiteResource(SiteService siteService, SiteQueryService siteQueryService) {
        this.siteService = siteService;
        this.siteQueryService = siteQueryService;
    }

    /**
     * POST  /sites : Create a new site.
     *
     * @param siteDTO the siteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new siteDTO, or with status 400 (Bad Request) if the site has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sites")
    public ResponseEntity<SiteDTO> createSite(@RequestBody SiteDTO siteDTO) throws URISyntaxException {
        log.debug("REST request to save Site : {}", siteDTO);
        if (siteDTO.getId() != null) {
            throw new BadRequestAlertException("A new site cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SiteDTO result = siteService.save(siteDTO);
        return ResponseEntity.created(new URI("/api/sites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sites : Updates an existing site.
     *
     * @param siteDTO the siteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated siteDTO,
     * or with status 400 (Bad Request) if the siteDTO is not valid,
     * or with status 500 (Internal Server Error) if the siteDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sites")
    public ResponseEntity<SiteDTO> updateSite(@RequestBody SiteDTO siteDTO) throws URISyntaxException {
        log.debug("REST request to update Site : {}", siteDTO);
        if (siteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SiteDTO result = siteService.save(siteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, siteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sites : get all the sites.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of sites in body
     */
    @GetMapping("/sites")
    public ResponseEntity<List<SiteDTO>> getAllSites(SiteCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Sites by criteria: {}", criteria);
        Page<SiteDTO> page = siteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sites");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /sites/count : count all the sites.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/sites/count")
    public ResponseEntity<Long> countSites(SiteCriteria criteria) {
        log.debug("REST request to count Sites by criteria: {}", criteria);
        return ResponseEntity.ok().body(siteQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /sites/:id : get the "id" site.
     *
     * @param id the id of the siteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the siteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sites/{id}")
    public ResponseEntity<SiteDTO> getSite(@PathVariable Long id) {
        log.debug("REST request to get Site : {}", id);
        Optional<SiteDTO> siteDTO = siteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(siteDTO);
    }

    /**
     * DELETE  /sites/:id : delete the "id" site.
     *
     * @param id the id of the siteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sites/{id}")
    public ResponseEntity<Void> deleteSite(@PathVariable Long id) {
        log.debug("REST request to delete Site : {}", id);
        siteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/sites?query=:query : search for the site corresponding
     * to the query.
     *
     * @param query the query of the site search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/sites")
    public ResponseEntity<List<SiteDTO>> searchSites(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Sites for query {}", query);
        Page<SiteDTO> page = siteService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/sites");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
