package github.fast.xauth.service.dto;

import java.io.Serializable;
import java.util.Objects;
import github.fast.xauth.domain.enumeration.ClientType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the Template entity. This class is used in TemplateResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /templates?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TemplateCriteria implements Serializable {
    /**
     * Class for filtering ClientType
     */
    public static class ClientTypeFilter extends Filter<ClientType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private ClientTypeFilter clientType;

    private StringFilter remark;

    private StringFilter extmap;

    private LongFilter workspaceId;

    private LongFilter siteId;

    private LongFilter authId;

    private LongFilter menuId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public ClientTypeFilter getClientType() {
        return clientType;
    }

    public void setClientType(ClientTypeFilter clientType) {
        this.clientType = clientType;
    }

    public StringFilter getRemark() {
        return remark;
    }

    public void setRemark(StringFilter remark) {
        this.remark = remark;
    }

    public StringFilter getExtmap() {
        return extmap;
    }

    public void setExtmap(StringFilter extmap) {
        this.extmap = extmap;
    }

    public LongFilter getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(LongFilter workspaceId) {
        this.workspaceId = workspaceId;
    }

    public LongFilter getSiteId() {
        return siteId;
    }

    public void setSiteId(LongFilter siteId) {
        this.siteId = siteId;
    }

    public LongFilter getAuthId() {
        return authId;
    }

    public void setAuthId(LongFilter authId) {
        this.authId = authId;
    }

    public LongFilter getMenuId() {
        return menuId;
    }

    public void setMenuId(LongFilter menuId) {
        this.menuId = menuId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TemplateCriteria that = (TemplateCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(clientType, that.clientType) &&
            Objects.equals(remark, that.remark) &&
            Objects.equals(extmap, that.extmap) &&
            Objects.equals(workspaceId, that.workspaceId) &&
            Objects.equals(siteId, that.siteId) &&
            Objects.equals(authId, that.authId) &&
            Objects.equals(menuId, that.menuId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        clientType,
        remark,
        extmap,
        workspaceId,
        siteId,
        authId,
        menuId
        );
    }

    @Override
    public String toString() {
        return "TemplateCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (clientType != null ? "clientType=" + clientType + ", " : "") +
                (remark != null ? "remark=" + remark + ", " : "") +
                (extmap != null ? "extmap=" + extmap + ", " : "") +
                (workspaceId != null ? "workspaceId=" + workspaceId + ", " : "") +
                (siteId != null ? "siteId=" + siteId + ", " : "") +
                (authId != null ? "authId=" + authId + ", " : "") +
                (menuId != null ? "menuId=" + menuId + ", " : "") +
            "}";
    }

}
