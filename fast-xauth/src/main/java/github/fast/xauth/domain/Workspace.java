package github.fast.xauth.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Workspace.
 */
@Entity
@Table(name = "workspace")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "workspace")
public class Workspace implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "remark")
    private String remark;

    @Column(name = "extmap")
    private String extmap;

    @ManyToOne
    @JsonIgnoreProperties("workspaces")
    private Workspace parent;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "workspace_site",
               joinColumns = @JoinColumn(name = "workspace_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "site_id", referencedColumnName = "id"))
    private Set<Site> sites = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "workspace_template",
               joinColumns = @JoinColumn(name = "workspace_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "template_id", referencedColumnName = "id"))
    private Set<Template> templates = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Workspace name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public Workspace remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExtmap() {
        return extmap;
    }

    public Workspace extmap(String extmap) {
        this.extmap = extmap;
        return this;
    }

    public void setExtmap(String extmap) {
        this.extmap = extmap;
    }

    public Workspace getParent() {
        return parent;
    }

    public Workspace parent(Workspace workspace) {
        this.parent = workspace;
        return this;
    }

    public void setParent(Workspace workspace) {
        this.parent = workspace;
    }

    public Set<Site> getSites() {
        return sites;
    }

    public Workspace sites(Set<Site> sites) {
        this.sites = sites;
        return this;
    }

    public Workspace addSite(Site site) {
        this.sites.add(site);
        site.getWorkspaces().add(this);
        return this;
    }

    public Workspace removeSite(Site site) {
        this.sites.remove(site);
        site.getWorkspaces().remove(this);
        return this;
    }

    public void setSites(Set<Site> sites) {
        this.sites = sites;
    }

    public Set<Template> getTemplates() {
        return templates;
    }

    public Workspace templates(Set<Template> templates) {
        this.templates = templates;
        return this;
    }

    public Workspace addTemplate(Template template) {
        this.templates.add(template);
        template.getWorkspaces().add(this);
        return this;
    }

    public Workspace removeTemplate(Template template) {
        this.templates.remove(template);
        template.getWorkspaces().remove(this);
        return this;
    }

    public void setTemplates(Set<Template> templates) {
        this.templates = templates;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Workspace workspace = (Workspace) o;
        if (workspace.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), workspace.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Workspace{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", remark='" + getRemark() + "'" +
            ", extmap='" + getExtmap() + "'" +
            "}";
    }
}
