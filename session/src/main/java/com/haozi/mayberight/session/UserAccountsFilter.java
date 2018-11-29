package com.haozi.mayberight.session;

import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.session.web.http.HttpSessionManager;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @author wanghao
 * @Description
 * @date 2018-11-27 13:45
 */
public class UserAccountsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        // tag::HttpSessionManager[]
        HttpSessionManager sessionManager = (HttpSessionManager) httpRequest
                .getAttribute(HttpSessionManager.class.getName());
        // end::HttpSessionManager[]
        SessionRepository<Session> repo = (SessionRepository<Session>) httpRequest
                .getAttribute(SessionRepository.class.getName());

//        String currentSessionAlias = sessionManager.getCurrentSessionAlias(httpRequest);
//        Map<String, String> sessionIds = sessionManager.getSessionIds(httpRequest);

        String contextPath = httpRequest.getContextPath();
//        System.out.println("currentSessionAlias: " + currentSessionAlias);
        System.out.println("contextPath: " + contextPath);
//        for (Map.Entry<String, String> entry : sessionIds.entrySet()) {
//            String alias = entry.getKey();
//            String sessionId = entry.getValue();
//
//            Session session = repo.findById(sessionId);
//            if (session == null) {
//                continue;
//            }
//
//            System.out.println("alias: " + alias);
//            System.out.println("sessionId: " + sessionId);
//        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
