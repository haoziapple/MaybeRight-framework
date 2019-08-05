package github.fast.xauth.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import github.fast.xauth.domain.enumeration.RoleType;

/**
 * A Role.
 */
@Entity
@Table(name = "role")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "role")
public class Role implements Serializable {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "role_type")
    private RoleType roleType;

    @ManyToOne
    @JsonIgnoreProperties("roles")
    private Workspace workspace;

    @ManyToMany(mappedBy = "roles")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Site> sites = new HashSet<>();

    @ManyToMany(mappedBy = "roles")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Auth> auths = new HashSet<>();

    @ManyToMany(mappedBy = "roles")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Menu> menus = new HashSet<>();

    @ManyToMany(mappedBy = "roles")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Profile> profiles = new HashSet<>();

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

    public Role name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public Role remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExtmap() {
        return extmap;
    }

    public Role extmap(String extmap) {
        this.extmap = extmap;
        return this;
    }

    public void setExtmap(String extmap) {
        this.extmap = extmap;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public Role roleType(RoleType roleType) {
        this.roleType = roleType;
        return this;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public Role workspace(Workspace workspace) {
        this.workspace = workspace;
        return this;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public Set<Site> getSites() {
        return sites;
    }

    public Role sites(Set<Site> sites) {
        this.sites = sites;
        return this;
    }

    public Role addSite(Site site) {
        this.sites.add(site);
        site.getRoles().add(this);
        return this;
    }

    public Role removeSite(Site site) {
        this.sites.remove(site);
        site.getRoles().remove(this);
        return this;
    }

    public void setSites(Set<Site> sites) {
        this.sites = sites;
    }

    public Set<Auth> getAuths() {
        return auths;
    }

    public Role auths(Set<Auth> auths) {
        this.auths = auths;
        return this;
    }

    public Role addAuth(Auth auth) {
        this.auths.add(auth);
        auth.getRoles().add(this);
        return this;
    }

    public Role removeAuth(Auth auth) {
        this.auths.remove(auth);
        auth.getRoles().remove(this);
        return this;
    }

    public void setAuths(Set<Auth> auths) {
        this.auths = auths;
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public Role menus(Set<Menu> menus) {
        this.menus = menus;
        return this;
    }

    public Role addMenu(Menu menu) {
        this.menus.add(menu);
        menu.getRoles().add(this);
        return this;
    }

    public Role removeMenu(Menu menu) {
        this.menus.remove(menu);
        menu.getRoles().remove(this);
        return this;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    public Set<Profile> getProfiles() {
        return profiles;
    }

    public Role profiles(Set<Profile> profiles) {
        this.profiles = profiles;
        return this;
    }

    public Role addProfile(Profile profile) {
        this.profiles.add(profile);
        profile.getRoles().add(this);
        return this;
    }

    public Role removeProfile(Profile profile) {
        this.profiles.remove(profile);
        profile.getRoles().remove(this);
        return this;
    }

    public void setProfiles(Set<Profile> profiles) {
        this.profiles = profiles;
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
        Role role = (Role) o;
        if (role.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), role.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Role{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", remark='" + getRemark() + "'" +
            ", extmap='" + getExtmap() + "'" +
            ", roleType='" + getRoleType() + "'" +
            "}";
    }
}
