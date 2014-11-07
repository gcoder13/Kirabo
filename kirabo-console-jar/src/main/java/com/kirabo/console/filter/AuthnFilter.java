package com.kirabo.console.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kirabo.console.authn.AuthnManagerImpl;
import com.kirabo.console.authn.AuthnManagerImpl.AUTH_STATUS;

public class AuthnFilter implements Filter {

    FilterConfig filterConfig = null;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest servletRequest,
            ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        servletResponse.setContentType("text/html");
        System.out.println("Entering Authentication Filter 001");
        AuthnManagerImpl authnManager = new AuthnManagerImpl();
        AUTH_STATUS authnStatus = authnManager.manageAuthn(servletRequest,
                servletResponse);
        switch (authnStatus) {
        case AUTHENTICATED_ALREADY:
            filterChain.doFilter(servletRequest, servletResponse);
            break;
        case AUTHENTICATED_NOW:
            ((HttpServletResponse) servletResponse).sendRedirect("index.jsp");
            return;
        case LOGOUT_REQUEST:
            ((HttpServletRequest) servletRequest).getRequestDispatcher(
                    "logout.jsp").forward(servletRequest, servletResponse);
            break;
        case LOGIN_REQUEST:
            ((HttpServletRequest) servletRequest).getRequestDispatcher(
                    "login.jsp").forward(servletRequest, servletResponse);
            break;
        case NOT_AUTHENTICATED:
            ((HttpServletRequest) servletRequest).getRequestDispatcher(
                    "notAuthenticated.jsp").forward(servletRequest,
                    servletResponse);
            break;
        default:
            break;
        }
    }
}
