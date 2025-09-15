package com.zosh.treading.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Collection;
import java.util.stream.Collectors;

public class JwtTokenValidator extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(JwtConstant.JWT_HEADER);

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = header.substring(7);
        try {
            SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes(StandardCharsets.UTF_8));
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            String email = claims.get("email", String.class);
            if (email == null) {
                email = claims.getSubject();
            }

            Object authClaim = claims.get("authorities");
            String authoritiesString = "";
            if (authClaim instanceof String) {
                authoritiesString = (String) authClaim;
            } else if (authClaim instanceof Collection) {
                @SuppressWarnings("unchecked")
                Collection<?> coll = (Collection<?>) authClaim;
                authoritiesString = coll.stream().map(Object::toString).collect(Collectors.joining(","));
            } else if (authClaim != null) {
                authoritiesString = authClaim.toString();
            }

            List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesString);
            Authentication auth = new UsernamePasswordAuthenticationToken(email, null, authorityList);
            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (JwtException | IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Invalid JWT Token\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }
}