package com.jeroka.auth.service;

import com.jeroka.auth.dto.MethodePaiementRequestDTO;
import com.jeroka.auth.mapper.MethodePaiementMapper;
import com.jeroka.auth.model.MethodePaiement;
import com.jeroka.auth.model.User;
import com.jeroka.auth.repository.MethodePaiementRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@Validated
public class MethodePaiementService {
    @Autowired
    private MethodePaiementRepository methodePaiementRepository;
    @Autowired
    private UserService userService;

    public List<MethodePaiement> getMethodesPaiementByUser(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("L'ID de l'utilisateur ne peut pas être null");
        }
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new EntityNotFoundException("Utilisateur non trouvé avec l'ID : " + userId);
        }
        return methodePaiementRepository.findByUser(user);
    }

    public MethodePaiement getMethodePaiementById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID de la méthode de paiement ne peut pas être null");
        }
        return methodePaiementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Méthode de paiement non trouvée avec l'ID : " + id));
    }

    public MethodePaiement createMethodePaiement(@Valid MethodePaiementRequestDTO requestDTO) {
        if (requestDTO == null) {
            throw new IllegalArgumentException("La requête de méthode de paiement ne peut pas être null");
        }

        User user = userService.getUserById(requestDTO.getUserId());
        if (user == null) {
            throw new EntityNotFoundException("Utilisateur non trouvé avec l'ID : " + requestDTO.getUserId());
        }

        MethodePaiement methodePaiement = MethodePaiementMapper.toEntity(requestDTO, user);
        methodePaiement.setDateCreation(LocalDateTime.now());
        methodePaiement.setDateMiseAJour(LocalDateTime.now());

        if (methodePaiement.getParDefaut()) {
            methodePaiementRepository.findByUserAndParDefautTrue(user)
                    .ifPresent(oldDefault -> {
                        oldDefault.setParDefaut(false);
                        methodePaiementRepository.save(oldDefault);
                    });
        }

        return methodePaiementRepository.save(methodePaiement);
    }

    public MethodePaiement updateMethodePaiement(Long id, @Valid MethodePaiementRequestDTO requestDTO) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID de la méthode de paiement ne peut pas être null");
        }
        if (requestDTO == null) {
            throw new IllegalArgumentException("La requête de mise à jour ne peut pas être null");
        }

        MethodePaiement methodePaiement = methodePaiementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Méthode de paiement non trouvée avec l'ID : " + id));
        
        User user = userService.getUserById(requestDTO.getUserId());
        if (user == null) {
            throw new EntityNotFoundException("Utilisateur non trouvé avec l'ID : " + requestDTO.getUserId());
        }

        // Mise à jour des champs
        methodePaiement.setType(requestDTO.getType());
        methodePaiement.setToken(requestDTO.getToken());
        methodePaiement.setIdentifiantExterne(requestDTO.getIdentifiantExterne());
        methodePaiement.setDerniersChiffres(requestDTO.getDerniersChiffres());
        methodePaiement.setDateExpiration(LocalDate.parse(requestDTO.getDateExpiration(), DateTimeFormatter.ofPattern("MM/yy")));
        methodePaiement.setMarque(requestDTO.getMarque());
        methodePaiement.setNomTitulaire(requestDTO.getNomTitulaire());
        
        if (requestDTO.getParDefaut() && !methodePaiement.getParDefaut()) {
            methodePaiementRepository.findByUserAndParDefautTrue(user)
                    .ifPresent(oldDefault -> {
                        oldDefault.setParDefaut(false);
                        methodePaiementRepository.save(oldDefault);
                    });
        }
        
        methodePaiement.setParDefaut(requestDTO.getParDefaut());
        methodePaiement.setDateMiseAJour(LocalDateTime.now());

        return methodePaiementRepository.save(methodePaiement);
    }

    public void deleteMethodePaiement(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID de la méthode de paiement ne peut pas être null");
        }
        if (!methodePaiementRepository.existsById(id)) {
            throw new EntityNotFoundException("Méthode de paiement non trouvée avec l'ID : " + id);
        }
        methodePaiementRepository.deleteById(id);
    }

    public MethodePaiement initializePayment(PaymentRequestDTO paymentData) {
        // Initialiser le paiement pour obtenir un token et créer la méthode de paiement
        // Cette méthode doit être implémentée en fonction de la logique de paiement spécifique
        // Par exemple, si vous utilisez Stripe, vous devrez initialiser le paiement Stripe
        String type = paymentData.getType();
        if (type.equals("PAYPAL")) {
            // Initialiser le paiement PayPal
            return initializePaypalPayment(paymentData);
        } else if (type.equals("CARTE")) {
            // Initialiser le paiement par carte
            return initializeCardPayment(paymentData);
        }
        return null;
    }

    private MethodePaiement initializePaypalPayment(PaymentRequestDTO paymentData) {
        MethodePaiement methodePaiement = new MethodePaiement();
        // Implémenter la logique pour initialiser le paiement PayPal
        // Cette méthode doit retourner une instance de MethodePaiement
        
        return methodePaiement;
    }

    private MethodePaiement initializeCardPayment(PaymentRequestDTO paymentData) {
        MethodePaiement methodePaiement = new MethodePaiement();
        // Implémenter la logique pour initialiser le paiement par carte par exemple avec stripe
        // Cette méthode doit retourner une instance de MethodePaiement
        return methodePaiement;
    }
    
    
} 