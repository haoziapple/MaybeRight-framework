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

import github.fast.xauth.domain.enumeration.ClientType;

/**
 * A Template.
 */
@Entity
@Table(name = "template")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "template")
public class Template implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "client_type")
    private ClientType clientType;

    @Column(name = "remark")
    private String remark;

    @Column(name = "extmap")
    private String extmap;

    @ManyToMany(mappedBy = "templates")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Workspace> workspaces = new HashSet<>();

    @ManyToMany(mappedBy = "templates")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Site> sites = new HashSet<>();

    @ManyToMany(mappedBy = "templates")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Auth> auths = new HashSet<>();

    @ManyToMany(mappedBy = "templates")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Menu> menus = new HashSet<>();

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

    public Template name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public Template clientType(ClientType clientType) {
        this.clientType = clientType;
        return this;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public String getRemark() {
        return remark;
    }

    public Template remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExtmap() {
        return extmap;
    }

    public Template extmap(String extmap) {
        this.extmap = extmap;
        return this;
    }

    public void setExtmap(String extmap) {
        this.extmap = extmap;
    }

    public Set<Workspace> getWorkspaces() {
        return workspaces;
    }

    public Template workspaces(Set<Workspace> workspaces) {
        this.workspaces = workspaces;
        return this;
    }

    public Template addWorkspace(Workspace workspace) {
        this.workspaces.add(workspace);
        workspace.getTemplates().add(this);
        return this;
    }

    public Template removeWorkspace(Workspace workspace) {
        this.workspaces.remove(workspace);
        workspace.getTemplates().remove(this);
        return this;
    }

    public void setWorkspaces(Set<Workspace> workspaces) {
        this.workspaces = workspaces;
    }

    public Set<Site> getSites() {
        return sites;
    }

    public Template sites(Set<Site> sites) {
        this.sites = sites;
        return this;
    }

    public Template addSite(Site site) {
        this.sites.add(site);
        site.getTemplates().add(this);
        return this;
    }

    public Template removeSite(Site site) {
        this.sites.remove(site);
        site.getTemplates().remove(this);
        return this;
    }

    public void setSites(Set<Site> sites) {
        this.sites = sites;
    }

    public Set<Auth> getAuths() {
        return auths;
    }

    public Template auths(Set<Auth> auths) {
        this.auths = auths;
        return this;
    }

    public Template addAuth(Auth auth) {
        this.auths.add(auth);
        auth.getTemplates().add(this);
        return this;
    }

    public Template removeAuth(Auth auth) {
        this.auths.remove(auth);
        auth.getTemplates().remove(this);
        return this;
    }

    public void setAuths(Set<Auth> auths) {
        this.auths = auths;
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public Template menus(Set<Menu> menus) {
        this.menus = menus;
        return this;
    }

    public Template addMenu(Menu menu) {
        this.menus.add(menu);
        menu.getTemplates().add(this);
        return this;
    }

    public Template removeMenu(Menu menu) {
        this.menus.remove(menu);
        menu.getTemplates().remove(this);
        return this;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
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
        Template template = (Template) o;
        if (template.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), template.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Template{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", clientType='" + getClientType() + "'" +
            ", remark='" + getRemark() + "'" +
            ", extmap='" + getExtmap() + "'" +
            "}";
    }
}
