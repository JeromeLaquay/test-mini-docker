package com.example.authservice.service;

import com.example.authservice.model.AuthRequest;
import com.example.authservice.model.AuthResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final RestTemplate restTemplate;
    private final Key key;
    private final String usersServiceUrl;

    public AuthService(@Value("${jwt.secret}") String secret,
                      @Value("${users.service.url}") String usersServiceUrl) {
        this.restTemplate = new RestTemplate();
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.usersServiceUrl = usersServiceUrl;
    }

    public AuthResponse authenticate(AuthRequest request) {
        // Vérifier les credentials avec le service utilisateur
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = new HashMap<>();
        body.put("username", request.getUsername());
        body.put("password", request.getPassword());

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
        
        try {
            // Appel au service utilisateur pour vérifier les credentials
            restTemplate.postForEntity(usersServiceUrl + "/api/users/validate", entity, Void.class);
            
            // Générer le token JWT
            String token = generateToken(request.getUsername());
            return new AuthResponse(token, request.getUsername());
        } catch (Exception e) {
            throw new RuntimeException("Authentication failed");
        }
    }

    private String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 3600000); // 1 heure

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
} 