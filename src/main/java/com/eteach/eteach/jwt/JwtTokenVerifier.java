package com.eteach.eteach.jwt;

import com.eteach.eteach.redis.RedisService;
import com.eteach.eteach.security.userdetails.ApplicationUserService;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenVerifier extends OncePerRequestFilter {

    @Autowired
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ApplicationUserService userDetailsService;


    @Autowired
    public JwtTokenVerifier(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider =jwtTokenProvider;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        if ("/api/v1/auth/signup/".equals(path) || "/api/v1/auth/signin/".equals(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = jwtTokenProvider.getJwtFromRequest(request);
            System.out.println("token is:" + token);

            if (token != null || !jwtTokenProvider.validateJwtToken(token, request)) {

                String username = jwtTokenProvider.getUserNameFromJwtToken(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null || !jwtTokenProvider.isTokenInBlacklist(username)) {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (JwtException e) {
            throw new IllegalStateException(String.format("Token %s cannot be trusted", "hamada"));
        }

        filterChain.doFilter(request, response);
    }
}
