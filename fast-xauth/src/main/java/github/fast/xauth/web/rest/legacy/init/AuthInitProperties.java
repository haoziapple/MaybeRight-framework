package github.fast.xauth.web.rest.legacy.init;

import github.fast.xauth.domain.Auth;
import github.fast.xauth.domain.Menu;
import github.fast.xauth.domain.Template;
import github.fast.xauth.domain.Workspace;

import java.util.List;

/**
 * @author wanghao
 * @Description
 * @date 2019-08-07 9:41
 */
public class AuthInitProperties {
    private Workspace rootSpace;

    private List<Template> templates;

    private List<Auth> auths;

    private List<Menu> menus;

    public Workspace getRootSpace() {
        return rootSpace;
    }

    public void setRootSpace(Workspace rootSpace) {
        this.rootSpace = rootSpace;
    }

    public List<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
    }

    public List<Auth> getAuths() {
        return auths;
    }

    public void setAuths(List<Auth> auths) {
        this.auths = auths;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }
}
