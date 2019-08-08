package github.fast.xauth.web.rest.legacy.init;

import github.fast.xauth.domain.*;
import github.fast.xauth.service.dto.*;

import java.util.List;

/**
 * @author wanghao
 * @Description
 * @date 2019-08-07 9:41
 */
public class AuthInitProperties {
    private WorkspaceDTO rootSpace;

    private List<SiteDTO> sites;

    private List<TemplateDTO> templates;

    private List<AuthDTO> auths;

    private List<MenuDTO> menus;

    public WorkspaceDTO getRootSpace() {
        return rootSpace;
    }

    public void setRootSpace(WorkspaceDTO rootSpace) {
        this.rootSpace = rootSpace;
    }

    public List<SiteDTO> getSites() {
        return sites;
    }

    public void setSites(List<SiteDTO> sites) {
        this.sites = sites;
    }

    public List<TemplateDTO> getTemplates() {
        return templates;
    }

    public void setTemplates(List<TemplateDTO> templates) {
        this.templates = templates;
    }

    public List<AuthDTO> getAuths() {
        return auths;
    }

    public void setAuths(List<AuthDTO> auths) {
        this.auths = auths;
    }

    public List<MenuDTO> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuDTO> menus) {
        this.menus = menus;
    }
}
