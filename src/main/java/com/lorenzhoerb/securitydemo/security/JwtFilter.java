package com.lorenzhoerb.securitydemo.security;

import com.lorenzhoerb.securitydemo.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    public static final String HEADER_NAME = "Authorization";

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        final String headerValue;
        final String userEmail;
        final String token;

        headerValue = request.getHeader(HEADER_NAME);
        if (headerValue == null || !headerValue.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        token = headerValue.substring(7);
        userEmail = jwtService.extractUsername(token);

        if (userEmail.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
        if (jwtService.isTokenValid(token, userDetails)) {
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            var authentication = new UsernamePasswordAuthenticationToken(
                    userEmail,
                    null,
                    userDetails.getAuthorities()
            );
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
        }

        filterChain.doFilter(request, response);
    }
}
