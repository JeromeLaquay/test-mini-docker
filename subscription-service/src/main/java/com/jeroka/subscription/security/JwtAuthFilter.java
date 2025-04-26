package com.jeroka.subscription.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Filtre d'authentification JWT pour valider les tokens dans les requêtes entrantes.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null) {
                Claims claims = validateToken(jwt);
                
                // Extraire les rôles du token
                @SuppressWarnings("unchecked")
                List<String> roles = (List<String>) claims.get("roles");
                
                // Créer les autorités Spring Security
                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
                
                // Créer un objet Principal qui contient l'ID de l'utilisateur
                JwtUserDetails principal = new JwtUserDetails(claims.getSubject(), claims.get("id", Long.class));
                
                // Créer et configurer l'authentification
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        principal, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // Définir l'authentification dans le contexte de sécurité
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Impossible de définir l'authentification utilisateur: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }

    private Claims validateToken(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(Decoders.BASE64.decode(jwtSecret))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Classe interne représentant les détails d'un utilisateur authentifié via JWT.
     */
    public static class JwtUserDetails {
        private final String username;
        private final Long id;

        public JwtUserDetails(String username, Long id) {
            this.username = username;
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public Long getId() {
            return id;
        }
    }
} 