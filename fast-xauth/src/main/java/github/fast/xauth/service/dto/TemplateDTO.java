package github.fast.xauth.service.dto;
import java.io.Serializable;
import java.util.Objects;
import github.fast.xauth.domain.enumeration.ClientType;

/**
 * A DTO for the Template entity.
 */
public class TemplateDTO implements Serializable {

    private Long id;

    private String name;

    private ClientType clientType;

    private String remark;

    private String extmap;


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

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TemplateDTO templateDTO = (TemplateDTO) o;
        if (templateDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), templateDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TemplateDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", clientType='" + getClientType() + "'" +
            ", remark='" + getRemark() + "'" +
            ", extmap='" + getExtmap() + "'" +
            "}";
    }
}
