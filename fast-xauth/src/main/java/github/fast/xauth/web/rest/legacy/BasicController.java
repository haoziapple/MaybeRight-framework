package github.fast.xauth.web.rest.legacy;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wanghao
 * @Description
 * @date 2019-08-06 10:34
 */
public class BasicController {
    public String getUserId(HttpServletRequest request) {
        String userId = request.getHeader("x-user-id");
        return userId;
    }

}
