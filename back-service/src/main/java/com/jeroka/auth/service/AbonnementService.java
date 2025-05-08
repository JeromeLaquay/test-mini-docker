package com.jeroka.auth.service;

import com.jeroka.auth.mapper.AbonnementMapper;
import com.jeroka.auth.model.Abonnement;
import com.jeroka.auth.model.User;
import com.jeroka.auth.model.Plan;
import com.jeroka.auth.model.MethodePaiement;
import com.jeroka.auth.model.enums.StatutAbonnement;
import com.jeroka.auth.repository.AbonnementRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import java.time.LocalDateTime;
import java.util.List;
import com.jeroka.auth.dto.AbonnementRequestDTO;

@Service
@Transactional
@Validated
public class AbonnementService {
    @Autowired
    private AbonnementRepository abonnementRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PlanService planService;
    @Autowired
    private MethodePaiementService methodePaiementService;

    public List<Abonnement> getAbonnementsByUser(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("L'ID de l'utilisateur ne peut pas être null");
        }
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new EntityNotFoundException("Utilisateur non trouvé avec l'ID : " + userId);
        }
        return abonnementRepository.findByUser(user);
    }

    public List<Abonnement> getAbonnementsActifsByUser(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("L'ID de l'utilisateur ne peut pas être null");
        }
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new EntityNotFoundException("Utilisateur non trouvé avec l'ID : " + userId);
        }
        return abonnementRepository.findByUserAndStatut(user, StatutAbonnement.ACTIF);
    }

    public Abonnement getAbonnementById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID de l'abonnement ne peut pas être null");
        }
        return abonnementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Abonnement non trouvé avec l'ID : " + id));
    }

    public Abonnement createAbonnement(@Valid AbonnementRequestDTO requestDTO) {
        if (requestDTO == null) {
            throw new IllegalArgumentException("La requête d'abonnement ne peut pas être null");
        }

        User user = userService.getUserById(requestDTO.getUserId());
        if (user == null) {
            throw new EntityNotFoundException("Utilisateur non trouvé avec l'ID : " + requestDTO.getUserId());
        }

        Plan plan = planService.getPlanById(requestDTO.getPlanId());
        if (plan == null) {
            throw new EntityNotFoundException("Plan non trouvé avec l'ID : " + requestDTO.getPlanId());
        }

        MethodePaiement methodePaiement = null;
        if (requestDTO.getMethodePaiementId() != null) {
            methodePaiement = methodePaiementService.getMethodePaiementById(requestDTO.getMethodePaiementId());
            if (methodePaiement == null) {
                throw new EntityNotFoundException("Méthode de paiement non trouvée avec l'ID : " + requestDTO.getMethodePaiementId());
            }
        }

        Abonnement abonnement = AbonnementMapper.toEntity(requestDTO, user, plan, methodePaiement);
        abonnement.setDateCreation(LocalDateTime.now());
        abonnement.setDateMiseAJour(LocalDateTime.now());

        if (plan.getPeriodeEssaiJours() != null && plan.getPeriodeEssaiJours() > 0) {
            abonnement.setStatut(StatutAbonnement.ESSAI);
            abonnement.setDateFinEssai(LocalDateTime.now().plusDays(plan.getPeriodeEssaiJours()));
        } else {
            abonnement.setStatut(StatutAbonnement.ACTIF);
        }

        return abonnementRepository.save(abonnement);
    }

    public Abonnement updateAbonnement(Long id, @Valid AbonnementRequestDTO requestDTO) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID de l'abonnement ne peut pas être null");
        }
        if (requestDTO == null) {
            throw new IllegalArgumentException("La requête de mise à jour ne peut pas être null");
        }

        Abonnement abonnement = abonnementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Abonnement non trouvé avec l'ID : " + id));

        // Mise à jour des champs
        abonnement.setPeriode(requestDTO.getPeriode());
        abonnement.setDateProchaineFacturation(requestDTO.getDateProchaineFacturation());
        abonnement.setDateFin(requestDTO.getDateFin());
        abonnement.setMontant(requestDTO.getMontant());
        abonnement.setAutoRenouvellement(requestDTO.getAutoRenouvellement());
        abonnement.setDateMiseAJour(LocalDateTime.now());

        return abonnementRepository.save(abonnement);
    }

    public void cancelAbonnement(Long id, String raison) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID de l'abonnement ne peut pas être null");
        }
        if (raison == null || raison.trim().isEmpty()) {
            throw new IllegalArgumentException("La raison d'annulation ne peut pas être null ou vide");
        }

        Abonnement abonnement = abonnementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Abonnement non trouvé avec l'ID : " + id));

        abonnement.setStatut(StatutAbonnement.ANNULE);
        abonnement.setRaisonAnnulation(raison);
        abonnement.setDateFin(LocalDateTime.now());
        abonnement.setAutoRenouvellement(false);
        abonnement.setDateMiseAJour(LocalDateTime.now());
        
        abonnementRepository.save(abonnement);
    }

    public List<Abonnement> getAbonnementsExpires() {
        return abonnementRepository.findByDateFinBefore(LocalDateTime.now());
    }

    public List<Abonnement> getAbonnementsAFacturer() {
        return abonnementRepository.findByDateProchaineFacturationBefore(LocalDateTime.now());
    }
} 