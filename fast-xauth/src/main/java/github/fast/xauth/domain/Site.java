package github.fast.xauth.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Site.
 */
@Entity
@Table(name = "site")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "site")
public class Site implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "home_url")
    private String homeUrl;

    @Column(name = "remark")
    private String remark;

    @Column(name = "extmap")
    private String extmap;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "site_role",
               joinColumns = @JoinColumn(name = "site_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "site_template",
               joinColumns = @JoinColumn(name = "site_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "template_id", referencedColumnName = "id"))
    private Set<Template> templates = new HashSet<>();

    @ManyToMany(mappedBy = "sites")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Workspace> workspaces = new HashSet<>();

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

    public Site name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHomeUrl() {
        return homeUrl;
    }

    public Site homeUrl(String homeUrl) {
        this.homeUrl = homeUrl;
        return this;
    }

    public void setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl;
    }

    public String getRemark() {
        return remark;
    }

    public Site remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExtmap() {
        return extmap;
    }

    public Site extmap(String extmap) {
        this.extmap = extmap;
        return this;
    }

    public void setExtmap(String extmap) {
        this.extmap = extmap;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Site roles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Site addRole(Role role) {
        this.roles.add(role);
        role.getSites().add(this);
        return this;
    }

    public Site removeRole(Role role) {
        this.roles.remove(role);
        role.getSites().remove(this);
        return this;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Template> getTemplates() {
        return templates;
    }

    public Site templates(Set<Template> templates) {
        this.templates = templates;
        return this;
    }

    public Site addTemplate(Template template) {
        this.templates.add(template);
        template.getSites().add(this);
        return this;
    }

    public Site removeTemplate(Template template) {
        this.templates.remove(template);
        template.getSites().remove(this);
        return this;
    }

    public void setTemplates(Set<Template> templates) {
        this.templates = templates;
    }

    public Set<Workspace> getWorkspaces() {
        return workspaces;
    }

    public Site workspaces(Set<Workspace> workspaces) {
        this.workspaces = workspaces;
        return this;
    }

    public Site addWorkspace(Workspace workspace) {
        this.workspaces.add(workspace);
        workspace.getSites().add(this);
        return this;
    }

    public Site removeWorkspace(Workspace workspace) {
        this.workspaces.remove(workspace);
        workspace.getSites().remove(this);
        return this;
    }

    public void setWorkspaces(Set<Workspace> workspaces) {
        this.workspaces = workspaces;
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
        Site site = (Site) o;
        if (site.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), site.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Site{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", homeUrl='" + getHomeUrl() + "'" +
            ", remark='" + getRemark() + "'" +
            ", extmap='" + getExtmap() + "'" +
            "}";
    }
}
