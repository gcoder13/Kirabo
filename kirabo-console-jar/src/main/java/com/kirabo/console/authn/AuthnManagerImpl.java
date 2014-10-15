package com.kirabo.console.authn;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AuthnManagerImpl {
    public enum AUTH_STATUS {
        NOT_AUTHENTICATED, AUTHENTICATED_ALREADY, AUTHENTICATED_NOW
    }

    public AUTH_STATUS manageAuthn(ServletRequest servletRequest,
            ServletResponse servletResponse) {
        AUTH_STATUS authStatus = AUTH_STATUS.NOT_AUTHENTICATED;

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpSession httpSession = httpServletRequest.getSession();
        System.out.println("SessionID: " + httpSession.getId());
        if (httpSession.getAttribute("authenticationStatus") != null) {
            authStatus = (AUTH_STATUS) httpSession
                    .getAttribute("authenticationStatus");
        }

        if (authStatus.equals(AUTH_STATUS.AUTHENTICATED_ALREADY)
                || authStatus.equals(AUTH_STATUS.AUTHENTICATED_NOW)) {
            authStatus = AUTH_STATUS.AUTHENTICATED_ALREADY;
        } else {
            String username = httpServletRequest.getParameter("username");
            String password = httpServletRequest.getParameter("password");
            if (username != null
                    && password != null
                    && ((username.equals("bharath") && password
                            .equals("bharath")) || (username.equals("geoffrey") && password
                            .equals("geoffrey")))) {
                authStatus = AUTH_STATUS.AUTHENTICATED_NOW;
            } else {
                authStatus = AUTH_STATUS.NOT_AUTHENTICATED;
            }
        }
        httpSession.setAttribute("authenticationStatus", authStatus);
        return authStatus;
    }
}
