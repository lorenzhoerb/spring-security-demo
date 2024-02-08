package com.lorenzhoerb.securitydemo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class RobotFilter extends OncePerRequestFilter {

    public static final String HEADER_NAME = "robot-x";
    private final AuthenticationManager authenticationManager;

    public RobotFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String headerValue = request.getHeader(HEADER_NAME);

        if (headerValue == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authenticationManager == null) {
            System.out.println("Cant authenticate robot because AuthenticationManager is null");
            filterChain.doFilter(request, response);
            return;
        }

        Authentication authenticatedAuthRequest = RobotAuthentication.unauthenticated(headerValue);
        try {
            var authenticated = authenticationManager.authenticate(authenticatedAuthRequest);
            var newContext = SecurityContextHolder.createEmptyContext();
            newContext.setAuthentication(authenticated);
            SecurityContextHolder.setContext(newContext);
            filterChain.doFilter(request, response);
            return;
        } catch (AuthenticationException e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-type", "text/plain;charset=utf-8");
            response.getWriter().println(e.getMessage());
            return;
        }
    }
}
