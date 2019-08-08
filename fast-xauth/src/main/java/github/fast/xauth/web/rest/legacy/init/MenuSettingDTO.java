package github.fast.xauth.web.rest.legacy.init;

import java.util.List;

/**
 * @author wanghao
 * @Description 菜单配置DTO
 * @date 2019-08-08 14:34
 */
public class MenuSettingDTO {
    private Long id;
    private List<Long> childIds;
    private List<Long> authIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getChildIds() {
        return childIds;
    }

    public void setChildIds(List<Long> childIds) {
        this.childIds = childIds;
    }

    public List<Long> getAuthIds() {
        return authIds;
    }

    public void setAuthIds(List<Long> authIds) {
        this.authIds = authIds;
    }
}
