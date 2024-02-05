package com.springapps.shop.services;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.http.HttpHeaders;

@Service
//este un filtru care ia tokenul si il valideaza daca este bun
public class JwtRequestFilter extends OncePerRequestFilter {

    private JwtTokenService jwtTokenService;

    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public JwtRequestFilter(JwtTokenService jwtTokenService, UserDetailsServiceImpl userDetailsService) {
        this.jwtTokenService = jwtTokenService;
        this.userDetailsService = userDetailsService;
    }
//din request imi iau head-rul cu ajutorul metodei doFilterInternal
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        //daca head- rul este null sau nu incepe cu "beare" atunci tu nu mi ai dat un token si ii dau return
        if (header == null || !header.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        //daca mi -ai dat un token
        String token = header.substring(7);
        String username = jwtTokenService.validateToken(token); //imi va da userName din token
        if (username == null){
            filterChain.doFilter(request,response);
            return;
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);

    }
}
