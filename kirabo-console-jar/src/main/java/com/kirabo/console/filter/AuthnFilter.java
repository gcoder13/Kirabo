package com.kirabo.console.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

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
        System.out.println("testtinnnnnnng");
        PrintWriter out = servletResponse.getWriter();

        AuthnManagerImpl authnManager = new AuthnManagerImpl();
        AUTH_STATUS authnStatus = authnManager.manageAuthn(servletRequest,
                servletResponse);
        switch (authnStatus) {
        case AUTHENTICATED_ALREADY:
            out.println("<br/>You have an authenticated session:<br/><hr/>");
            out.println("<br/>Start Regular Content:<br/><hr/>");
            filterChain.doFilter(servletRequest, servletResponse);
            out.println("<br/><hr/>End Regular Content:<br/>");
            break;
        case AUTHENTICATED_NOW:
            out.println("<br/>Start Regular Content:<br/><hr/>");
            filterChain.doFilter(servletRequest, servletResponse);
            out.println("<br/><hr/>End Regular Content:<br/>");
            break;
        case NOT_AUTHENTICATED:
            out.println("<br/>You are not Authenticated<br/><hr/>");
            out.println("<br/>");
            out.println("<tr>");
            out.println("<input type='text' name='username' value='??'/>");
            out.println("<input type='text' name='password' value='??'/>");
            out.println("<td>");
            out.println("</td>");
            out.println("<td>");
            out.println("</td>");
            out.println("</tr>");
            break;
        default:
            break;
        }
    }
}
