package com.jeroka.auth.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {
    
    @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
    private String nom;

    @Size(max = 100, message = "Le prénom ne doit pas dépasser 100 caractères")
    private String prenom;

    @Size(max = 20, message = "Le numéro de téléphone ne doit pas dépasser 20 caractères")
    private String telephone;

    private String adresse;

    @Size(max = 10, message = "Le code postal ne doit pas dépasser 10 caractères")
    private String codePostal;

    @Size(max = 100, message = "La ville ne doit pas dépasser 100 caractères")
    private String ville;

    @Size(max = 100, message = "Le pays ne doit pas dépasser 100 caractères")
    private String pays;
} 