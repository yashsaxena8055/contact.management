package com.example.contact.management.security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

    @Autowired
    private JwtUtilityService jwtUtilityService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;
        if (requestHeader == null || !requestHeader.startsWith("Bearer")) {
            logger.error("Validation fails !!");
        } else {
            token = requestHeader.substring(7);
            username = jwtUtilityService.getUsernameFromToken(token);
        }
        if (StringUtils.hasText(username) && SecurityContextHolder.getContext().getAuthentication() == null) {

            //fetch user detail from username
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            Boolean validateToken = jwtUtilityService.validateToken(token, userDetails);
            if (validateToken) {

                //set the authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                logger.error("Validation fails !!");
            }

        }
        filterChain.doFilter(request, response);
    }

}
