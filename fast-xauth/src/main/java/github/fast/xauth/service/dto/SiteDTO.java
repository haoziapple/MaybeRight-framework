package github.fast.xauth.service.dto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Site entity.
 */
public class SiteDTO implements Serializable {

    private Long id;

    private String name;

    private String homeUrl;

    private String remark;

    private String extmap;


    private Set<RoleDTO> roles = new HashSet<>();

    private Set<TemplateDTO> templates = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHomeUrl() {
        return homeUrl;
    }

    public void setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getExtmap() {
        return extmap;
    }

    public void setExtmap(String extmap) {
        this.extmap = extmap;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }

    public Set<TemplateDTO> getTemplates() {
        return templates;
    }

    public void setTemplates(Set<TemplateDTO> templates) {
        this.templates = templates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SiteDTO siteDTO = (SiteDTO) o;
        if (siteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), siteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SiteDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", homeUrl='" + getHomeUrl() + "'" +
            ", remark='" + getRemark() + "'" +
            ", extmap='" + getExtmap() + "'" +
            "}";
    }
}
