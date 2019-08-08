package github.fast.xauth.web.rest.legacy.init;

import java.util.List;

/**
 * @author wanghao
 * @Description 权限配置DTO
 * @date 2019-08-08 14:38
 */
public class TemplateSettingDTO {
    private Long id;

    private List<Long> workspaceIds;

    private List<Long> siteIds;

    private List<Long> menuIds;

    private List<Long> authIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getWorkspaceIds() {
        return workspaceIds;
    }

    public void setWorkspaceIds(List<Long> workspaceIds) {
        this.workspaceIds = workspaceIds;
    }

    public List<Long> getSiteIds() {
        return siteIds;
    }

    public void setSiteIds(List<Long> siteIds) {
        this.siteIds = siteIds;
    }

    public List<Long> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<Long> menuIds) {
        this.menuIds = menuIds;
    }

    public List<Long> getAuthIds() {
        return authIds;
    }

    public void setAuthIds(List<Long> authIds) {
        this.authIds = authIds;
    }
}
