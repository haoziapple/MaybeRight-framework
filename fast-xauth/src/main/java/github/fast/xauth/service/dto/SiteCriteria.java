package github.fast.xauth.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the Site entity. This class is used in SiteResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /sites?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SiteCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter homeUrl;

    private StringFilter remark;

    private StringFilter extmap;

    private LongFilter roleId;

    private LongFilter templateId;

    private LongFilter workspaceId;

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

    public StringFilter getHomeUrl() {
        return homeUrl;
    }

    public void setHomeUrl(StringFilter homeUrl) {
        this.homeUrl = homeUrl;
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

    public LongFilter getRoleId() {
        return roleId;
    }

    public void setRoleId(LongFilter roleId) {
        this.roleId = roleId;
    }

    public LongFilter getTemplateId() {
        return templateId;
    }

    public void setTemplateId(LongFilter templateId) {
        this.templateId = templateId;
    }

    public LongFilter getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(LongFilter workspaceId) {
        this.workspaceId = workspaceId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SiteCriteria that = (SiteCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(homeUrl, that.homeUrl) &&
            Objects.equals(remark, that.remark) &&
            Objects.equals(extmap, that.extmap) &&
            Objects.equals(roleId, that.roleId) &&
            Objects.equals(templateId, that.templateId) &&
            Objects.equals(workspaceId, that.workspaceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        homeUrl,
        remark,
        extmap,
        roleId,
        templateId,
        workspaceId
        );
    }

    @Override
    public String toString() {
        return "SiteCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (homeUrl != null ? "homeUrl=" + homeUrl + ", " : "") +
                (remark != null ? "remark=" + remark + ", " : "") +
                (extmap != null ? "extmap=" + extmap + ", " : "") +
                (roleId != null ? "roleId=" + roleId + ", " : "") +
                (templateId != null ? "templateId=" + templateId + ", " : "") +
                (workspaceId != null ? "workspaceId=" + workspaceId + ", " : "") +
            "}";
    }

}
