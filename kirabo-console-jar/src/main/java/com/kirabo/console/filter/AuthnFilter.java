package com.kirabo.console.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpSession httpSession = httpServletRequest.getSession();
        System.out.println("SessionID: " + httpSession.getId());
        boolean authenticated = false;
        if (httpSession.getAttribute("authenticated") != null) {
            authenticated = (Boolean) httpSession.getAttribute("authenticated");
        }
        if (authenticated) {
            out.println("<br/>You have an authenticated session:<br/><hr/>");
            out.println("<br/>Start Regular Content:<br/><hr/>");
            filterChain.doFilter(servletRequest, servletResponse);
            out.println("<br/><hr/>End Regular Content:<br/>");
        } else {
            String username = httpServletRequest.getParameter("username");
            String password = httpServletRequest.getParameter("password");
            if (username != null
                    && password != null
                    && ((username.equals("bharath") && password
                            .equals("bharath")) || (username.equals("geoffrey") && password
                            .equals("geoffrey")))) {
                httpSession
                        .setAttribute("authenticated", Boolean.valueOf(true));
                out.println("<br/>Start Regular Content:<br/><hr/>");
                filterChain.doFilter(servletRequest, servletResponse);
                out.println("<br/><hr/>End Regular Content:<br/>");
            } else {
                out.println("<br/>Not Authenticated<br/><hr/>");
            }
        }

    }
}
