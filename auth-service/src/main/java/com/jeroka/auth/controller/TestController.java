package com.jeroka.auth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/all")
    public String allAccess() {
        return "Contenu Public";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess() {
        return "Contenu Utilisateur";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorAccess() {
        return "Contenu Modérateur";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Contenu Admin";
    }
    
    @GetMapping("/auth-info")
    public Map<String, Object> getAuthInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> info = new HashMap<>();
        
        if (auth != null) {
            info.put("authenticated", auth.isAuthenticated());
            info.put("name", auth.getName());
            info.put("authorities", auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()));
                    info.put("credentials", auth.getCredentials());
            info.put("details", auth.getDetails());
            info.put("principal", auth.getPrincipal().toString());
        } else {
            info.put("authenticated", false);
        }
        
        return info;
    }
} 