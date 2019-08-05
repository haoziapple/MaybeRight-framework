package github.fast.xauth.web.rest;
import github.fast.xauth.service.DepartmentService;
import github.fast.xauth.web.rest.errors.BadRequestAlertException;
import github.fast.xauth.web.rest.util.HeaderUtil;
import github.fast.xauth.web.rest.util.PaginationUtil;
import github.fast.xauth.service.dto.DepartmentDTO;
import github.fast.xauth.service.dto.DepartmentCriteria;
import github.fast.xauth.service.DepartmentQueryService;
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
 * REST controller for managing Department.
 */
@RestController
@RequestMapping("/api")
public class DepartmentResource {

    private final Logger log = LoggerFactory.getLogger(DepartmentResource.class);

    private static final String ENTITY_NAME = "xauthDepartment";

    private final DepartmentService departmentService;

    private final DepartmentQueryService departmentQueryService;

    public DepartmentResource(DepartmentService departmentService, DepartmentQueryService departmentQueryService) {
        this.departmentService = departmentService;
        this.departmentQueryService = departmentQueryService;
    }

    /**
     * POST  /departments : Create a new department.
     *
     * @param departmentDTO the departmentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new departmentDTO, or with status 400 (Bad Request) if the department has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/departments")
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody DepartmentDTO departmentDTO) throws URISyntaxException {
        log.debug("REST request to save Department : {}", departmentDTO);
        if (departmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new department cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DepartmentDTO result = departmentService.save(departmentDTO);
        return ResponseEntity.created(new URI("/api/departments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /departments : Updates an existing department.
     *
     * @param departmentDTO the departmentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated departmentDTO,
     * or with status 400 (Bad Request) if the departmentDTO is not valid,
     * or with status 500 (Internal Server Error) if the departmentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/departments")
    public ResponseEntity<DepartmentDTO> updateDepartment(@RequestBody DepartmentDTO departmentDTO) throws URISyntaxException {
        log.debug("REST request to update Department : {}", departmentDTO);
        if (departmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DepartmentDTO result = departmentService.save(departmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, departmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /departments : get all the departments.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of departments in body
     */
    @GetMapping("/departments")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments(DepartmentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Departments by criteria: {}", criteria);
        Page<DepartmentDTO> page = departmentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/departments");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /departments/count : count all the departments.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/departments/count")
    public ResponseEntity<Long> countDepartments(DepartmentCriteria criteria) {
        log.debug("REST request to count Departments by criteria: {}", criteria);
        return ResponseEntity.ok().body(departmentQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /departments/:id : get the "id" department.
     *
     * @param id the id of the departmentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the departmentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/departments/{id}")
    public ResponseEntity<DepartmentDTO> getDepartment(@PathVariable Long id) {
        log.debug("REST request to get Department : {}", id);
        Optional<DepartmentDTO> departmentDTO = departmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(departmentDTO);
    }

    /**
     * DELETE  /departments/:id : delete the "id" department.
     *
     * @param id the id of the departmentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/departments/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        log.debug("REST request to delete Department : {}", id);
        departmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/departments?query=:query : search for the department corresponding
     * to the query.
     *
     * @param query the query of the department search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/departments")
    public ResponseEntity<List<DepartmentDTO>> searchDepartments(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Departments for query {}", query);
        Page<DepartmentDTO> page = departmentService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/departments");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
