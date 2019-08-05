package github.fast.xauth.web.rest;

import github.fast.xauth.XauthApp;

import github.fast.xauth.domain.Template;
import github.fast.xauth.domain.Workspace;
import github.fast.xauth.domain.Site;
import github.fast.xauth.domain.Auth;
import github.fast.xauth.domain.Menu;
import github.fast.xauth.repository.TemplateRepository;
import github.fast.xauth.repository.search.TemplateSearchRepository;
import github.fast.xauth.service.TemplateService;
import github.fast.xauth.service.dto.TemplateDTO;
import github.fast.xauth.service.mapper.TemplateMapper;
import github.fast.xauth.web.rest.errors.ExceptionTranslator;
import github.fast.xauth.service.dto.TemplateCriteria;
import github.fast.xauth.service.TemplateQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static github.fast.xauth.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import github.fast.xauth.domain.enumeration.ClientType;
/**
 * Test class for the TemplateResource REST controller.
 *
 * @see TemplateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XauthApp.class)
public class TemplateResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ClientType DEFAULT_CLIENT_TYPE = ClientType.PC;
    private static final ClientType UPDATED_CLIENT_TYPE = ClientType.APP;

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final String DEFAULT_EXTMAP = "AAAAAAAAAA";
    private static final String UPDATED_EXTMAP = "BBBBBBBBBB";

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private TemplateMapper templateMapper;

    @Autowired
    private TemplateService templateService;

    /**
     * This repository is mocked in the github.fast.xauth.repository.search test package.
     *
     * @see github.fast.xauth.repository.search.TemplateSearchRepositoryMockConfiguration
     */
    @Autowired
    private TemplateSearchRepository mockTemplateSearchRepository;

    @Autowired
    private TemplateQueryService templateQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restTemplateMockMvc;

    private Template template;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TemplateResource templateResource = new TemplateResource(templateService, templateQueryService);
        this.restTemplateMockMvc = MockMvcBuilders.standaloneSetup(templateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Template createEntity(EntityManager em) {
        Template template = new Template()
            .name(DEFAULT_NAME)
            .clientType(DEFAULT_CLIENT_TYPE)
            .remark(DEFAULT_REMARK)
            .extmap(DEFAULT_EXTMAP);
        return template;
    }

    @Before
    public void initTest() {
        template = createEntity(em);
    }

    @Test
    @Transactional
    public void createTemplate() throws Exception {
        int databaseSizeBeforeCreate = templateRepository.findAll().size();

        // Create the Template
        TemplateDTO templateDTO = templateMapper.toDto(template);
        restTemplateMockMvc.perform(post("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templateDTO)))
            .andExpect(status().isCreated());

        // Validate the Template in the database
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeCreate + 1);
        Template testTemplate = templateList.get(templateList.size() - 1);
        assertThat(testTemplate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTemplate.getClientType()).isEqualTo(DEFAULT_CLIENT_TYPE);
        assertThat(testTemplate.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testTemplate.getExtmap()).isEqualTo(DEFAULT_EXTMAP);

        // Validate the Template in Elasticsearch
        verify(mockTemplateSearchRepository, times(1)).save(testTemplate);
    }

    @Test
    @Transactional
    public void createTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = templateRepository.findAll().size();

        // Create the Template with an existing ID
        template.setId(1L);
        TemplateDTO templateDTO = templateMapper.toDto(template);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTemplateMockMvc.perform(post("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Template in the database
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeCreate);

        // Validate the Template in Elasticsearch
        verify(mockTemplateSearchRepository, times(0)).save(template);
    }

    @Test
    @Transactional
    public void getAllTemplates() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList
        restTemplateMockMvc.perform(get("/api/templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(template.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].clientType").value(hasItem(DEFAULT_CLIENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
            .andExpect(jsonPath("$.[*].extmap").value(hasItem(DEFAULT_EXTMAP.toString())));
    }
    
    @Test
    @Transactional
    public void getTemplate() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get the template
        restTemplateMockMvc.perform(get("/api/templates/{id}", template.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(template.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.clientType").value(DEFAULT_CLIENT_TYPE.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()))
            .andExpect(jsonPath("$.extmap").value(DEFAULT_EXTMAP.toString()));
    }

    @Test
    @Transactional
    public void getAllTemplatesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where name equals to DEFAULT_NAME
        defaultTemplateShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the templateList where name equals to UPDATED_NAME
        defaultTemplateShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTemplatesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTemplateShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the templateList where name equals to UPDATED_NAME
        defaultTemplateShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTemplatesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where name is not null
        defaultTemplateShouldBeFound("name.specified=true");

        // Get all the templateList where name is null
        defaultTemplateShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllTemplatesByClientTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where clientType equals to DEFAULT_CLIENT_TYPE
        defaultTemplateShouldBeFound("clientType.equals=" + DEFAULT_CLIENT_TYPE);

        // Get all the templateList where clientType equals to UPDATED_CLIENT_TYPE
        defaultTemplateShouldNotBeFound("clientType.equals=" + UPDATED_CLIENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllTemplatesByClientTypeIsInShouldWork() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where clientType in DEFAULT_CLIENT_TYPE or UPDATED_CLIENT_TYPE
        defaultTemplateShouldBeFound("clientType.in=" + DEFAULT_CLIENT_TYPE + "," + UPDATED_CLIENT_TYPE);

        // Get all the templateList where clientType equals to UPDATED_CLIENT_TYPE
        defaultTemplateShouldNotBeFound("clientType.in=" + UPDATED_CLIENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllTemplatesByClientTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where clientType is not null
        defaultTemplateShouldBeFound("clientType.specified=true");

        // Get all the templateList where clientType is null
        defaultTemplateShouldNotBeFound("clientType.specified=false");
    }

    @Test
    @Transactional
    public void getAllTemplatesByRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where remark equals to DEFAULT_REMARK
        defaultTemplateShouldBeFound("remark.equals=" + DEFAULT_REMARK);

        // Get all the templateList where remark equals to UPDATED_REMARK
        defaultTemplateShouldNotBeFound("remark.equals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllTemplatesByRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where remark in DEFAULT_REMARK or UPDATED_REMARK
        defaultTemplateShouldBeFound("remark.in=" + DEFAULT_REMARK + "," + UPDATED_REMARK);

        // Get all the templateList where remark equals to UPDATED_REMARK
        defaultTemplateShouldNotBeFound("remark.in=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void getAllTemplatesByRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where remark is not null
        defaultTemplateShouldBeFound("remark.specified=true");

        // Get all the templateList where remark is null
        defaultTemplateShouldNotBeFound("remark.specified=false");
    }

    @Test
    @Transactional
    public void getAllTemplatesByExtmapIsEqualToSomething() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where extmap equals to DEFAULT_EXTMAP
        defaultTemplateShouldBeFound("extmap.equals=" + DEFAULT_EXTMAP);

        // Get all the templateList where extmap equals to UPDATED_EXTMAP
        defaultTemplateShouldNotBeFound("extmap.equals=" + UPDATED_EXTMAP);
    }

    @Test
    @Transactional
    public void getAllTemplatesByExtmapIsInShouldWork() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where extmap in DEFAULT_EXTMAP or UPDATED_EXTMAP
        defaultTemplateShouldBeFound("extmap.in=" + DEFAULT_EXTMAP + "," + UPDATED_EXTMAP);

        // Get all the templateList where extmap equals to UPDATED_EXTMAP
        defaultTemplateShouldNotBeFound("extmap.in=" + UPDATED_EXTMAP);
    }

    @Test
    @Transactional
    public void getAllTemplatesByExtmapIsNullOrNotNull() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList where extmap is not null
        defaultTemplateShouldBeFound("extmap.specified=true");

        // Get all the templateList where extmap is null
        defaultTemplateShouldNotBeFound("extmap.specified=false");
    }

    @Test
    @Transactional
    public void getAllTemplatesByWorkspaceIsEqualToSomething() throws Exception {
        // Initialize the database
        Workspace workspace = WorkspaceResourceIntTest.createEntity(em);
        em.persist(workspace);
        em.flush();
        template.addWorkspace(workspace);
        templateRepository.saveAndFlush(template);
        Long workspaceId = workspace.getId();

        // Get all the templateList where workspace equals to workspaceId
        defaultTemplateShouldBeFound("workspaceId.equals=" + workspaceId);

        // Get all the templateList where workspace equals to workspaceId + 1
        defaultTemplateShouldNotBeFound("workspaceId.equals=" + (workspaceId + 1));
    }


    @Test
    @Transactional
    public void getAllTemplatesBySiteIsEqualToSomething() throws Exception {
        // Initialize the database
        Site site = SiteResourceIntTest.createEntity(em);
        em.persist(site);
        em.flush();
        template.addSite(site);
        templateRepository.saveAndFlush(template);
        Long siteId = site.getId();

        // Get all the templateList where site equals to siteId
        defaultTemplateShouldBeFound("siteId.equals=" + siteId);

        // Get all the templateList where site equals to siteId + 1
        defaultTemplateShouldNotBeFound("siteId.equals=" + (siteId + 1));
    }


    @Test
    @Transactional
    public void getAllTemplatesByAuthIsEqualToSomething() throws Exception {
        // Initialize the database
        Auth auth = AuthResourceIntTest.createEntity(em);
        em.persist(auth);
        em.flush();
        template.addAuth(auth);
        templateRepository.saveAndFlush(template);
        Long authId = auth.getId();

        // Get all the templateList where auth equals to authId
        defaultTemplateShouldBeFound("authId.equals=" + authId);

        // Get all the templateList where auth equals to authId + 1
        defaultTemplateShouldNotBeFound("authId.equals=" + (authId + 1));
    }


    @Test
    @Transactional
    public void getAllTemplatesByMenuIsEqualToSomething() throws Exception {
        // Initialize the database
        Menu menu = MenuResourceIntTest.createEntity(em);
        em.persist(menu);
        em.flush();
        template.addMenu(menu);
        templateRepository.saveAndFlush(template);
        Long menuId = menu.getId();

        // Get all the templateList where menu equals to menuId
        defaultTemplateShouldBeFound("menuId.equals=" + menuId);

        // Get all the templateList where menu equals to menuId + 1
        defaultTemplateShouldNotBeFound("menuId.equals=" + (menuId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTemplateShouldBeFound(String filter) throws Exception {
        restTemplateMockMvc.perform(get("/api/templates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(template.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].clientType").value(hasItem(DEFAULT_CLIENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].extmap").value(hasItem(DEFAULT_EXTMAP)));

        // Check, that the count call also returns 1
        restTemplateMockMvc.perform(get("/api/templates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTemplateShouldNotBeFound(String filter) throws Exception {
        restTemplateMockMvc.perform(get("/api/templates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTemplateMockMvc.perform(get("/api/templates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTemplate() throws Exception {
        // Get the template
        restTemplateMockMvc.perform(get("/api/templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTemplate() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        int databaseSizeBeforeUpdate = templateRepository.findAll().size();

        // Update the template
        Template updatedTemplate = templateRepository.findById(template.getId()).get();
        // Disconnect from session so that the updates on updatedTemplate are not directly saved in db
        em.detach(updatedTemplate);
        updatedTemplate
            .name(UPDATED_NAME)
            .clientType(UPDATED_CLIENT_TYPE)
            .remark(UPDATED_REMARK)
            .extmap(UPDATED_EXTMAP);
        TemplateDTO templateDTO = templateMapper.toDto(updatedTemplate);

        restTemplateMockMvc.perform(put("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templateDTO)))
            .andExpect(status().isOk());

        // Validate the Template in the database
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeUpdate);
        Template testTemplate = templateList.get(templateList.size() - 1);
        assertThat(testTemplate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTemplate.getClientType()).isEqualTo(UPDATED_CLIENT_TYPE);
        assertThat(testTemplate.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testTemplate.getExtmap()).isEqualTo(UPDATED_EXTMAP);

        // Validate the Template in Elasticsearch
        verify(mockTemplateSearchRepository, times(1)).save(testTemplate);
    }

    @Test
    @Transactional
    public void updateNonExistingTemplate() throws Exception {
        int databaseSizeBeforeUpdate = templateRepository.findAll().size();

        // Create the Template
        TemplateDTO templateDTO = templateMapper.toDto(template);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTemplateMockMvc.perform(put("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(templateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Template in the database
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Template in Elasticsearch
        verify(mockTemplateSearchRepository, times(0)).save(template);
    }

    @Test
    @Transactional
    public void deleteTemplate() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        int databaseSizeBeforeDelete = templateRepository.findAll().size();

        // Delete the template
        restTemplateMockMvc.perform(delete("/api/templates/{id}", template.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Template in Elasticsearch
        verify(mockTemplateSearchRepository, times(1)).deleteById(template.getId());
    }

    @Test
    @Transactional
    public void searchTemplate() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);
        when(mockTemplateSearchRepository.search(queryStringQuery("id:" + template.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(template), PageRequest.of(0, 1), 1));
        // Search the template
        restTemplateMockMvc.perform(get("/api/_search/templates?query=id:" + template.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(template.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].clientType").value(hasItem(DEFAULT_CLIENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].extmap").value(hasItem(DEFAULT_EXTMAP)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Template.class);
        Template template1 = new Template();
        template1.setId(1L);
        Template template2 = new Template();
        template2.setId(template1.getId());
        assertThat(template1).isEqualTo(template2);
        template2.setId(2L);
        assertThat(template1).isNotEqualTo(template2);
        template1.setId(null);
        assertThat(template1).isNotEqualTo(template2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TemplateDTO.class);
        TemplateDTO templateDTO1 = new TemplateDTO();
        templateDTO1.setId(1L);
        TemplateDTO templateDTO2 = new TemplateDTO();
        assertThat(templateDTO1).isNotEqualTo(templateDTO2);
        templateDTO2.setId(templateDTO1.getId());
        assertThat(templateDTO1).isEqualTo(templateDTO2);
        templateDTO2.setId(2L);
        assertThat(templateDTO1).isNotEqualTo(templateDTO2);
        templateDTO1.setId(null);
        assertThat(templateDTO1).isNotEqualTo(templateDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(templateMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(templateMapper.fromId(null)).isNull();
    }
}
